package com.onurkol.app.calculator.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onurkol.app.calculator.R;
import com.onurkol.app.calculator.adapters.HistoryListAdapter;
import com.onurkol.app.calculator.data.HistoryData;
import com.onurkol.app.calculator.tools.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import static com.onurkol.app.calculator.data.SettingData.getHistoryList;
import static com.onurkol.app.calculator.data.SettingData.updateApplicationData;
import static com.onurkol.app.calculator.tools.ContextTool.setContext;

public class HistoryActivity extends AppCompatActivity {

    ImageButton backSettingsButton;
    TextView settingName;
    ListView historyListView;
    LinearLayout noHistoryLayout;

    HistoryListAdapter historyAdapter;

    SharedPreferenceManager prefManager;

    static Gson gson=new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set Context
        setContext(this);
        // Init Preferences & App Data
        updateApplicationData();
        // Create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Get Preference Manager
        prefManager=SharedPreferenceManager.getInstance();

        // Get Elements
        backSettingsButton=findViewById(R.id.backSettingsButton);
        settingName=findViewById(R.id.settingName);
        historyListView=findViewById(R.id.historyListView);
        noHistoryLayout=findViewById(R.id.noHistoryLayout);

        // Set Toolbar Title
        settingName.setText(getString(R.string.history_text));

        // Button Click Events
        backSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Close Activity
                finish();
            }
        });

        // Set List Adapter & Data
        historyAdapter=new HistoryListAdapter(this,historyListView,getHistoryList());
        historyListView.setAdapter(historyAdapter);
        // Get Preference Data
        getPreferenceDataToList();

    }
    private void getPreferenceDataToList(){
        // Get Preference
        String histories=prefManager.getString("CALC_HISTORY_DATA");

        if(histories.equals("NULL")){
            // Show No History Layout
            noHistoryLayout.setVisibility(View.VISIBLE);
            // Clear List
            getHistoryList().clear();
        }
        else{
            // Show No History Layout
            noHistoryLayout.setVisibility(View.GONE);
            // Data Convert String to List
            List<HistoryData> getHistData=gson.fromJson(histories, new TypeToken<ArrayList<HistoryData>>(){}.getType());

            int mainListSize=getHistoryList().size();
            int getListSize=getHistData.size();
            // Check Data
            if(mainListSize<=0) {
                int i=0;
                while(i<getListSize){
                    // Get Data
                    String gExpression=getHistData.get(i).getProcessExpression();
                    String gValue=getHistData.get(i).getProcessValue();
                    // Add View
                    getHistoryList().add(new HistoryData(gExpression,gValue));
                    // Count
                    i++;
                }
            }
        }
    }
}