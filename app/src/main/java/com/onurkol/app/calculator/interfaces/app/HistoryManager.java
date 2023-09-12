package com.onurkol.app.calculator.interfaces.app;

import com.onurkol.app.calculator.data.HistoryData;

import java.util.ArrayList;

public interface HistoryManager {
    void addHistory(String Expression, String Value);
    void removeHistory(int Index);
    void deleteAllHistory();
    void setHistoryListToJSON(ArrayList<HistoryData> historyData);
    ArrayList<HistoryData> getHistoryListFromJSON();
    void syncHistoryData();
}
