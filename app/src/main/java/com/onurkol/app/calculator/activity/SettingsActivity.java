package com.onurkol.app.calculator.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.onurkol.app.calculator.R;
import com.onurkol.app.calculator.fragments.SettingsFragment;
import com.onurkol.app.calculator.lib.AppDataManager;
import com.onurkol.app.calculator.lib.ContextManager;

public class SettingsActivity extends AppCompatActivity {
    // Elements
    ImageButton backButton;
    TextView settingName;

    // Variables
    public static boolean isConfigChanged=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set Current Activity Context
        ContextManager.Build(this);
        // Load App Data
        AppDataManager.loadApplicationData();
        // Create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // Get Elements
        backButton=findViewById(R.id.backButton);
        settingName=findViewById(R.id.settingName);
        // Set Toolbar Title
        settingName.setText(getString(R.string.settings_text));

        // Button Click Events
        backButton.setOnClickListener(view -> {
            // Close Activity
            finish();
        });

        // Set Fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.settingsFragmentContent,new SettingsFragment()).commit();
    }

    @Override
    protected void onResume() {
        // Re-set Context
        ContextManager.Build(this);
        // Check Config Changes
        if(isConfigChanged) {
            MainActivity.isConfigChanged=true;
            // Load Data
            AppDataManager.loadApplicationData();
            recreate();
            // Reset Variables
            isConfigChanged=false;
        }
        super.onResume();
    }
}