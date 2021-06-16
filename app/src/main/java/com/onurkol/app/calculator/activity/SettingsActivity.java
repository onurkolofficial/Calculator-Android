package com.onurkol.app.calculator.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.onurkol.app.calculator.R;
import com.onurkol.app.calculator.fragments.SettingsFragment;

import static com.onurkol.app.calculator.data.SettingData.updateApplicationData;
import static com.onurkol.app.calculator.data.SettingData.updateThemeList;
import static com.onurkol.app.calculator.tools.ContextTool.setContext;

public class SettingsActivity extends AppCompatActivity {

    ImageButton backSettingsButton;
    TextView settingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set Context
        setContext(this);
        // Init Preferences & App Data
        updateApplicationData();
        // Create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Get Elements
        backSettingsButton=findViewById(R.id.backSettingsButton);
        settingName=findViewById(R.id.settingName);

        // Set Toolbar Title
        settingName.setText(getString(R.string.settings_text));

        // Button Click Events
        backSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Close Activity
                finish();
            }
        });

        // Update Setting Data
        // Themes
        updateThemeList();

        // Set Fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.settingsFragmentContent,new SettingsFragment()).commit();
    }
}