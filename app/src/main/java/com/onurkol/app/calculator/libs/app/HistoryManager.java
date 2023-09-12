package com.onurkol.app.calculator.libs.app;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.onurkol.app.calculator.data.HistoryData;
import com.onurkol.app.calculator.interfaces.AppSettingsInterface;
import com.onurkol.app.calculator.libs.AppPreferenceManager;
import com.onurkol.app.calculator.libs.modules.ListToJson;

import java.util.ArrayList;

public class HistoryManager implements AppSettingsInterface,
        com.onurkol.app.calculator.interfaces.app.HistoryManager {
    private static HistoryManager instance = null;

    private final AppPreferenceManager appPreferenceManager;

    private HistoryManager(Context context){
        appPreferenceManager=AppPreferenceManager.getInstance(context);
    }

    public static synchronized HistoryManager getManager(Context context){
        if(instance == null)
            instance = new HistoryManager(context);
        return instance;
    }

    @Override
    public void addHistory(String Expression, String Value) {
        if(HISTORY_DATA_LIST.size() == 0)
            syncHistoryData();

        HISTORY_DATA_LIST.add(0,new HistoryData(Expression,Value));

        setHistoryListToJSON(HISTORY_DATA_LIST);
    }

    @Override
    public void removeHistory(int Index) {
        HISTORY_DATA_LIST.remove(Index);

        setHistoryListToJSON(HISTORY_DATA_LIST);
    }

    @Override
    public void deleteAllHistory() {
        HISTORY_DATA_LIST.clear();

        setHistoryListToJSON(HISTORY_DATA_LIST);
    }

    @Override
    public void setHistoryListToJSON(ArrayList<HistoryData> historyData) {
        String listData;
        if(historyData.size() == 0)
            listData = "";
        else
            listData = ListToJson.getJson(historyData);

        appPreferenceManager.setPreference(_APP_KEY_CALCULATOR_HISTORY,listData);
    }

    @Override
    public ArrayList<HistoryData> getHistoryListFromJSON() {
        ArrayList<HistoryData> savedHistoriesList = new ArrayList<>();
        // Get Preference
        String getSavedHistoriesString = appPreferenceManager.getString(_APP_KEY_CALCULATOR_HISTORY);

        if(getSavedHistoriesString != null && !getSavedHistoriesString.equals(""))
            savedHistoriesList = ListToJson.getArrayList(getSavedHistoriesString,
                    new TypeToken<ArrayList<HistoryData>>(){}.getType());
            /* .
            // Convert String to List
            savedHistoriesList = gson.fromJson(getSavedHistoriesString,
                    new TypeToken<ArrayList<HistoryData>>(){}.getType());
             */

        return savedHistoriesList;
    }

    @Override
    public void syncHistoryData() {
        HISTORY_DATA_LIST.clear();
        HISTORY_DATA_LIST.addAll(getHistoryListFromJSON());
    }
}
