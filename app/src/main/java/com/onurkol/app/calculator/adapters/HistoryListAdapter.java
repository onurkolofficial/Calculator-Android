package com.onurkol.app.calculator.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.onurkol.app.calculator.R;
import com.onurkol.app.calculator.activity.MainActivity;
import com.onurkol.app.calculator.data.HistoryData;
import com.onurkol.app.calculator.libs.app.HistoryManager;
import com.onurkol.app.calculator.libs.modules.StringLimiter;
import com.onurkol.app.calculator.libs.settings.ThemeManager;

import java.util.List;

public class HistoryListAdapter extends ArrayAdapter<HistoryData> {
    private final LayoutInflater inflater;
    private ViewHolder holder;
    private static List<HistoryData> historyData;
    private final ListView historyListView;

    ThemeManager themeManager;
    HistoryManager historyManager;

    public HistoryListAdapter(@NonNull Context context, ListView pHistoryListView, @NonNull List<HistoryData> pHistoryData) {
        super(context, 0, pHistoryData);
        historyData = pHistoryData;
        historyListView = pHistoryListView;
        inflater = LayoutInflater.from(context);
        themeManager = ThemeManager.getInstance();
        historyManager = HistoryManager.getManager(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list_history, null);
            holder = new ViewHolder();
            holder.expressionText = convertView.findViewById(R.id.expressionText);
            holder.valueText = convertView.findViewById(R.id.valueText);
            holder.deleteHistoryButton = convertView.findViewById(R.id.deleteHistoryButton);
            holder.openViewButton = convertView.findViewById(R.id.openViewButton);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }

        final HistoryData currentData = historyData.get(position);

        String Expression = StringLimiter.Limit(currentData.getExpression(), 26);
        String Value = StringLimiter.Limit(currentData.getValue(),24);
        // Write Data
        holder.expressionText.setText(Expression);
        holder.valueText.setText(Value);

        int themeColor=themeManager.getCurrentThemeColor(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            holder.openViewButton.setOutlineAmbientShadowColor(themeColor);
            holder.openViewButton.setOutlineSpotShadowColor(themeColor);
        }
        themeManager.setBackgroundTint(getContext(), holder.openViewButton);

        holder.deleteHistoryButton.setOnClickListener(view -> {
            // Get Root View
            View rView=view.getRootView();

            historyManager.removeHistory(position);
            historyListView.invalidateViews();

            if(historyManager.getHistoryListFromJSON().size()==0) {
                LinearLayout noHistoryLayout=rView.findViewById(R.id.noHistoryLayout);
                // Show 'No History' Layout
                noHistoryLayout.setVisibility(View.VISIBLE);
            }
        });

        holder.openViewButton.setOnClickListener(view -> {
            // Get Current Context Activity
            Activity $this=(Activity)getContext();
            // Create Main Activity
            Intent mainActivity=new Intent($this, MainActivity.class);
            // Create new Bundle
            Bundle bundle = new Bundle();
            // Set bundle Data
            bundle.putBoolean("LOAD_INTENT_DATA", true);
            bundle.putString("HISTORY_EXPRESSION", currentData.getExpression());
            bundle.putString("HISTORY_VALUE", currentData.getValue());

            mainActivity.putExtras(bundle);

            // for SHORTCUT Activity!
            if(!MainActivity.isCreated)
                // Start MainActivity
                $this.startActivity(mainActivity);
            else
                // Update MainActivity Intent
                MainActivity.updatedIntent=mainActivity;

            $this.finish();
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView expressionText, valueText;
        ImageButton deleteHistoryButton;
        CardView openViewButton;
    }
}
