package com.onurkol.app.calculator.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.onurkol.app.calculator.R;
import com.onurkol.app.calculator.adapters.CalculatorButtonsPagerAdapter;
import com.onurkol.app.calculator.interfaces.AppSettingsInterface;
import com.onurkol.app.calculator.libs.AppPreferenceManager;
import com.onurkol.app.calculator.libs.AppSettingsInitializeManager;
import com.onurkol.app.calculator.libs.settings.LanguageManager;
import com.onurkol.app.calculator.libs.settings.ThemeManager;
import com.onurkol.app.calculator.libs.settings.UIModeManager;
import com.startapp.sdk.adsbase.AutoInterstitialPreferences;
import com.startapp.sdk.adsbase.StartAppAd;

public class MainActivity extends AppCompatActivity implements AppSettingsInterface {
    ThemeManager themeManager;
    AppPreferenceManager appPreferenceManager;
    UIModeManager uiModeManager;
    LanguageManager languageManager;

    DrawerLayout drawerLayout;
    LinearLayout drawerMenuLayout;
    ImageButton buttonDrawerMenu;
    TextView drawerMenuTitle;
    ViewPager2 calcButtonsPager;
    Button settingsButton, historyButton, currencyConverterButton;
    EditText calcShowPrevious, calcShowValue;

    public static boolean isConfigChanged = false,
            isLanguageChanged = false,
            isCreated = false;

    // CurrencyConverter Tool
    public static boolean thisSessionReceiptCurrencyData=false;

    public static Intent updatedIntent;

    AppUpdateManager appUpdateManager;
    Task<AppUpdateInfo> appUpdateInfoTask;

    ActivityResultLauncher<IntentSenderRequest> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppSettingsInitializeManager.onStart(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        themeManager = ThemeManager.getInstance();
        appPreferenceManager = AppPreferenceManager.getInstance(this);
        uiModeManager = UIModeManager.getInstance();
        languageManager = LanguageManager.getInstance();

        drawerLayout = findViewById(R.id.drawerLayout);
        drawerMenuLayout = findViewById(R.id.drawerMenuLayout);
        buttonDrawerMenu = findViewById(R.id.buttonDrawerMenu);
        drawerMenuTitle = findViewById(R.id.drawerMenuTitle);
        calcButtonsPager = findViewById(R.id.calcButtonsPager);
        settingsButton = findViewById(R.id.settingsButton);
        calcShowPrevious = findViewById(R.id.calcShowPrevious);
        calcShowValue = findViewById(R.id.calcShowValue);
        historyButton = findViewById(R.id.historyButton);
        currencyConverterButton = findViewById(R.id.currencyConverterButton);

        drawerMenuTitle.setText(getString(R.string.app_name));

        startAppTheme();

        calcShowValue.setShowSoftInputOnFocus(false);
        calcShowValue.requestFocus();

        String CALC_CUR_VALUE = appPreferenceManager.getString(_APP_KEY_CALCULATOR_CURRENT_VALUE);
        if(CALC_CUR_VALUE.isEmpty())
            calcShowValue.setText("0");
        else
            calcShowValue.setText(CALC_CUR_VALUE);
        calcShowValue.setSelection(calcShowValue.getText().length());

        String CALC_CUR_HISTORY = appPreferenceManager.getString(_APP_KEY_CALCULATOR_CURRENT_HISTORY);
        if(CALC_CUR_HISTORY.isEmpty())
            calcShowPrevious.setText("");
        else
            calcShowPrevious.setText(CALC_CUR_HISTORY);

        calcButtonsPager.setAdapter(new CalculatorButtonsPagerAdapter(this));

        buttonDrawerMenu.setOnClickListener(view -> drawerLayout.openDrawer(drawerMenuLayout));
        settingsButton.setOnClickListener(view -> {
            drawerLayout.close();

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });
        historyButton.setOnClickListener(view -> {
            drawerLayout.close();

            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        });
        currencyConverterButton.setOnClickListener(view -> {
            drawerLayout.close();

            Intent intent = new Intent(this, CurrencyConverterActivity.class);
            startActivity(intent);
        });
        calcShowPrevious.setOnClickListener(view -> {
            calcShowValue.setText(calcShowPrevious.getText());
            calcShowValue.setSelection(calcShowValue.getText().length());
        });

        // StartApp Ads SDK
        StartAppAd.setAutoInterstitialPreferences(
                new AutoInterstitialPreferences()
                        .setActivitiesBetweenAds(8)
        );

        // In-App Update
        appUpdateManager = AppUpdateManagerFactory.create(this);
        appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.StartIntentSenderForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Log.e("ActivityResultLauncher",
                                "Update flow failed! Result code: " + result.getResultCode());
                        // If the update is cancelled or fails,
                        // you can request to start the update again.
                    }
                });

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.
                appUpdateManager.startUpdateFlowForResult(
                        // Pass the intent that is returned by 'getAppUpdateInfo()'.
                        appUpdateInfo,
                        // an activity result launcher registered via registerForActivityResult
                        activityResultLauncher,
                        // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
                        // flexible updates.
                        AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build());
            }
            else if(appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_NOT_AVAILABLE)
                appUpdateManager.unregisterListener(installStateUpdatedListener);
        });

        appUpdateManager.registerListener(installStateUpdatedListener);

        isCreated=true;
    }

    InstallStateUpdatedListener installStateUpdatedListener = state -> {
        // (Optional) Provide a download progress bar.
        if (state.installStatus() == InstallStatus.DOWNLOADING) {
            long bytesDownloaded = state.bytesDownloaded();
            long totalBytesToDownload = state.totalBytesToDownload();
            // Implement progress bar.
        }
        else if (state.installStatus() == InstallStatus.DOWNLOADED) {
            // After the update is downloaded, show a notification
            // and request user confirmation to restart the app.
            popupSnackbarForCompleteUpdate();
        }
        // Log state or install the update.
    };

    // Displays the snackbar notification and call to action.
    private void popupSnackbarForCompleteUpdate() {
        Snackbar snackbar =
                Snackbar.make(drawerLayout,
                        getString(R.string.str_update_downloaded),
                        Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getString(R.string.str_install), view -> appUpdateManager.completeUpdate());
        snackbar.setActionTextColor(themeManager.getCurrentThemeColor(this));
        snackbar.show();
    }

    private void startAppTheme(){
        themeManager.setBackgroundTint(this, buttonDrawerMenu);
        themeManager.setTextColor(this, calcShowPrevious);
        themeManager.setTextColor(this, calcShowValue);
    }

    private void onLoadIntentData(Intent intent){
        if(intent.getBooleanExtra("LOAD_INTENT_DATA",false)){
            String getExpression=intent.getStringExtra("HISTORY_EXPRESSION"),
                    getValue=intent.getStringExtra("HISTORY_VALUE");
            calcShowPrevious.setText(getExpression);
            calcShowValue.setText(getValue);

            appPreferenceManager.setPreference(_APP_KEY_CALCULATOR_CURRENT_HISTORY, getExpression);
            appPreferenceManager.setPreference(_APP_KEY_CALCULATOR_CURRENT_HISTORY, getValue);
        }
    }

    @Override
    protected void onResume() {
        if(isConfigChanged){
            startAppTheme();
            uiModeManager.setAppUIMode(appPreferenceManager.getInt(_APP_KEY_UI_MODE));
            calcButtonsPager.setAdapter(new CalculatorButtonsPagerAdapter(this));
        }
        if(isLanguageChanged){
            languageManager.setAppLanguage(this, appPreferenceManager.getInt(_APP_KEY_LANGUAGE));
            recreate();
        }

        if(updatedIntent!=null)
            onLoadIntentData(updatedIntent);
        else
            onLoadIntentData(getIntent());

        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(appUpdateInfo -> {
                    // If the update is downloaded but not installed,
                    // notify the user to complete the update.
                    if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                        popupSnackbarForCompleteUpdate();
                    }
                });

        isLanguageChanged = false;
        isConfigChanged = false;
        super.onResume();
    }
}