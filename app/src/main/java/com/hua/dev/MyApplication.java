package com.hua.dev;

import android.app.Application;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
    }

    public static void setInstance(MyApplication instance) {
        MyApplication.instance = instance;
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
