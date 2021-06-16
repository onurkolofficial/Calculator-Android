package com.onurkol.app.calculator.controllers;

import com.google.gson.Gson;
import com.onurkol.app.calculator.data.HistoryData;
import com.onurkol.app.calculator.tools.SharedPreferenceManager;

import static com.onurkol.app.calculator.data.SettingData.getHistoryList;

public class HistoryController {
    // Gson
    static Gson gson=new Gson();

    static SharedPreferenceManager prefManager;

    public static void addHistory(String Expression, String Value){
        // Get Preference Manager
        prefManager=SharedPreferenceManager.getInstance();
        // Convert Values(String) to BookmarkData Type
        HistoryData getHistData=new HistoryData(Expression,Value);
        // Convert HistoryData to JSON String
        String getData="["+gson.toJson(getHistData)+"]";
        // Get Preference Data
        String oldData=prefManager.getString("CALC_HISTORY_DATA");

        String mergeData;
        String newData=getData;

        // Check Data
        if(!oldData.equals("NULL")){
            // Remove array sembol '[' & ']'
            newData=getData.substring(1,(getData.length()-1));
            oldData=oldData.substring(1,(oldData.length()-1));
            // Add new Data
            mergeData=newData+","+oldData;
            // Add Array symbol '[{data},{old},...]'
            newData="["+mergeData+"]";
        }
        // Add View
        getHistoryList().add(getHistData);

        // Save Preference
        prefManager.setPreference("CALC_HISTORY_DATA",newData);
    }

    public static void removeHistory(int index){
        // Get Preference Manager
        prefManager=SharedPreferenceManager.getInstance();
        // Get Preference Data
        String histDataString=prefManager.getString("CALC_HISTORY_DATA");
        // Get Delete Item
        HistoryData getDelHistData=getHistoryList().get(index);
        // Convert Delete HistoryData to JSON String
        String getDeleteData=gson.toJson(getDelHistData);
        String newData,deleteConvert;
        // Search Data Position
        if (histDataString.contains(getDeleteData + ","))
            deleteConvert = getDeleteData + ",";
        else if (histDataString.contains("," + getDeleteData))
            deleteConvert = "," + getDeleteData;
        else
            deleteConvert = getDeleteData;
        newData=histDataString.replace(deleteConvert,"");

        // <Fixed> If data to empty is set NULL value
        if(newData.equals("[]"))
            newData="NULL";

        // Save Preference
        prefManager.setPreference("CALC_HISTORY_DATA",newData);
    }
}
