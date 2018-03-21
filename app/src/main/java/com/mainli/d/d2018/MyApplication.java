package com.mainli.d.d2018;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;


/**
 * Application基类
 * Created by shixiaoming on 16/12/6.
 */

public class MyApplication extends Application{

    private static Context mContext;

    @Override public void onCreate() {
        super.onCreate();
        MyApplication.mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.mContext;
    }

    public static String getAndroidId() {
        return Settings.Secure.getString(
                getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
