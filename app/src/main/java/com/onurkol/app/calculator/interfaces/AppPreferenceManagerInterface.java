package com.onurkol.app.calculator.interfaces;

import android.content.SharedPreferences;

public interface AppPreferenceManagerInterface {
    String defaultPreferenceName = "app.preference.data";

    SharedPreferences getPreferences();

    // Set/Get
    void setPreference(String Name, String Value);
    void setPreference(String Name, int Value);
    void setPreference(String Name, boolean Value);

    String getString(String Name);
    int getInt(String Name);
    boolean getBoolean(String Name);

    void clearPreferences();
}
