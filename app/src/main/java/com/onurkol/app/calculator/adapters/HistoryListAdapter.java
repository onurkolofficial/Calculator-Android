package com.onurkol.app.calculator.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.onurkol.app.calculator.tools.LimitCharTool;

import java.util.List;

import static com.onurkol.app.calculator.controllers.HistoryController.removeHistory;
import static com.onurkol.app.calculator.data.SettingData.getHistoryList;

public class HistoryListAdapter extends ArrayAdapter<HistoryData> {
    private final LayoutInflater inflater;
    private ViewHolder holder;
    private static List<HistoryData> historyData;
    private final ListView historyListView;

    public HistoryListAdapter(Context context, ListView mHistoryListView, List<HistoryData> mHistoryData){
        super(context,0,mHistoryData);
        historyData=mHistoryData;
        historyListView=mHistoryListView;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null) {
            convertView=inflater.inflate(R.layout.item_history_list, null);
            holder=new ViewHolder();
            holder.expressionView=convertView.findViewById(R.id.procExpressText);
            holder.valueView=convertView.findViewById(R.id.procValueText);
            holder.deleteButton=convertView.findViewById(R.id.deleteHistoryButton);
            holder.openHistoryData=convertView.findViewById(R.id.historyOpenButton);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }

        // Get Data
        final HistoryData hsData=historyData.get(position);

        // Character Limits
        String Expression=LimitCharTool.LimitChar(hsData.getProcessExpression(), 26);
        String Value=LimitCharTool.LimitChar(hsData.getProcessValue(),24);
        // Write Data
        holder.expressionView.setText(Expression);
        holder.valueView.setText(Value);

        // Button Click Events
        // Delete Button
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Root View
                View rView=view.getRootView();
                // Delete HistoryData
                removeHistory(position);
                // Remove List Item
                getHistoryList().remove(position);
                // Refresh View
                historyListView.invalidateViews();
                // Check No History Layout
                if(historyData.size()<=0) {
                    // Get Element
                    LinearLayout noHistoryLayout=rView.findViewById(R.id.noHistoryLayout);
                    // Show No History Layout
                    noHistoryLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        // Open Button
        holder.openHistoryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Context Activity
                Activity $this=(Activity)getContext();
                // Create Main Activity
                Intent mainActivity=new Intent($this, MainActivity.class);
                // Create new Bundle
                Bundle bundle = new Bundle();
                // Set bundle Data
                bundle.putBoolean("LOAD_INTENT_DATA",true);
                bundle.putString("HISTORY_EXPRESSION",hsData.getProcessExpression());
                bundle.putString("HISTORY_VALUE",hsData.getProcessValue());
                // Put Intent in MainActivity
                mainActivity.putExtras(bundle);

                // Check Exist MainActivity
                if(!MainActivity.isCreate)
                    // Start MainActivity
                    $this.startActivity(mainActivity);
                else
                    // Update MainActivity Intent
                    MainActivity.updatedIntent=mainActivity;
                // Close Current Activity
                $this.finish();
            }
        });

        return convertView;
    }

    //View Holder
    private static class ViewHolder {
        TextView expressionView,valueView;
        ImageButton deleteButton;
        CardView openHistoryData;
    }
}
