package com.softtech.android.displaydata.app;


import android.app.Application;
import android.content.Context;

public class AppSingleton extends Application {
    private static AppSingleton appInstance;
    public static final String TAG = AppSingleton.class.getSimpleName();
    private static Context mContext;

    public static AppSingleton getInstance() {
        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        mContext = this;
    }

    public static Context getContext() {
        return getInstance().getBaseContext();
    }
}

