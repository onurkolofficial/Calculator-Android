package com.onurkol.app.calculator.interfaces.lib.history;

import com.onurkol.app.calculator.data.HistoryData;

import java.util.ArrayList;

public interface HistorySettings {
    // Preference Keys
    String KEY_HISTORY_PREFERENCE="CALCULATOR_HISTORY_PREFERENCE";

    // History List
    ArrayList<HistoryData> CALCULATOR_HISTORY_LIST=new ArrayList<>();
}
