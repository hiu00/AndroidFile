package com.example.mymusic.listener;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mymusic.util.LogUtil;

/**
 * 网络请求Observer
 */
public abstract class HttpObserver<T> extends ObserverAdapter<T>{
    private static final String TAG = "HttpObserver";

    /**
     * 请求成功
     * @param data
     */
    public abstract void onSuccessed(T data);

    /**
     * 请求失败
     * @param t
     * @param e
     * @return
     */
    public boolean onFailed(T t,Throwable e){
        return false;
    }

    @Override
    public void onNext(@NonNull T t) {
        super.onNext(t);
        LogUtil.d(TAG,"onNext:"+t);

        //TODO 处理错误

        //请求正常
        onSuccessed(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        super.onError(e);
        Log.d(TAG, "onError: "+e.getLocalizedMessage());

        //TODO 处理错误
    }
}
