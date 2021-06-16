package com.onurkol.app.calculator.tools;

import android.app.Activity;
import android.content.Context;

import java.lang.ref.WeakReference;

public class ContextTool {
    private static WeakReference<Context> mContext;

    public static void setContext(Context context){
        mContext=new WeakReference<>(context);
    }

    public static Activity getActivity(){
        return (Activity)mContext.get();
    }
    public static Context getContext(){
        return mContext.get();
    }
}
