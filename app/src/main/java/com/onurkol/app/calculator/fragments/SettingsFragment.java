package com.onurkol.app.calculator.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.onurkol.app.calculator.R;
import com.onurkol.app.calculator.activity.settings.SettingsAboutActivity;
import com.onurkol.app.calculator.activity.settings.SettingsLanguagesActivity;
import com.onurkol.app.calculator.activity.settings.SettingsThemesActivity;

public class SettingsFragment extends PreferenceFragmentCompat {
    Preference themesPref,langsPref,aboutPref;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Set Resource
        setPreferencesFromResource(R.xml.preference_settings,rootKey);

        // Get Preferences
        themesPref=findPreference("app_themes");
        langsPref=findPreference("app_languages");
        aboutPref=findPreference("app_about");

        // Preference Click Events
        themesPref.setOnPreferenceClickListener(preference -> {
            // Start About Activity
            startActivity(new Intent(getActivity(), SettingsThemesActivity.class));
            return false;
        });
        langsPref.setOnPreferenceClickListener(preference -> {
            // Start About Activity
            startActivity(new Intent(getActivity(), SettingsLanguagesActivity.class));
            return false;
        });
        aboutPref.setOnPreferenceClickListener(preference -> {
            // Start About Activity
            startActivity(new Intent(getActivity(), SettingsAboutActivity.class));
            return false;
        });
    }
}
