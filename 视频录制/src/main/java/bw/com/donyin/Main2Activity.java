package bw.com.donyin;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private EditText etPhoneNumber;
    private EditText etCode;
    private Button btnSendMsg;
    private Button btnSubmitCode;

    int i=60;
    Handler h=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==-1){
                //修改控件文本进行倒计时  i 以60秒倒计时为例
                btnSendMsg.setText( i+" s");
                i--;
                if(i>0){
                    h.sendEmptyMessageDelayed(-1,1000);
                }else{
                    //修改控件文本，进行重新发送验证码
                    btnSendMsg.setText("重新发送");
                    btnSendMsg.setClickable(true);
                    i = 60;
                }
            }else{
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                // 短信注册成功后，返回MainActivity,然后提示
                if(event==SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){

                    Toast.makeText(getApplicationContext(), "短信以注册成功！",Toast.LENGTH_SHORT).show();

                }else if(event==SMSSDK.EVENT_GET_VERIFICATION_CODE){

                    Toast.makeText(getApplicationContext(), "验证码已经发送",Toast.LENGTH_SHORT).show();

                }else if(result==SMSSDK.RESULT_ERROR){

                    try {
                        Throwable throwable = (Throwable) data;
                        throwable.printStackTrace();
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");//错误描述
                        int status = object.optInt("status");//错误代码
                        if (status > 0 && !TextUtils.isEmpty(des)) {
                            Toast.makeText(Main2Activity.this, "错误代码==="+status+",错误描述==="+des, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (Exception e) {
                        //do something
                    }
                }else if(result==SMSSDK.RESULT_COMPLETE){
                    Toast.makeText(getApplicationContext(), "验证成功！！",Toast.LENGTH_SHORT).show();
                }

            }

        }
    };

    EventHandler eventHandler = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            h.sendMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        //initSDK方法是短信SDK的入口，需要传递您从MOB应用管理后台中注册的SMSSDK的应用AppKey和AppSecrete，如果填写错误，后续的操作都将不能进行
//        SMSSDK.initSDK(Main2Activity.this, "24b829ca8f60e", "dcdccab9979ede94d7f5456e81f4a48b");
        SMSSDK.registerEventHandler(eventHandler);

    }

    private void initView() {
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etCode = (EditText) findViewById(R.id.etCode);
        btnSendMsg = (Button) findViewById(R.id.btnSendMsg);
        btnSubmitCode = (Button) findViewById(R.id.btnSubmitCode);

        btnSendMsg.setOnClickListener(this);
        btnSubmitCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSendMsg:
                String phoneNum = etPhoneNumber.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNum)) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                SMSSDK.getVerificationCode("86", phoneNum);
                btnSendMsg.setClickable(false);
                h.sendEmptyMessageDelayed(-1,1000);
                break;
            case R.id.btnSubmitCode:
                String phoneNum1 = etPhoneNumber.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNum1)) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = etCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                SMSSDK.submitVerificationCode("86", phoneNum1, code);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
