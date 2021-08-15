package com.onurkol.app.calculator.lib;

import android.util.Log;

import com.onurkol.app.calculator.interfaces.AppDataSettings;
import com.onurkol.app.calculator.interfaces.AppPreferenceSettings;
import com.onurkol.app.calculator.lib.core.LanguageManager;
import com.onurkol.app.calculator.lib.core.ThemeManager;

import java.lang.ref.WeakReference;

public class AppDataManager implements AppDataSettings {
    static WeakReference<AppPreferenceManager> prefManagerStatic;

    public static void loadApplicationData(){
        prefManagerStatic=new WeakReference<>(AppPreferenceManager.getInstance());
        // Load Preference
        loadPreferenceData();
    }

    private static void loadPreferenceData(){
        // Default Theme
        if(prefManagerStatic.get().getInt(KEY_APP_THEME)==AppPreferenceManager.INTEGER_NULL)
            prefManagerStatic.get().setPreference(KEY_APP_THEME, DEFAULT_APP_THEME);
        // Default Language
        if(prefManagerStatic.get().getInt(KEY_APP_LANGUAGE)==AppPreferenceManager.INTEGER_NULL)
            prefManagerStatic.get().setPreference(KEY_APP_LANGUAGE, DEFAULT_APP_LANGUAGE);
        // History List
        if(prefManagerStatic.get().getString(KEY_CALCULATOR_HISTORY)==null)
            prefManagerStatic.get().setPreference(KEY_CALCULATOR_HISTORY, "");
        // Set Settings
        initAppSettings();
    }

    private static void initAppSettings(){
        // Load Theme
        ThemeManager.getInstance().setAppTheme(prefManagerStatic.get().getInt(KEY_APP_THEME));
        // Load Language
        LanguageManager.getInstance().setAppLanguage(prefManagerStatic.get().getInt(KEY_APP_LANGUAGE));
    }
}
