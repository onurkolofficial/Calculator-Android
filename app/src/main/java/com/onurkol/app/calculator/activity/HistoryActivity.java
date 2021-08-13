package com.onurkol.app.calculator.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.onurkol.app.calculator.R;
import com.onurkol.app.calculator.adapters.HistoryListAdapter;
import com.onurkol.app.calculator.interfaces.AppDataSettings;
import com.onurkol.app.calculator.lib.AppDataManager;
import com.onurkol.app.calculator.lib.AppPreferenceManager;
import com.onurkol.app.calculator.lib.ContextManager;
import com.onurkol.app.calculator.lib.calculator.history.HistoryManager;

public class HistoryActivity extends AppCompatActivity implements AppDataSettings {
    // Elements
    ImageButton backButton;
    TextView settingName;
    ListView historyListView;
    LinearLayout noHistoryLayout;
    // Classes
    AppPreferenceManager prefManager;
    HistoryManager historyManager;
    // Adapter
    HistoryListAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set Current Activity Context
        ContextManager.Build(this);
        // Load App Data
        AppDataManager.loadApplicationData();
        // Create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        // Get Classes
        prefManager=AppPreferenceManager.getInstance();
        historyManager=HistoryManager.getManager();
        // Get Elements
        backButton=findViewById(R.id.backButton);
        settingName=findViewById(R.id.settingName);
        historyListView=findViewById(R.id.historyListView);
        noHistoryLayout=findViewById(R.id.noHistoryLayout);
        // Set Toolbar Title
        settingName.setText(getString(R.string.history_text));

        // Button Click Events
        backButton.setOnClickListener(view -> {
            // Close Activity
            finish();
        });

        // Set List Adapter & Data
        historyAdapter=new HistoryListAdapter(this,historyListView,HISTORY_DATA_LIST);
        historyListView.setAdapter(historyAdapter);
        // Get Preference Data
        getPreferenceDataToList();
    }

    private void getPreferenceDataToList(){
        // Get Preference
        String histories=prefManager.getString(KEY_CALCULATOR_HISTORY);

        if(histories==null || histories.equals("")){
            // Show No History Layout
            noHistoryLayout.setVisibility(View.VISIBLE);
            // Clear List
            HISTORY_DATA_LIST.clear();
        }
        else{
            // Show No History Layout
            noHistoryLayout.setVisibility(View.GONE);
            // Update Data
            historyManager.syncSavedHistoryData();
        }
    }
}