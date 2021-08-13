package com.onurkol.app.calculator.lib.calculator.history;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onurkol.app.calculator.data.HistoryData;
import com.onurkol.app.calculator.interfaces.AppDataSettings;
import com.onurkol.app.calculator.interfaces.lib.history.HistoryManagers;
import com.onurkol.app.calculator.lib.AppPreferenceManager;
import com.onurkol.app.calculator.lib.ContextManager;
import com.onurkol.app.calculator.tools.ListToJson;

import java.util.ArrayList;

public class HistoryManager implements HistoryManagers,AppDataSettings {
    private static HistoryManager instance=null;

    // Classes
    private AppPreferenceManager prefManager;

    // Gson
    static Gson gson=new Gson();

    // Constructor
    private HistoryManager(){
        prefManager=AppPreferenceManager.getInstance();
    }

    public static synchronized HistoryManager getManager(){
        if(instance==null)
            instance=new HistoryManager();
        return instance;
    }

    @Override
    public void addHistory(String Expression, String Value) {
        // Get Preference Data
        if(HISTORY_DATA_LIST.size()<=0)
            syncSavedHistoryData();
        // Add Data
        HISTORY_DATA_LIST.add(0,new HistoryData(Expression,Value));
        // Save Preferences
        saveHistoryListPreference(HISTORY_DATA_LIST);
    }

    @Override
    public void removeHistory(int Index) {
        // Remove History
        HISTORY_DATA_LIST.remove(Index);
        // Save Preferences
        saveHistoryListPreference(HISTORY_DATA_LIST);
    }

    @Override
    public void deleteAllHistory() {
        // Clear Datas
        HISTORY_DATA_LIST.clear();
        // Save Preferences
        saveHistoryListPreference(HISTORY_DATA_LIST);
    }

    @Override
    public void saveHistoryListPreference(ArrayList<HistoryData> historyData) {
        String listData;
        if(historyData.size()<=0)
            listData="";
        else
            listData=ListToJson.getJson(historyData);
        // Save Preference
        prefManager.setPreference(KEY_CALCULATOR_HISTORY,listData);
    }

    @Override
    public ArrayList<HistoryData> getSavedHistoryData() {
        if(prefManager==null)
            prefManager=AppPreferenceManager.getInstance();
        // New Data
        ArrayList<HistoryData> savedHistoriesList=new ArrayList<>();
        // Get Preference
        String getSavedHistoriesString=prefManager.getString(KEY_CALCULATOR_HISTORY);
        if(getSavedHistoriesString!=null && !getSavedHistoriesString.equals(""))
            // Convert String to List
            savedHistoriesList=gson.fromJson(getSavedHistoriesString, new TypeToken<ArrayList<HistoryData>>(){}.getType());
        return savedHistoriesList;
    }

    @Override
    public void syncSavedHistoryData() {
        // SYNC Data
        HISTORY_DATA_LIST.clear();
        HISTORY_DATA_LIST.addAll(getSavedHistoryData());
    }
}
