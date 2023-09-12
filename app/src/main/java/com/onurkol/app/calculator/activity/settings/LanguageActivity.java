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
import com.onurkol.app.calculator.libs.settings.LanguageManager;
import com.onurkol.app.calculator.libs.settings.ThemeManager;

public class LanguageActivity extends AppCompatActivity implements AppSettingsInterface {
    ThemeManager themeManager;
    AppPreferenceManager appPreferenceManager;
    LanguageManager languageManager;

    ImageButton settingsBackButton;
    TextView settingsTitle;
    LinearLayoutCompat languageSystemButton, languageEnglishButton, languageTurkishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppSettingsInitializeManager.onStart(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_language);

        themeManager = ThemeManager.getInstance();
        appPreferenceManager = AppPreferenceManager.getInstance(this);
        languageManager = LanguageManager.getInstance();

        settingsBackButton = findViewById(R.id.settingsBackButton);
        settingsTitle = findViewById(R.id.settingsTitle);
        languageSystemButton = findViewById(R.id.languageSystemButton);
        languageEnglishButton = findViewById(R.id.languageEnglishButton);
        languageTurkishButton = findViewById(R.id.languageTurkishButton);

        settingsTitle.setText(getString(R.string.str_language_title));

        startAppTheme();

        settingsBackButton.setOnClickListener(view -> finish());
        languageSystemButton.setOnClickListener(view -> {
            appPreferenceManager.setPreference(_APP_KEY_LANGUAGE, _LANGUAGE_SYSTEM);
            SettingsActivity.isLanguageChanged = true;

            languageManager.setAppLanguage(this, _LANGUAGE_SYSTEM);
            recreate();
        });
        languageEnglishButton.setOnClickListener(view -> {
            appPreferenceManager.setPreference(_APP_KEY_LANGUAGE, _LANGUAGE_EN_US);
            SettingsActivity.isLanguageChanged = true;

            languageManager.setAppLanguage(this, _LANGUAGE_EN_US);
            recreate();
        });
        languageTurkishButton.setOnClickListener(view -> {
            appPreferenceManager.setPreference(_APP_KEY_LANGUAGE, _LANGUAGE_TR_TR);
            SettingsActivity.isLanguageChanged = true;

            languageManager.setAppLanguage(this, _LANGUAGE_TR_TR);
            recreate();
        });
    }

    private void startAppTheme(){
        themeManager.setBackgroundTint(this, settingsBackButton);
        themeManager.setTextColor(this, settingsTitle);
    }
}