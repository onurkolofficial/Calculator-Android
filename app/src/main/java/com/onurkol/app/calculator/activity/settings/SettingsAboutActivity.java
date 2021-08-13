package com.onurkol.app.calculator.activity.settings;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.onurkol.app.calculator.R;
import com.onurkol.app.calculator.fragments.SettingsFragment;
import com.onurkol.app.calculator.fragments.settings.SettingsAboutFragment;
import com.onurkol.app.calculator.lib.AppDataManager;
import com.onurkol.app.calculator.lib.ContextManager;

public class SettingsAboutActivity extends AppCompatActivity {
    // Elements
    ImageButton backButton;
    TextView settingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set Current Activity Context
        ContextManager.Build(this);
        // Load App Data
        AppDataManager.loadApplicationData();
        // Create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_about);
        // Get Elements
        backButton=findViewById(R.id.backButton);
        settingName=findViewById(R.id.settingName);
        // Set Toolbar Title
        settingName.setText(getString(R.string.about_text));

        // Button Click Events
        backButton.setOnClickListener(view -> {
            // Close Activity
            finish();
        });

        // Set Fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.settingsAboutFragmentContent,new SettingsAboutFragment()).commit();
    }
}