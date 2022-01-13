package com.example.mymusic.listener;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mymusic.domain.response.BaseResponse;
import com.example.mymusic.util.HttpUtil;
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

        if (isSucceeded(t)) {
            //请求正常
            onSuccessed(t);
        }else {
            //有状态码
            //表示请求出错了
            handlerRequest(t, null);
        }


    }

    /**
     * 是否请求成功
     * 有状态码表示请求失败
     * @param t
     * @return
     */
    private boolean isSucceeded(T t) {
        if (t instanceof BaseResponse){//instanceof用作对象的判断
            //判断具体的业务请求
            BaseResponse baseResponse= (BaseResponse) t;

            //没有状态码表示请求成功
            //这是服务端的规定
            return baseResponse.getStatus()==null;
        }
        return false;
    }

    /**
     * 处理请求错误
     *
     * @param t     请求返回的对象
     * @param e 错误信息
     */
    private void handlerRequest(T t, Throwable e) {
        if (onFailed(t,e)){
            //回调了请求失败方法
            //并且该方法返回了true

            //返回true就表示外部手动处理错误
            //那我们框架内部就不用做任何事情了
        }
        else {
            HttpUtil.handlerRequest(t,e);
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        super.onError(e);
        Log.d(TAG, "onError: "+e.getLocalizedMessage());

        //处理错误
        handlerRequest(null,e);
    }
}
