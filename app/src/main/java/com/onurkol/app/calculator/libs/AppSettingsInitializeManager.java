package com.onurkol.app.calculator.libs;

import android.content.Context;

import com.onurkol.app.calculator.interfaces.AppSettingsInterface;
import com.onurkol.app.calculator.libs.settings.LanguageManager;
import com.onurkol.app.calculator.libs.settings.UIModeManager;

import java.lang.ref.WeakReference;

public class AppSettingsInitializeManager implements AppSettingsInterface {
    private static WeakReference<AppSettingsInitializeManager> wInstance = null;
    AppPreferenceManager appPrefManager;

    public static synchronized void onStart(Context context) {
        if(wInstance == null || wInstance.get() == null)
            wInstance = new WeakReference<>(new AppSettingsInitializeManager());

        wInstance.get().initializePreferenceData(context);
    }

    private void initializePreferenceData(Context context){
        appPrefManager = AppPreferenceManager.getInstance(context);

        if(appPrefManager.getInt(_APP_KEY_UI_MODE) == AppPreferenceManager.INT_NULL)
            appPrefManager.setPreference(_APP_KEY_UI_MODE, _APP_DEF_UI_MODE);

        if(appPrefManager.getInt(_APP_KEY_THEME) == AppPreferenceManager.INT_NULL)
            appPrefManager.setPreference(_APP_KEY_THEME, _APP_DEF_THEME);

        if(appPrefManager.getInt(_APP_KEY_LANGUAGE) == AppPreferenceManager.INT_NULL)
            appPrefManager.setPreference(_APP_KEY_LANGUAGE, _APP_DEF_LANGUAGE);

        if(appPrefManager.getString(_APP_KEY_CALCULATOR_HISTORY) == null)
            appPrefManager.setPreference(_APP_KEY_CALCULATOR_HISTORY, "");

        if(appPrefManager.getString(_APP_KEY_CALCULATOR_CURRENT_HISTORY) == null)
            appPrefManager.setPreference(_APP_KEY_CALCULATOR_CURRENT_HISTORY, "");

        if(appPrefManager.getString(_APP_KEY_CALCULATOR_CURRENT_VALUE) == null)
            appPrefManager.setPreference(_APP_KEY_CALCULATOR_CURRENT_VALUE, "");

        initializeSettings(context);
    }

    private void initializeSettings(Context context){
        // Update UI
        UIModeManager.getInstance().setAppUIMode(appPrefManager.getInt(_APP_KEY_UI_MODE));
        LanguageManager.getInstance().setAppLanguage(context, appPrefManager.getInt(_APP_KEY_LANGUAGE));
    }
}
