package com.onurkol.app.calculator.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.onurkol.app.calculator.R;
import com.onurkol.app.calculator.activity.AboutActivity;
import com.onurkol.app.calculator.data.SettingData;
import com.onurkol.app.calculator.tools.SharedPreferenceManager;

import java.util.List;

import static com.onurkol.app.calculator.data.SettingData.getThemeList;
import static com.onurkol.app.calculator.data.SettingData.updateApplicationData;

public class SettingsFragment extends PreferenceFragmentCompat {

    SharedPreferenceManager prefManager;

    Preference aboutPref;
    ListPreference themesPref;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Set Resource
        setPreferencesFromResource(R.xml.settings_preference,rootKey);

        // Get Preference Manager
        prefManager=SharedPreferenceManager.getInstance();

        // Get Preferences
        aboutPref=findPreference("app_about");
        themesPref=findPreference("app_theme");

        // Add Theme List Preference
        List<String> themes=getThemeList();
        CharSequence[] themeEntries=new String[themes.size()];
        CharSequence[] themeEntryValues=new String[themes.size()];
        // Adding Themes
        for(int i=0; i<themes.size(); i++){
            themeEntries[i]=themes.get(i);
            themeEntryValues[i]=String.valueOf(i);
        }
        // Set Entries
        themesPref.setEntries(themeEntries);
        themesPref.setEntryValues(themeEntryValues);
        // Set Default Value
        themesPref.setValue(String.valueOf(prefManager.getInteger("APP_THEME")));

        // Set Listeners
        themesPref.setOnPreferenceChangeListener(themeChangeListener);
        aboutPref.setOnPreferenceClickListener(aboutClickListener);
    }

    Preference.OnPreferenceChangeListener themeChangeListener=new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            // Save new value to Preference Data.
            prefManager.setPreference("APP_THEME",Integer.parseInt(newValue.toString()));
            // Update Data
            updateApplicationData();
            // Apply Set Theme Changed
            SettingData.isThemeChanged=true;
            getActivity().recreate();
            return false;
        }
    };

    Preference.OnPreferenceClickListener aboutClickListener=new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            // Start About Activity
            startActivity(new Intent(getActivity(), AboutActivity.class));
            return false;
        }
    };
}