package com.onurkol.app.calculator.libs.settings;

import androidx.appcompat.app.AppCompatDelegate;

import com.onurkol.app.calculator.interfaces.AppSettingsInterface;

public class UIModeManager implements AppSettingsInterface {
    private static UIModeManager instance = null;

    public static synchronized UIModeManager getInstance(){
        if(instance == null)
            instance = new UIModeManager();
        return instance;
    }

    public void setAppUIMode(int UI_MODE){
        if(UI_MODE == _UI_MODE_DAY)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else if(UI_MODE == _UI_MODE_NIGHT)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else if(UI_MODE == _UI_MODE_AUTO)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }
}
