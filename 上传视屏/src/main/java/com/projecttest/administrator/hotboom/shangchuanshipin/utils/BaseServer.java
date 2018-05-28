package com.projecttest.administrator.hotboom.shangchuanshipin.utils;


import android.support.annotation.NonNull;
import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by mypc on 2018/1/6.
 *
 * 父类的公有方法的抽取，，抽象的思想
 */

public abstract class BaseServer implements Observer<String> {

    public abstract void onSuccess(String json);
    public abstract void onErroy(String ss);

    @Override
    public void onError(Throwable e) {
        onErroy("请求失败");
        String message = e.getMessage();
        String localizedMessage = e.getLocalizedMessage();


        Log.i("jiba","message===="+message+",localizedMessage===="+localizedMessage);
    }

    @Override
    public void onNext(String json) {
        try {
//            Log.i("jiba","onNext==="+json);
            onSuccess(json);
        }catch (Exception e1) {

        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onComplete() {

    }
}
