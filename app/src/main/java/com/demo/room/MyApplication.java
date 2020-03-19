package com.demo.room;

import android.app.Application;

/**
 * Created by guoxiaodong on 2020/3/18 17:11
 */
public class MyApplication extends Application {
    public static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
