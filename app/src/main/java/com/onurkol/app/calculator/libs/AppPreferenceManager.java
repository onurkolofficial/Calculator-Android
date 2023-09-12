package com.onurkol.app.calculator.libs;

import android.content.Context;
import android.content.SharedPreferences;

import com.onurkol.app.calculator.interfaces.AppPreferenceManagerInterface;

import java.lang.ref.WeakReference;

public class AppPreferenceManager implements AppPreferenceManagerInterface {
    private static WeakReference<AppPreferenceManager> wInstance = null;
    private static SharedPreferences preferences;

    // Variables
    public static int INT_NULL = -0x9999;

    private AppPreferenceManager(Context context){
        // Get Preferences
        preferences = context.getSharedPreferences(defaultPreferenceName, Context.MODE_PRIVATE);
    }

    public static synchronized AppPreferenceManager getInstance(Context context){
        if(wInstance == null || wInstance.get() == null)
            wInstance = new WeakReference<>(new AppPreferenceManager(context));
        return wInstance.get();
    }

    @Override
    public SharedPreferences getPreferences() {
        return preferences;
    }
    // Set
    @Override
    public void setPreference(String Name, String Value) {
        preferences.edit().putString(Name,Value).apply();
    }
    @Override
    public void setPreference(String Name, int Value) {
        preferences.edit().putInt(Name,Value).apply();
    }
    @Override
    public void setPreference(String Name, boolean Value) {
        preferences.edit().putBoolean(Name,Value).apply();
    }
    // Get
    @Override
    public String getString(String Name) {
        return preferences.getString(Name, null);
    }
    @Override
    public int getInt(String Name) {
        return preferences.getInt(Name, INT_NULL);
    }
    @Override
    public boolean getBoolean(String Name) {
        return preferences.getBoolean(Name, false);
    }
    // Clear
    @Override
    public void clearPreferences() {
        preferences.edit().clear().apply();
    }
}
