package com.onurkol.app.calculator.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.onurkol.app.calculator.BuildConfig;
import com.onurkol.app.calculator.R;

import static com.onurkol.app.calculator.data.SettingData.updateApplicationData;
import static com.onurkol.app.calculator.tools.ContextTool.setContext;

public class AboutActivity extends AppCompatActivity {

    ImageButton backSettingsButton;
    TextView settingName,appVersion;
    Button webButton, gitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set Context
        setContext(this);
        // Init Preferences & App Data
        updateApplicationData();
        // Create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Get Elements
        backSettingsButton=findViewById(R.id.backSettingsButton);
        settingName=findViewById(R.id.settingName);
        appVersion=findViewById(R.id.appVersion);
        webButton=findViewById(R.id.webButton);
        gitButton=findViewById(R.id.githubButton);

        // Get Version
        String appVersionInfo = BuildConfig.VERSION_NAME+"-"+BuildConfig.VERSION_CODE+" "+ BuildConfig.BUILD_TYPE+"";

        // Set Toolbar Title
        settingName.setText(getString(R.string.about_text));
        // Set Version Text
        appVersion.setText(appVersionInfo);

        // Button Click Events
        // Back Button
        backSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Close Activity
                finish();
            }
        });
        // Web Button
        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebActivity("https://onurkolofficial.cf");
            }
        });
        // Github Button
        gitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebActivity("https://github.com/onurkolofficial");
            }
        });
    }

    public void openWebActivity(String url){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}