package com.onurkol.app.calculator.fragments.settings;

import android.os.Bundle;

import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.onurkol.app.calculator.R;
import com.onurkol.app.calculator.activity.SettingsActivity;
import com.onurkol.app.calculator.interfaces.AppDataSettings;
import com.onurkol.app.calculator.lib.AppPreferenceManager;

public class SettingsLanguagesFragment extends PreferenceFragmentCompat implements AppDataSettings {
    CheckBoxPreference systemLangPref,enLangPref,trLangPref;

    // Classes
    AppPreferenceManager prefManager;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Set Resource
        setPreferencesFromResource(R.xml.preference_settings_languages, rootKey);
        // Get Classes
        prefManager=AppPreferenceManager.getInstance();

        // Get Preferences
        systemLangPref=findPreference("pref_lang_system");
        enLangPref=findPreference("pref_lang_english");
        trLangPref=findPreference("pref_lang_turkish");

        // Preference Click Listener
        systemLangPref.setOnPreferenceClickListener(preference -> {
            // Change Language
            changeLanguage(0);
            return false;
        });
        enLangPref.setOnPreferenceClickListener(preference -> {
            // Change Language
            changeLanguage(1);
            return false;
        });
        trLangPref.setOnPreferenceClickListener(preference -> {
            // Change Language
            changeLanguage(2);
            return false;
        });

        // Check Default Settings
        int language_id=prefManager.getInt(KEY_APP_LANGUAGE);
        checkboxChecked(language_id);
    }

    private void changeLanguage(int langId){
        // Check Default Settings
        checkboxChecked(langId);
        // Save Preference
        prefManager.setPreference(KEY_APP_LANGUAGE,langId);
        // Recreate for language
        getActivity().recreate();
        // Set Config Status
        SettingsActivity.isConfigChanged=true;
    }

    private void checkboxChecked(int langId){
        systemLangPref.setChecked(langId==0);
        enLangPref.setChecked(langId==1);
        trLangPref.setChecked(langId==2);
    }
}
