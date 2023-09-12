package com.onurkol.app.calculator.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.onurkol.app.calculator.R;
import com.onurkol.app.calculator.adapters.CalculatorButtonsPagerAdapter;
import com.onurkol.app.calculator.adapters.HistoryListAdapter;
import com.onurkol.app.calculator.interfaces.AppSettingsInterface;
import com.onurkol.app.calculator.libs.AppPreferenceManager;
import com.onurkol.app.calculator.libs.AppSettingsInitializeManager;
import com.onurkol.app.calculator.libs.app.HistoryManager;
import com.onurkol.app.calculator.libs.settings.LanguageManager;
import com.onurkol.app.calculator.libs.settings.ThemeManager;
import com.onurkol.app.calculator.libs.settings.UIModeManager;

public class HistoryActivity extends AppCompatActivity implements AppSettingsInterface {
    ThemeManager themeManager;
    AppPreferenceManager appPreferenceManager;
    UIModeManager uiModeManager;
    LanguageManager languageManager;

    HistoryManager historyManager;

    HistoryListAdapter historyListAdapter;

    ImageButton settingsBackButton;
    TextView settingsTitle;
    LinearLayout noHistoryLayout;
    ListView historyListView;
    Button deleteAllHistory;

    public static boolean isConfigChanged = false,
            isLanguageChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppSettingsInitializeManager.onStart(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        themeManager = ThemeManager.getInstance();
        appPreferenceManager = AppPreferenceManager.getInstance(this);
        uiModeManager = UIModeManager.getInstance();
        languageManager = LanguageManager.getInstance();

        historyManager = HistoryManager.getManager(this);

        settingsBackButton = findViewById(R.id.settingsBackButton);
        settingsTitle = findViewById(R.id.settingsTitle);
        noHistoryLayout = findViewById(R.id.noHistoryLayout);
        historyListView = findViewById(R.id.historyListView);
        deleteAllHistory = findViewById(R.id.deleteAllHistory);

        settingsTitle.setText(getString(R.string.str_history));

        startAppTheme();

        settingsBackButton.setOnClickListener(view -> finish());

        deleteAllHistory.setOnClickListener(view -> {
            // Dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
            builder.setMessage(getString(R.string.str_delete_all_history_question))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(getString(R.string.str_yes),(dialog, which) -> {
                        historyManager.deleteAllHistory();
                        historyListView.invalidateViews();
                        checkHistoryListOrNoView();
                    })
                    .setNegativeButton(getString(R.string.str_no), null)
                    .show();
        });

        historyListAdapter = new HistoryListAdapter(this,historyListView,HISTORY_DATA_LIST);
        historyListView.setAdapter(historyListAdapter);

        checkHistoryListOrNoView();
    }

    private void startAppTheme(){
        themeManager.setBackgroundTint(this, settingsBackButton);
        themeManager.setTextColor(this, settingsTitle);
    }

    private void checkHistoryListOrNoView(){
        // Get Preference
        String histories=appPreferenceManager.getString(_APP_KEY_CALCULATOR_HISTORY);

        if(histories==null || histories.equals("")){
            // Show 'No History' Layout
            noHistoryLayout.setVisibility(View.VISIBLE);
            // Clear List
            HISTORY_DATA_LIST.clear();
        }
        else{
            // Hide 'No History' Layout
            noHistoryLayout.setVisibility(View.GONE);
            // Update Data
            historyManager.syncHistoryData();
        }
    }

    @Override
    protected void onResume() {
        if(isConfigChanged){
            startAppTheme();
            uiModeManager.setAppUIMode(appPreferenceManager.getInt(_APP_KEY_UI_MODE));
        }
        if(isLanguageChanged){
            languageManager.setAppLanguage(this, appPreferenceManager.getInt(_APP_KEY_LANGUAGE));
            recreate();
        }

        isLanguageChanged = false;
        isConfigChanged = false;
        super.onResume();
    }
}