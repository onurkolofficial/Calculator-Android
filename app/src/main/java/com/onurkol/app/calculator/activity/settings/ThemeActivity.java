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

public class ThemeActivity extends AppCompatActivity implements AppSettingsInterface {
    ThemeManager themeManager;
    AppPreferenceManager appPreferenceManager;

    ImageButton settingsBackButton;
    TextView settingsTitle;
    LinearLayoutCompat themeGreyButton, themeBlueButton, themePurpleButton, themePinkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppSettingsInitializeManager.onStart(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_theme);

        themeManager = ThemeManager.getInstance();
        appPreferenceManager = AppPreferenceManager.getInstance(this);

        settingsBackButton = findViewById(R.id.settingsBackButton);
        settingsTitle = findViewById(R.id.settingsTitle);
        themeGreyButton = findViewById(R.id.themeGreyButton);
        themeBlueButton = findViewById(R.id.themeBlueButton);
        themePurpleButton = findViewById(R.id.themePurpleButton);
        themePinkButton = findViewById(R.id.themePinkButton);

        settingsTitle.setText(getString(R.string.str_theme_title));

        startAppTheme();

        settingsBackButton.setOnClickListener(view -> finish());
        themeGreyButton.setOnClickListener(view -> colorButtonEvent(_THEME_COLOR_GREY));
        themeBlueButton.setOnClickListener(view -> colorButtonEvent(_THEME_COLOR_BLUE));
        themePurpleButton.setOnClickListener(view -> colorButtonEvent(_THEME_COLOR_PURPLE));
        themePinkButton.setOnClickListener(view -> colorButtonEvent(_THEME_COLOR_PINK));
    }

    private void colorButtonEvent(int _THEME_COLOR_ID){
        appPreferenceManager.setPreference(_APP_KEY_THEME, _THEME_COLOR_ID);
        SettingsActivity.isConfigChanged = true;

        startAppTheme();
    }

    private void startAppTheme(){
        themeManager.setBackgroundTint(this, settingsBackButton);
        themeManager.setTextColor(this, settingsTitle);
    }
}