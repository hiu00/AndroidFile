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
     * 错误短时间Toast
     * @param context
     * @param id
     */
    public static void errorShortToast(@NonNull Context context, @StringRes int id) {
        Toasty.error(context,id,Toasty.LENGTH_SHORT).show();
    }

    /**
     * 错误长时间Toast
     * @param context
     * @param id
     */
    public static void errorLongToast(@NonNull Context context, @StringRes int id) {
        Toasty.error(context,id,Toasty.LENGTH_LONG).show();
    }

    /**
     * 成功短时间Toast
     * @param context
     * @param id
     */
    public static void successLongToast(@NonNull Context context, @StringRes int id) {
        Toasty.success(context,id,Toasty.LENGTH_SHORT).show();
    }
}
