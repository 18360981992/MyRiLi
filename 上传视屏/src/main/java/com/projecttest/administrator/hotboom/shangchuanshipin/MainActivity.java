package com.projecttest.administrator.hotboom.shangchuanshipin;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.projecttest.administrator.hotboom.shangchuanshipin.apis.APIs;
import com.projecttest.administrator.hotboom.shangchuanshipin.services.ApiService;
import com.projecttest.administrator.hotboom.shangchuanshipin.utils.BaseServer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity implements BDLocationListener {

    private VideoView vv;

    private File videoFile;  // 视屏资源
    private File coverFile;  // 视屏封面
    private String videoDesc = "这个视屏非常好看！";  // 视屏描述
    private String uid = "13563";  // uid   废物的id
    private String token = "26BD55D2E872C840EA697151F31BE06C";  // 用户令牌

//    private String uid = "12400";  // uid    废物的id
//    private String token = "51690705FDAC4132A3BD7453D134682D";  // 用户令牌

//    private String uid = "13664";  // uid   我的id
//    private String token = "EDBC3B54F2E9E375E50A76E8E3F8E483";  // 用户令牌

    private String latitude="39.95";   // 纬度
    private String longitude="116.30";   // 经度

    private LocationClient mLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File f1 = Environment.getExternalStorageDirectory();
        videoFile = new File(f1, "baby.mp4");   // 视屏资源
        File f2 = Environment.getExternalStorageDirectory();
        coverFile = new File(f2, "nv.png");

        initDingWei();//设置定位功能  开启/关闭

        vv = (VideoView) findViewById(R.id.vv);
        MediaController mc = new MediaController(this);
        vv.setVideoPath(videoFile.getPath());
        mc.setMediaPlayer(vv);
        vv.setMediaController(mc);
    }

    //设置定位功能  初始化定位功能
    private void initDingWei(){
        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(this);//注册监听函数
        initLocation(); //初始化定位参数  封装在类中
//        mBaiduMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase
        mLocationClient.start();//开启定位
    }

    // 设置定位的基本功能  比如精度模式，是否随时保持定位。。。
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("gcj02");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }


    /**
     * 上传视屏所需参数如下
     *   uid  	    string	是	用户id       13563
         videoFile	File	是	视频文件
         coverFile	File	是	视频封面
         videoDesc	String	否	视频描述
         latitude	String	是	高德纬度坐标
         longitude	String	是	高德经度坐标
         token	    String	是	用户令牌      26BD55D2E872C840EA697151F31BE06C
     *
     *  写的是个demo所有我把所有值都写成死的了，除了经纬度
     *
     * @param view
     */
    public void chuan(View view) {
        if(!latitude.equals("")&&!longitude.equals("")){
            Log.i("jiba",latitude+"====="+longitude);
            Log.i("jiba","videoFile====="+videoFile);
            Log.i("jiba","coverFile====="+coverFile);

            // 这是为了打印retrofit的log日志
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    //打印retrofit日志
                    Log.i("RetrofitLog","retrofitBack = "+message);
                }
            });
            // 对log 的初始化
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            // log 属于拦截器 所以需要将他注入okhttp中
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();
            // 初始化retrofit
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIs.debug)
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            // 通过反射获得 apiservice接口
            ApiService apiService = retrofit.create(ApiService.class);

            // 将参数整合 拼接
            HashMap<String, String> map = new HashMap<>();
            map.put("uid",uid);
            map.put("videoDesc",videoDesc);
            map.put("latitude",latitude);
            map.put("longitude",longitude);
            map.put("token",token); // ?source=android&appVersion=101
            map.put("source","android");
            map.put("appVersion","101");

            String oldUrl = APIs.SHANGCHUAN;
            StringBuilder stringBuilder = new StringBuilder();//创建一个stringBuilder
            stringBuilder.append(oldUrl);

            if (oldUrl.contains("?")){
                //?在最后面....2类型
                if (oldUrl.indexOf("?") == oldUrl.length()-1){

                }else {
                    //3类型...拼接上&
                    stringBuilder.append("&");
                }
            }else {
                //不包含? 属于1类型,,,先拼接上?号
                stringBuilder.append("?");
            }

            for (Map.Entry<String,String> entry: map.entrySet()) {
                //拼接
                stringBuilder.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }
            //删掉最后一个&符号
            if (stringBuilder.indexOf("&") != -1){
                stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("&"));
            }
            String newUrl = stringBuilder.toString();//新的路径

            // 凡是经过file上传的参数，我都需要对其进行指示，也可以理解为对他进行备注，转码

            // 这这个接口中，我们发现有两个参数是通过file类型上传的，所以我们可以理解为这是一个多文件上传

            // 首先是对文件视频的备注 ，

            // 备注分为三个步骤 1，转为请求体 2.指定真正的参数名 与 实际名   3. 为file类型添加描述
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), videoFile);

            // 2.指定真正的参数名 与 实际名
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("videoFile", videoFile.getName(), requestFile);

            // 3. 为file类型添加字段描述
            String descriptionString = "hello, 这是文件视屏";
            RequestBody description =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), descriptionString);

            // 为文件封面进行转码，备注

            RequestBody requestFile1 =
                    RequestBody.create(MediaType.parse("multipart/form-data"), coverFile);

            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body1 =
                    MultipartBody.Part.createFormData("coverFile", coverFile.getName(), requestFile1);

            // 添加描述
            String descriptionString1 = "hello, 这是文件封面";
            RequestBody description1 =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), descriptionString1);

            /**
             *  newUrl == 请求路径
             *
             *  description == 为视屏文件的字段描述
             *
             *  body == 为视频文件的请求体
             *
             *  description1== 为封面文件的字段描述
             *
             *  body1==  为封面文件的请求体
             *
             */
            Observable<String> responseBodyCall = apiService.uploadFile(newUrl,description,body,description1,body1);

            responseBodyCall.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseServer() {
                                @Override
                                public void onSuccess(String json) {

                                    Log.i("jiba","这是成功的===="+json);
                                }

                                @Override
                                public void onErroy(String ss) {
                                    Log.i("jiba","这是失败的===="+ss);
                                }
                            });


        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        latitude=location.getLatitude()+"";
        longitude=location.getLongitude()+"";
    }


    /**
     *
     * 头像上传
     *  uid	    string	是	用户id
        file	File	是	文件
        token	String	是	用户令牌

     * @param view
     */

    public void chuantou(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIs.debug)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        //addFormDataPart()第一个参数为表单名字，这是和后台约定好的
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("uid", uid);
        //注意，file是后台约定的参数，如果是多图，file[]，如果是单张图片，file就行
        builder.addFormDataPart("file", coverFile.getName(),
                RequestBody.create(MediaType.parse("image/png"),coverFile));
        RequestBody requestBody = builder.build();

        Observable<String> upload = apiService.upload(requestBody);
        upload.subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(new BaseServer() {
                    @Override
                    public void onSuccess(String json) {
                        Log.i("jiba","头像上传成功====="+json);
                    }

                    @Override
                    public void onErroy(String ss) {
                        Log.i("jiba","头像上传失败====="+ss);
                    }
                });

    }
}
