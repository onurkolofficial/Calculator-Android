package com.onurkol.app.calculator.activity.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.onurkol.app.calculator.R;
import com.onurkol.app.calculator.activity.SettingsActivity;
import com.onurkol.app.calculator.interfaces.AppSettingsInterface;
import com.onurkol.app.calculator.libs.AppPreferenceManager;
import com.onurkol.app.calculator.libs.AppSettingsInitializeManager;
import com.onurkol.app.calculator.libs.settings.ThemeManager;
import com.onurkol.app.calculator.libs.settings.UIModeManager;

public class UIModeActivity extends AppCompatActivity implements AppSettingsInterface {
    ThemeManager themeManager;
    AppPreferenceManager appPreferenceManager;
    UIModeManager uiModeManager;

    ImageButton settingsBackButton;
    TextView settingsTitle;
    LinearLayoutCompat uiModeSystemButton, uiModeDayButton, uiModeNightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppSettingsInitializeManager.onStart(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_uimode);

        themeManager = ThemeManager.getInstance();
        appPreferenceManager = AppPreferenceManager.getInstance(this);
        uiModeManager = UIModeManager.getInstance();

        settingsBackButton = findViewById(R.id.settingsBackButton);
        settingsTitle = findViewById(R.id.settingsTitle);
        uiModeSystemButton = findViewById(R.id.uiModeSystemButton);
        uiModeDayButton = findViewById(R.id.uiModeDayButton);
        uiModeNightButton = findViewById(R.id.uiModeNightButton);

        settingsTitle.setText(getString(R.string.str_ui_mode_title));

        startAppTheme();

        settingsBackButton.setOnClickListener(view -> finish());
        uiModeSystemButton.setOnClickListener(view -> {
            appPreferenceManager.setPreference(_APP_KEY_UI_MODE, _UI_MODE_AUTO);
            SettingsActivity.isConfigChanged = true;

            uiModeManager.setAppUIMode(_UI_MODE_AUTO);
        });
        uiModeDayButton.setOnClickListener(view -> {
            appPreferenceManager.setPreference(_APP_KEY_UI_MODE, _UI_MODE_DAY);
            SettingsActivity.isConfigChanged = true;

            uiModeManager.setAppUIMode(_UI_MODE_DAY);
        });
        uiModeNightButton.setOnClickListener(view -> {
            appPreferenceManager.setPreference(_APP_KEY_UI_MODE, _UI_MODE_NIGHT);
            SettingsActivity.isConfigChanged = true;

            uiModeManager.setAppUIMode(_UI_MODE_NIGHT);
        });
    }

    private void startAppTheme(){
        themeManager.setBackgroundTint(this, settingsBackButton);
        themeManager.setTextColor(this, settingsTitle);
    }
}