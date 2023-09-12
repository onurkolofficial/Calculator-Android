package com.onurkol.app.calculator.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.onurkol.app.calculator.R;
import com.onurkol.app.calculator.activity.settings.AboutActivity;
import com.onurkol.app.calculator.activity.settings.LanguageActivity;
import com.onurkol.app.calculator.activity.settings.ThemeActivity;
import com.onurkol.app.calculator.activity.settings.UIModeActivity;
import com.onurkol.app.calculator.interfaces.AppSettingsInterface;
import com.onurkol.app.calculator.libs.AppPreferenceManager;
import com.onurkol.app.calculator.libs.AppSettingsInitializeManager;
import com.onurkol.app.calculator.libs.settings.LanguageManager;
import com.onurkol.app.calculator.libs.settings.ThemeManager;
import com.onurkol.app.calculator.libs.settings.UIModeManager;

public class SettingsActivity extends AppCompatActivity implements AppSettingsInterface {
    ThemeManager themeManager;
    AppPreferenceManager appPreferenceManager;
    UIModeManager uiModeManager;
    LanguageManager languageManager;

    ImageButton settingsBackButton;
    TextView settingsTitle, appearanceTitle, otherTitle;
    LinearLayoutCompat uiModeButton, themeButton, languageButton, aboutButton;

    public static boolean isConfigChanged = false,
            isLanguageChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppSettingsInitializeManager.onStart(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        themeManager = ThemeManager.getInstance();
        appPreferenceManager = AppPreferenceManager.getInstance(this);
        uiModeManager = UIModeManager.getInstance();
        languageManager = LanguageManager.getInstance();

        settingsBackButton = findViewById(R.id.settingsBackButton);
        settingsTitle = findViewById(R.id.settingsTitle);
        appearanceTitle = findViewById(R.id.appearanceTitle);
        otherTitle = findViewById(R.id.otherTitle);
        uiModeButton = findViewById(R.id.uiModeButton);
        themeButton = findViewById(R.id.themeButton);
        languageButton = findViewById(R.id.languageButton);
        aboutButton = findViewById(R.id.aboutButton);

        settingsTitle.setText(getString(R.string.str_settings));

        startAppTheme();

        settingsBackButton.setOnClickListener(view -> finish());

        uiModeButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, UIModeActivity.class);
            startActivity(intent);
        });
        themeButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, ThemeActivity.class);
            startActivity(intent);
        });
        languageButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, LanguageActivity.class);
            startActivity(intent);
        });
        aboutButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        });

    }

    private void startAppTheme(){
        themeManager.setBackgroundTint(this, settingsBackButton);
        themeManager.setTextColor(this, settingsTitle);
        themeManager.setTextColor(this, appearanceTitle);
        themeManager.setTextColor(this, otherTitle);
    }

    @Override
    protected void onResume() {
        if(isConfigChanged){
            MainActivity.isConfigChanged = true;
            startAppTheme();
            uiModeManager.setAppUIMode(appPreferenceManager.getInt(_APP_KEY_UI_MODE));
        }
        if(isLanguageChanged){
            MainActivity.isLanguageChanged = true;
            languageManager.setAppLanguage(this, appPreferenceManager.getInt(_APP_KEY_LANGUAGE));
            recreate();
        }

        isLanguageChanged = false;
        isConfigChanged = false;
        super.onResume();
    }
}