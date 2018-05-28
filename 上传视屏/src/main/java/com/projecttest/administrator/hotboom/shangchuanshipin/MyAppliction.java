package com.projecttest.administrator.hotboom.shangchuanshipin;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by zzt on 2018/4/20.
 */

public class MyAppliction extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
    }

}
