package com.onurkol.app.calculator.interfaces.lib.history;

import com.onurkol.app.calculator.data.HistoryData;

import java.util.ArrayList;

public interface HistoryManagers {
    void addHistory(String Expression, String Value);
    void removeHistory(int Index);
    void deleteAllHistory();
    void saveHistoryListPreference(ArrayList<HistoryData> historyData);
    ArrayList<HistoryData> getSavedHistoryData();
    void syncSavedHistoryData();
}
