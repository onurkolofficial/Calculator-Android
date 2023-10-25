package com.onurkol.app.calculator.libs.settings;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.onurkol.app.calculator.R;
import com.onurkol.app.calculator.interfaces.AppSettingsInterface;
import com.onurkol.app.calculator.libs.AppPreferenceManager;

public class ThemeManager implements AppSettingsInterface {
    private static ThemeManager instance = null;

    private final int _MODE_BACKGROUND_TINT = 2001,
            _MODE_ICON_TINT = 2002,
            _MODE_TEXT_COLOR = 2003;


    public static synchronized ThemeManager getInstance(){
        if(instance == null)
            instance = new ThemeManager();
        return instance;
    }

    private void applyThemeUI(Context context, View object, int Mode){
        ColorStateList colorGrey = context.getColorStateList(R.color.btn_grey);
        ColorStateList colorBlue = context.getColorStateList(R.color.btn_blue);
        ColorStateList colorPurple = context.getColorStateList(R.color.btn_purple);
        ColorStateList colorPink = context.getColorStateList(R.color.btn_pink);

        int theme = getTheme(context);

        if(Mode == _MODE_BACKGROUND_TINT){
            if(theme == _THEME_COLOR_GREY)
                object.setBackgroundTintList(colorGrey);
            else if(theme == _THEME_COLOR_BLUE)
                object.setBackgroundTintList(colorBlue);
            else if(theme == _THEME_COLOR_PURPLE)
                object.setBackgroundTintList(colorPurple);
            else if(theme == _THEME_COLOR_PINK)
                object.setBackgroundTintList(colorPink);
        }
        else if(Mode == _MODE_ICON_TINT){
            if(theme == _THEME_COLOR_GREY)
                ((ImageView)object).setColorFilter(colorGrey.getDefaultColor());
            else if(theme == _THEME_COLOR_BLUE)
                ((ImageView)object).setColorFilter(colorBlue.getDefaultColor());
            else if(theme == _THEME_COLOR_PURPLE)
                ((ImageView)object).setColorFilter(colorPurple.getDefaultColor());
            else if(theme == _THEME_COLOR_PINK)
                ((ImageView)object).setColorFilter(colorPink.getDefaultColor());
        }
        else if(Mode == _MODE_TEXT_COLOR){
            if(theme == _THEME_COLOR_GREY)
                ((TextView)object).setTextColor(colorGrey);
            else if(theme == _THEME_COLOR_BLUE)
                ((TextView)object).setTextColor(colorBlue);
            else if(theme == _THEME_COLOR_PURPLE)
                ((TextView)object).setTextColor(colorPurple);
            else if(theme == _THEME_COLOR_PINK)
                ((TextView)object).setTextColor(colorPink);
        }
    }

    public void setBackgroundTint(Context context, View object){
        applyThemeUI(context, object, _MODE_BACKGROUND_TINT);
    }

    public void setIconTint(Context context, View object){
        applyThemeUI(context, object, _MODE_ICON_TINT);
    }

    public void setTextColor(Context context, View object){
        applyThemeUI(context, object, _MODE_TEXT_COLOR);
    }

    public int getTheme(Context context) {
        AppPreferenceManager appPrefManager = AppPreferenceManager.getInstance(context);
        return appPrefManager.getInt(_APP_KEY_THEME);
    }

    public int getCurrentThemeColor(Context context){
        int theme = getTheme(context);
        int color = 0;

        if(theme == _THEME_COLOR_GREY)
            color=ContextCompat.getColor(context, R.color.btn_grey);
        else if(theme == _THEME_COLOR_BLUE)
            color=ContextCompat.getColor(context, R.color.btn_blue);
        else if(theme == _THEME_COLOR_PURPLE)
            color=ContextCompat.getColor(context, R.color.btn_purple);
        else if(theme == _THEME_COLOR_PINK)
            color=ContextCompat.getColor(context, R.color.btn_pink);

        return color;
    }
}
