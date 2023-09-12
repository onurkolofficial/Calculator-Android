package com.onurkol.app.calculator.libs.settings;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.onurkol.app.calculator.interfaces.AppSettingsInterface;

import java.util.Locale;

public class LanguageManager implements AppSettingsInterface {
    private static LanguageManager instance = null;

    Activity contextActivity;

    public static synchronized LanguageManager getInstance(){
        if(instance == null)
            instance = new LanguageManager();
        return instance;
    }

    public void setAppLanguage(Context context, int Language){
        // Get System Locale
        Locale systemLocale = Resources.getSystem().getConfiguration().getLocales().get(0);
        String languageCode = systemLocale.getLanguage();
        String languageCountry = systemLocale.getCountry();

        if(Language == _LANGUAGE_EN_US) {
            languageCode = "en";
            languageCountry = "US";
        }
        else if(Language == _LANGUAGE_TR_TR) {
            languageCode = "tr";
            languageCountry = "TR";
        }

        Locale locale = new Locale(languageCode,languageCountry);
        Locale.setDefault(locale);

        contextActivity = (Activity)context;

        Resources resources = contextActivity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}
