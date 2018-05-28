package bw.com.donyin;

import android.app.Application;

import com.mob.MobSDK;

/**
 * Created by mypc on 2018/3/19.
 */

public class MyAppliction extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this);
    }
}
