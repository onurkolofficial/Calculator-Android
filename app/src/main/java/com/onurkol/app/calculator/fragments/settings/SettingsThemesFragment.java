package com.onurkol.app.calculator.fragments.settings;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.onurkol.app.calculator.R;
import com.onurkol.app.calculator.interfaces.AppDataSettings;
import com.onurkol.app.calculator.lib.AppDataManager;
import com.onurkol.app.calculator.lib.AppPreferenceManager;

public class SettingsThemesFragment extends PreferenceFragmentCompat implements AppDataSettings {
    CheckBoxPreference systemThemePref,lightThemePref,darkThemePref;

    // Classes
    AppPreferenceManager prefManager;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Set Resource
        setPreferencesFromResource(R.xml.preference_settings_themes, rootKey);
        // Get Classes
        prefManager=AppPreferenceManager.getInstance();

        // Get Preferences
        systemThemePref=findPreference("pref_system_theme");
        lightThemePref=findPreference("pref_light_theme");
        darkThemePref=findPreference("pref_dark_theme");

        // API 29 and and oldest versions not supported System Theme.
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
            systemThemePref.setVisible(false);

        // Preference Click Listener
        systemThemePref.setOnPreferenceClickListener(preference -> {
            systemThemePref.setChecked(true);
            lightThemePref.setChecked(false);
            darkThemePref.setChecked(false);
            // Save Preference
            prefManager.setPreference(KEY_APP_THEME,2);
            // Refresh Application
            AppDataManager.loadApplicationData();
            return false;
        });
        lightThemePref.setOnPreferenceClickListener(preference -> {
            systemThemePref.setChecked(false);
            lightThemePref.setChecked(true);
            darkThemePref.setChecked(false);
            // Save Preference
            prefManager.setPreference(KEY_APP_THEME,0);
            // Refresh Application
            AppDataManager.loadApplicationData();
            return false;
        });
        darkThemePref.setOnPreferenceClickListener(preference -> {
            systemThemePref.setChecked(false);
            lightThemePref.setChecked(false);
            darkThemePref.setChecked(true);
            // Save Preference
            prefManager.setPreference(KEY_APP_THEME,1);
            // Refresh Application
            AppDataManager.loadApplicationData();
            return false;
        });

        // Check Default Settings
        int theme_id=prefManager.getInt(KEY_APP_THEME);
        systemThemePref.setChecked(theme_id==2);
        lightThemePref.setChecked(theme_id==0);
        darkThemePref.setChecked(theme_id==1);
    }
}
