package bw.com.donyin;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import bw.com.donyin.utils.AppShortCutUtil;
import bw.com.donyin.utils.BadgeUtil;

public class MainActivity extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("jiba","====onCreate");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void add(View view) {
        Toast.makeText(MainActivity.this, "add..", Toast.LENGTH_LONG).show();
        Class<?> launchActivityName = null;
//            launchActivityName = Class.forName();
            String launchActivityName1 = AppShortCutUtil.getLaunchActivityName(this);
            AppShortCutUtil.addNumShortCut(this,launchActivityName1,true,"5",true);


    }

    public static final int NOTIFY_ID = 100;
    public void fa(View view) {
        try {
            final int count = Integer.parseInt("20");
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                    .setSmallIcon(getApplicationInfo().icon)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true);

            mBuilder.setContentTitle("test");
            mBuilder.setTicker("test");
            mBuilder.setContentText("test");

            //点击set 后，app退到桌面等待3s看效果（有的launcher当app在前台设置未读数量无效）
            final Notification notification = mBuilder.build();
            new Handler(getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    BadgeUtil.sendBadgeNotification(notification, NOTIFY_ID, getApplicationContext(), count, count);
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                }
            }, 3 * 1000);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void xian(View view) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                Log.i("jiba","B====="+this.getName());
                Looper.prepare(); //创建子线程的looper
                handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        String obj = (String) msg.obj;
                        Log.i("jiba","这是由"+obj+"发送的消息");
                    }
                };
                Looper.loop();//开始轮循
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                super.run();
                Log.i("jiba","A===="+ this.getName());
                Message obtain = Message.obtain();
                obtain.obj="A线程";
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendMessage(obtain);
            }
        }.start();

    }

    public void shiping(View view) {
        Intent intent = new Intent(MainActivity.this, Main3Activity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
