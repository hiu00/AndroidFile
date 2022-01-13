package com.example.mymusic.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.example.mymusic.activity.BaseCommonActivity;

import es.dmoral.toasty.Toasty;

/**
 * Toast工具类
 */
public class ToastUtil {

    /**
     * 上下文
     */
    private static Context context;

    /**
     * 错误短时间Toast
     * @param id
     */
    public static void errorShortToast(@StringRes int id) {
        Toasty.error(context,id,Toasty.LENGTH_SHORT).show();
    }

    /**
     * 错误短时间Toast
     * @param message
     */
    public static void errorShortToast(String message) {
        Toasty.error(context,message,Toasty.LENGTH_SHORT).show();
    }

    /**
     * 错误长时间Toast
     * @param id
     */
    public static void errorLongToast(@StringRes int id) {
        Toasty.error(context,id,Toasty.LENGTH_LONG).show();
    }

    /**
     * 成功短时间Toast
     * @param id
     */
    public static void successLongToast(@StringRes int id) {
        Toasty.success(context,id,Toasty.LENGTH_SHORT).show();
    }

    /**
     * 初始化方法
     * @param context
     */
    public static void init(Context context){
        ToastUtil.context=context;
    }


}
