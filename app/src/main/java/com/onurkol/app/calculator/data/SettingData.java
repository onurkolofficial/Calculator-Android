package com.onurkol.app.calculator.data;

import com.onurkol.app.calculator.R;
import com.onurkol.app.calculator.tools.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import static com.onurkol.app.calculator.tools.ContextTool.getContext;

public class SettingData {
    static SharedPreferenceManager prefManager;

    public static boolean isThemeChanged=false;

    // App Settings Data
    // List Data
    private static final List<String> APP_THEME_LIST=new ArrayList<>();
    // History Data
    private static final List<HistoryData> CALC_HISTORY_LIST=new ArrayList<>();

    // Themes
    public static void updateThemeList(){
        // Themes
        if(APP_THEME_LIST.size()<=0) {
            APP_THEME_LIST.add(0, getContext().getString(R.string.light_theme_text));
            APP_THEME_LIST.add(1, getContext().getString(R.string.dark_theme_text));
        }
    }

    public static List<String> getThemeList(){
        return APP_THEME_LIST;
    }

    public static List<HistoryData> getHistoryList(){
        return CALC_HISTORY_LIST;
    }

    public static void updateApplicationData(){
        // Get Preference Manager
        prefManager=SharedPreferenceManager.getInstance();

        // App Theme
        if(prefManager.getInteger("APP_THEME")==prefManager.INTEGER_NULL)
            prefManager.setPreference("APP_THEME",0);
        // History
        if(prefManager.getString("CALC_HISTORY_DATA")==null)
            prefManager.setPreference("CALC_HISTORY_DATA","NULL");

        applyApplicationTheme();
    }

    private static void applyApplicationTheme(){
        int themeid=prefManager.getInteger("APP_THEME");
        if(themeid==0){
            getContext().setTheme(R.style.LightTheme);
        }
        else if(themeid==1){
            getContext().setTheme(R.style.DarkTheme);
        }

    }
}
