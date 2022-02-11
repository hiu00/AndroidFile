package com.example.mymusic.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.mymusic.R;
import com.example.mymusic.domain.response.BaseResponse;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * 网络请求相关的辅助方法
 */
public class HttpUtil {
    /**
     * 网络请求错误处理
     *
     * @param t
     * @param e
     */
    public static void handlerRequest(Object t, Throwable e) {
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
                Log.e("loveYan", "网络请求错误--》HttpUtil--》52行 " + e.getMessage() );
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
}
