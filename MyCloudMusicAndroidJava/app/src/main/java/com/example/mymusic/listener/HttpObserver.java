package com.example.mymusic.listener;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mymusic.activity.BaseCommonActivity;
import com.example.mymusic.domain.response.BaseResponse;
import com.example.mymusic.util.HttpUtil;
import com.example.mymusic.util.LoadingUtil;
import com.example.mymusic.util.LogUtil;

import io.reactivex.disposables.Disposable;

/**
 * 网络请求Observer
 */
public abstract class HttpObserver<T> extends ObserverAdapter<T>{
    private static final String TAG = "HttpObserver";

    /**
     * 是否显示加载对话框
     */
    private boolean isShowLoading;

    /**
     * 界面
     */
    private  BaseCommonActivity activity;

    /**
     * 无参构造方法
     */
    public HttpObserver(){

    }

    /**
     * 可以控制是否显示的构造方法
     * @param activity
     * @param isShowLoading 是否显示加载对话框
     */
    public HttpObserver(BaseCommonActivity activity,boolean isShowLoading){
        this.activity=activity;
        this.isShowLoading=isShowLoading;
    }

    /**
     * 请求成功
     * @param data
     */
    public abstract void onSucceeded(T data);

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        super.onSubscribe(d);
        if (isShowLoading) {
            //显示加载对话框
            LoadingUtil.showLoading(activity);
        }
    }

    @Override
    public void onNext(@NonNull T t) {
        super.onNext(t);
        LogUtil.d(TAG,"onNext:"+t);

        //检查是否需要隐藏加载提示框
        checkHideLoading();

        if (isSucceeded(t)) {
            //请求正常
            onSucceeded(t);
        }else {
            //有状态码
            //表示请求出错了
            handlerRequest(t, null);
        }


    }

    @Override
    public void onError(@NonNull Throwable e) {
        super.onError(e);
        Log.d(TAG, "onError: "+e.getLocalizedMessage());

        //检查是否需要隐藏加载提示框
        checkHideLoading();

        //处理错误
        handlerRequest(null,e);
    }

    /**
     * 检查是否需要隐藏加载提示框
     */
    private void checkHideLoading() {
        if (isShowLoading){
            LoadingUtil.hideLoading();
        }

    }

    /**
     * 请求失败
     * @param t
     * @param e
     * @return
     */
    public boolean onFailed(T t,Throwable e){
        return false;
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

            //状态码为0表示请求成功
            //这是服务端的规定
            return baseResponse.getStatus()==0;
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
}
