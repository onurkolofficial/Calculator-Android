package com.onurkol.app.calculator.interfaces;

import com.onurkol.app.calculator.data.HistoryData;

import java.util.ArrayList;

public interface AppDataSettings {
    // Setting Preference Keys
    String KEY_APP_THEME="PREFERENCE_APP_THEME",
            KEY_CALCULATOR_HISTORY="PREFERENCE_CALCULATOR_HISTORY";

    // Default Values
    int DEFAULT_APP_THEME=0;

    // Lists
    ArrayList<HistoryData> HISTORY_DATA_LIST=new ArrayList<>();
}
