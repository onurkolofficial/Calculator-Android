package com.onurkol.app.calculator.tools;

import android.content.Context;
import android.content.SharedPreferences;

import static com.onurkol.app.calculator.tools.ContextTool.getContext;

public class SharedPreferenceManager {
    static SharedPreferenceManager instance=null;
    static SharedPreferences preferences;

    private final String preferenceDataName="CalculatorPreferenceData";

    public final int INTEGER_NULL=-1000;

    private SharedPreferenceManager(){
        preferences=getContext().getSharedPreferences(preferenceDataName, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPreferenceManager getInstance(){
        if(instance==null)
            instance=new SharedPreferenceManager();
        return instance;
    }

    // Get Values
    public String getString(String preferenceName){
        return preferences.getString(preferenceName,null);
    }
    public int getInteger(String preferenceName){
        return preferences.getInt(preferenceName, INTEGER_NULL);
    }
    // Set Values
    public void setPreference(String preferenceName, String preferenceValue){
        preferences.edit().putString(preferenceName,preferenceValue).apply();
    }
    public void setPreference(String preferenceName, int preferenceValue){
        preferences.edit().putInt(preferenceName,preferenceValue).apply();
    }

    // Delete All Preference
    public void clearPreferences(){
        preferences.edit().clear().apply();
    }
}
