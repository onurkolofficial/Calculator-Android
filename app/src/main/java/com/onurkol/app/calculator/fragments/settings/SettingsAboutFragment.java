package com.onurkol.app.calculator.fragments.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.onurkol.app.calculator.BuildConfig;
import com.onurkol.app.calculator.R;

public class SettingsAboutFragment extends PreferenceFragmentCompat {
    Preference appPackagePref,appVersionPref,androidVersionPref,openDevWebPref;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Set Resource
        setPreferencesFromResource(R.xml.preference_settings_about, rootKey);

        // Get Version Data
        String appPackage=getString(R.string.app_package_free_text)+" -"+ BuildConfig.BUILD_TYPE+"/free";
        String appVersion=BuildConfig.VERSION_NAME+" - "+BuildConfig.VERSION_CODE;
        String androidVersion=Build.VERSION.RELEASE+" - API "+Build.VERSION.SDK_INT;
        String developerWebPage="https://onurkolofficial.cf/en";

        // Get Preferences
        appPackagePref=findPreference("pref_app_package");
        appVersionPref=findPreference("pref_app_version");
        androidVersionPref=findPreference("pref_android_version");
        openDevWebPref=findPreference("pref_dev_web_page");

        // Set Summary Texts
        appPackagePref.setSummary(appPackage);
        appVersionPref.setSummary(appVersion);
        androidVersionPref.setSummary(androidVersion);

        // Preference Click Listener
        openDevWebPref.setOnPreferenceClickListener(preference -> {
            // Get Intent
            openWebActivity(developerWebPage);
            return false;
        });
    }

    public void openWebActivity(String url){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
