package com.onurkol.app.calculator.interfaces;

import com.onurkol.app.calculator.data.HistoryData;

import java.util.ArrayList;

public interface AppSettingsInterface {
    // Settings Keys
    String _APP_KEY_UI_MODE = "PREFERENCE_APP_UI_MODE",
            _APP_KEY_THEME = "PREFERENCE_APP_THEME",
            _APP_KEY_LANGUAGE = "PREFERENCE_APP_LANGUAGE",
            _APP_KEY_CALCULATOR_HISTORY = "PREFERENCE_CALCULATOR_HISTORY",
            _APP_KEY_CALCULATOR_CURRENT_HISTORY = "PREFERENCE_CALCULATOR_CURRENT_HISTORY",
            _APP_KEY_CALCULATOR_CURRENT_VALUE = "PREFERENCE_CALCULATOR_CURRENT_VALUE";

    // UI Modes
    int _UI_MODE_AUTO = 10000,
            _UI_MODE_DAY = 10001,
            _UI_MODE_NIGHT = 10002;

    // Theme Colors
    int _THEME_COLOR_GREY = 11000,
            _THEME_COLOR_BLUE = 11001,
            _THEME_COLOR_PURPLE = 11003,
            _THEME_COLOR_PINK = 11004;

    // Languages
    int _LANGUAGE_SYSTEM = 12000,
            _LANGUAGE_EN_US = 12001,
            _LANGUAGE_TR_TR = 12002;

    // Default Values
    int _APP_DEF_UI_MODE = _UI_MODE_AUTO,
            _APP_DEF_THEME = _THEME_COLOR_BLUE,
            _APP_DEF_LANGUAGE = _LANGUAGE_SYSTEM;

    ArrayList<HistoryData> HISTORY_DATA_LIST = new ArrayList<>();
}
