package com.onurkol.app.calculator.lib;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.lang.ref.WeakReference;

public class ContextManager {
    private static WeakReference<ContextManager> instance=null;
    private final Context mContext;
    private static WeakReference<Context> baseContextStatic;
    private static boolean base;

    private ContextManager(Context context){
        if(base)
            baseContextStatic=new WeakReference<>(context);
        mContext=context;
    }

    public static synchronized void Build(Context context){
        base=false;
        instance=new WeakReference<>(new ContextManager(context));
    }

    public static synchronized void BuildBase(Context context){
        base=true;
        instance=new WeakReference<>(new ContextManager(context));
    }

    public static synchronized ContextManager getManager(){
        return instance.get();
    }

    public Context getContext(){
        return mContext;
    }
    public Activity getContextActivity(){
        return ((Activity)mContext);
    }

    public Context getBaseContext(){
        return (baseContextStatic!=null ? baseContextStatic.get() : null);
    }
    public Activity getBaseContextActivity(){
        return (baseContextStatic!=null ? (Activity)baseContextStatic.get() : null);
    }
    public FragmentManager getBaseFragmentManager(){ return (baseContextStatic!=null ? ((FragmentActivity)baseContextStatic.get()).getSupportFragmentManager() : null); }
}
