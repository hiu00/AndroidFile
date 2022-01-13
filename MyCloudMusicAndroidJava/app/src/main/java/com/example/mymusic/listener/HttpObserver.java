package com.example.mymusic.listener;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mymusic.R;
import com.example.mymusic.domain.response.BaseResponse;
import com.example.mymusic.util.LogUtil;
import com.example.mymusic.util.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

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
            requestErrorHandler(t, null);
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
    private void requestErrorHandler(T t, Throwable e) {
        if (e!=null){
            //判断错误类型
            if (e instanceof UnknownHostException){
                ToastUtil.errorShortToast(R.string.error_network_unknow_host);
            }else if (e instanceof ConnectException){
                ToastUtil.errorShortToast(R.string.error_network_connect);
            }else if (e instanceof SocketTimeoutException){
                ToastUtil.errorShortToast(R.string.error_network_timeout);
            }else if (e instanceof HttpException){
                HttpException exception= (HttpException) e;

                //获取响应码
                int code=exception.code();
                if (code==401){
                    ToastUtil.errorShortToast(R.string.error_network_not_auth);
                }else if (code == 403) {
                    ToastUtil.errorShortToast(R.string.error_network_not_permission);
                } else if (code == 404) {
                    ToastUtil.errorShortToast(R.string.error_network_not_found);
                } else if (code >= 500) {
                    ToastUtil.errorShortToast(R.string.error_network_server);
                } else {
                    ToastUtil.errorShortToast(R.string.error_network_unknown);
                }
            } else{
                ToastUtil.errorShortToast(R.string.error_network_unknown);
            }

        }else {
            if (t instanceof BaseResponse) {//instanceof用作对象的判断
                //判断具体的业务请求
                BaseResponse baseResponse = (BaseResponse) t;
                if (TextUtils.isEmpty(baseResponse.getMessage())){
                    //没有错误提示信息
                    ToastUtil.errorShortToast(R.string.error_network_unknown);
                }else {
                    //有错误提示信息
                    ToastUtil.errorShortToast(baseResponse.getMessage());
                }
            }
        }

    }

    @Override
    public void onError(@NonNull Throwable e) {
        super.onError(e);
        Log.d(TAG, "onError: "+e.getLocalizedMessage());

        //处理错误
        requestErrorHandler(null,e);
    }
}
