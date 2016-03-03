package com.bob.lottery;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/3/2.
 */
public class BaseApplication extends Application {
    public static  Context mAppContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext=getApplicationContext();
    }
}
