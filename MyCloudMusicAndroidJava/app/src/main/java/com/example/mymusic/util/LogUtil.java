package com.example.mymusic.util;

import android.util.Log;

import com.example.mymusic.BuildConfig;

/**
 * 日志工具类
 * 对Android日志API做一层简单的封装
 */
public class LogUtil {

    /**
     * 是否是调试状态
     */
    public static boolean isDebug = BuildConfig.DEBUG;

    /**
     * 按钮点击了
     * @param tag
     * @param value
     */
    public static void d(String tag, String value) {
        if (isDebug){
            Log.d(tag,value);
        }
    }

    public static void w(String tag, String value) {
        if (isDebug) {
            Log.w(tag, value);
        }
    }
}
