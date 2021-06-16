package com.onurkol.app.calculator.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.onurkol.app.calculator.R;
import com.onurkol.app.calculator.adapters.OKFragmentPagerAdapter;
import com.onurkol.app.calculator.data.SettingData;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.ads.banner.BannerListener;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import static com.onurkol.app.calculator.data.SettingData.updateApplicationData;
import static com.onurkol.app.calculator.tools.ContextTool.getContext;
import static com.onurkol.app.calculator.tools.ContextTool.setContext;

public class MainActivity extends AppCompatActivity {

    EditText calcProcess, calcValue;
    ImageButton openMenuButton, closeAppButton;
    ViewPager2 calcButtonPager;
    Button settingsButton, historyButton;

    LinearLayout calcDrawerMenuLayout;
    DrawerLayout calcDrawerMenu;

    public static boolean isCreate=false;
    public static Intent updatedIntent=null;

    // Update Variables
    private AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set Context
        setContext(this);
        // Init Preferences & App Data
        updateApplicationData();
        // Create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set isCreate value
        isCreate=true;

        // Ads Initialize
        String getAppId=getString(R.string.startapp_app_id);
        StartAppSDK.init(this, getAppId, false);
        // Disable Startapp Splash Screen.
        StartAppAd.disableSplash();
        // Get Banner
        final Banner appBanner=findViewById(R.id.startAppBanner);
        // Set Listener
        appBanner.setBannerListener(new BannerListener() {
            @Override
            public void onReceiveAd(View view) {
                appBanner.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFailedToReceiveAd(View view) {
                appBanner.setVisibility(View.GONE);
            }
            @Override
            public void onImpression(View view) {}
            @Override
            public void onClick(View view) {}
        });
        // Hide Default
        appBanner.setVisibility(View.GONE);

        // Get Elements
        calcProcess=findViewById(R.id.calcShowProcess);
        calcValue=findViewById(R.id.calcShowValue);
        openMenuButton=findViewById(R.id.openMenuButton);
        closeAppButton=findViewById(R.id.closeAppButton);
        calcButtonPager=findViewById(R.id.calcButtonPager);
        calcDrawerMenuLayout=findViewById(R.id.calcDrawerMenuLayout);
        calcDrawerMenu=findViewById(R.id.calcDrawerMenu);
        settingsButton=findViewById(R.id.settingsButton);
        historyButton=findViewById(R.id.historyButton);

        // Load Calculator Data
        loadGetIntentData(this.getIntent());

        // Button Click Events
        // Close App
        closeAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        // Open Menu
        openMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcDrawerMenu.openDrawer(calcDrawerMenuLayout);
            }
        });
        // Start Settings Activity
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Close Drawer
                calcDrawerMenu.closeDrawer(calcDrawerMenuLayout);
                // Start Activity
                startActivity(new Intent(getContext(),SettingsActivity.class));
            }
        });
        // Start History Activity
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Close Drawer
                calcDrawerMenu.closeDrawer(calcDrawerMenuLayout);
                // Start Activity
                startActivity(new Intent(getContext(),HistoryActivity.class));
            }
        });
        // Init App
        // Set Pager Adapter
        calcButtonPager.setAdapter(new OKFragmentPagerAdapter(this));
        // Disable Keyboard & Focus
        calcValue.setShowSoftInputOnFocus(false);
        calcValue.requestFocus();
    }

    private void loadGetIntentData(Intent intent){
        if(intent.getBooleanExtra("LOAD_INTENT_DATA",false)){
            calcProcess.setText(intent.getStringExtra("HISTORY_EXPRESSION"));
            calcValue.setText(intent.getStringExtra("HISTORY_VALUE"));
        }
    }

    @Override
    protected void onResume() {
        // Check Theme Changed
        if(SettingData.isThemeChanged){
            SettingData.isThemeChanged=false;
            this.recreate();
        }
        // Check Updated Intent
        if(updatedIntent!=null){
            // Load Calculator Data
            loadGetIntentData(updatedIntent);
        }
        super.onResume();
    }

    @Override
    protected void onStart() {
        // Create App Update Manager
        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        // Register Listener
        mAppUpdateManager.registerListener(installStateUpdatedListener);

        // Update Manager Listener
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && result.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/)){
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(result, AppUpdateType.FLEXIBLE, MainActivity.this, RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }

                } else if (result.installStatus() == InstallStatus.DOWNLOADED){
                    //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                    popupSnackbarForCompleteUpdate();
                }
                /* else {
                    //Log.e(TAG, "checkForAppUpdateAvailability: something else");
                }*/
            }
        });
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (mAppUpdateManager != null)
            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_APP_UPDATE) {
            if (resultCode != RESULT_OK)
                Toast.makeText(this, getString(R.string.update_download_failed), Toast.LENGTH_LONG).show();
        }
    }

    // Updater Popup
    private void popupSnackbarForCompleteUpdate() {
        // Snackbar
        Snackbar snackbar = Snackbar.make(
                findViewById(R.id.calcDrawerMenu),
                getString(R.string.update_new_version_available),
                Snackbar.LENGTH_INDEFINITE);
        // Snackbar Action
        snackbar.setAction(getString(R.string.install_text), view -> {
            if (mAppUpdateManager != null)
                mAppUpdateManager.completeUpdate();
        });
        // Show Snackbar
        snackbar.setActionTextColor(ContextCompat.getColor(this,R.color.primary));
        snackbar.show();
    }

    // Update Listener
    InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(InstallState state) {
            if (state.installStatus() == InstallStatus.DOWNLOADED){
                //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                popupSnackbarForCompleteUpdate();
            } else if (state.installStatus() == InstallStatus.INSTALLED){
                if (mAppUpdateManager != null)
                    mAppUpdateManager.unregisterListener(installStateUpdatedListener);
                // Show Message
                Toast.makeText(MainActivity.this, getString(R.string.update_install_completed), Toast.LENGTH_SHORT).show();
            }
            /* else {
                //Log.e(TAG, "InstallStateUpdatedListener: state: " + state.installStatus());
            } */
        }
    };
}