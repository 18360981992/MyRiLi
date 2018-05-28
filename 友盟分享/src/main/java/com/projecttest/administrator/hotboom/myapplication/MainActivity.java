package com.projecttest.administrator.hotboom.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.umeng.socialize.bean.SHARE_MEDIA;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void qq(View view) {
        ShareUtils.shareWeb(this, Defaultcontent.url1, Defaultcontent.title1
                , Defaultcontent.text1, Defaultcontent.imageurl1, R.drawable.pp, SHARE_MEDIA.QQ
        );
    }

    public void weiXin(View view) {
    }

    public void weixinCircle(View view) {
    }

    public void sina(View view) {
    }

    public void Qzone(View view) {
        ShareUtils.shareWeb(this, Defaultcontent.url1, Defaultcontent.title1
                , Defaultcontent.text1, Defaultcontent.url1, R.mipmap.ic_launcher, SHARE_MEDIA.QZONE
        );
    }
}
