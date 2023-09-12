package com.onurkol.app.calculator.activity.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.onurkol.app.calculator.R;
import com.onurkol.app.calculator.libs.AppSettingsInitializeManager;
import com.onurkol.app.calculator.libs.settings.ThemeManager;

public class AboutActivity extends AppCompatActivity {
    ThemeManager themeManager;

    ImageButton settingsBackButton;
    TextView settingsTitle, versionTitle, contactTitle,
            appVersionSummary, osVersionSummary, devWebPageSummary;
    LinearLayoutCompat openWebButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppSettingsInitializeManager.onStart(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_about);

        themeManager = ThemeManager.getInstance();

        settingsBackButton = findViewById(R.id.settingsBackButton);
        settingsTitle = findViewById(R.id.settingsTitle);
        versionTitle = findViewById(R.id.versionTitle);
        contactTitle = findViewById(R.id.contactTitle);
        appVersionSummary = findViewById(R.id.appVersionSummary);
        osVersionSummary = findViewById(R.id.osVersionSummary);
        openWebButton = findViewById(R.id.openWebButton);
        devWebPageSummary = findViewById(R.id.devWebPageSummary);

        settingsTitle.setText(getString(R.string.str_about_title));

        String appPackage;
        String appVersion = "null";
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            appVersion = pInfo.versionName + " FREE ";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        
        String androidVersion = Build.VERSION.RELEASE+" - API "+ Build.VERSION.SDK_INT;
        String developerWebPage = "https://onurkolofficial.epizy.com/en";

        appVersionSummary.setText(appVersion);
        osVersionSummary.setText(androidVersion);
        devWebPageSummary.setText(developerWebPage);

        startAppTheme();

        settingsBackButton.setOnClickListener(view -> finish());
        openWebButton.setOnClickListener(view -> openWebActivity(developerWebPage));
    }

    private void startAppTheme(){
        themeManager.setBackgroundTint(this, settingsBackButton);
        themeManager.setTextColor(this, settingsTitle);
        themeManager.setTextColor(this, versionTitle);
        themeManager.setTextColor(this, contactTitle);
    }

    public void openWebActivity(String url){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}