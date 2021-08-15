package com.onurkol.app.calculator.lib.core;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.onurkol.app.calculator.lib.ContextManager;

import java.util.Locale;

public class LanguageManager {
    private static LanguageManager instance=null;

    // Variables
    private boolean isRecreate=false;

    // Constructor
    private LanguageManager(){}

    public static synchronized LanguageManager getInstance(){
        if(instance==null)
            instance=new LanguageManager();
        return instance;
    }

    public void setAppLanguage(int Language){
        // Get System Locale
        Locale systemLocale=Resources.getSystem().getConfiguration().locale;
        String languageCode=null;
        String languageCountry=null;
        if(Language==0) {
            systemLocale=Resources.getSystem().getConfiguration().locale;
            languageCode=systemLocale.getLanguage();
            languageCountry=systemLocale.getCountry();
        }
        else if(Language==1) {
            languageCode="en";
            languageCountry="US";
        }
        else if(Language==2) {
            languageCode = "tr";
            languageCountry="TR";
        }

        Locale locale = new Locale(languageCode,languageCountry);
        Locale.setDefault(locale);
        Activity contextActivity;
        if(ContextManager.getManager().getContextActivity()!=null)
            contextActivity=ContextManager.getManager().getContextActivity();
        else
            contextActivity=ContextManager.getManager().getBaseContextActivity();
        Resources resources = contextActivity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // Check first start to change application language
        if(!systemLocale.getLanguage().equals(locale.getLanguage()) && !isRecreate){
            isRecreate=true;
            contextActivity.recreate();
        }
    }
}
