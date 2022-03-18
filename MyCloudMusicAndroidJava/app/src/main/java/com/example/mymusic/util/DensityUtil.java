package com.example.mymusic.util;

import android.content.Context;

import com.example.mymusic.activity.BaseCommonActivity;

/**
 * Android尺寸相关工具类
 */
public class DensityUtil {
    /**
     * 根据手机的分辨率从 dip单位 转成 px(像素)
     * @param context
     * @param data
     * @return
     */
    public static int dip2px(Context context, float data) {
        float scale = context.getResources().getDisplayMetrics().density;
        //0.5f 四舍五入
        //px=缩放*dp
        return (int) (data*scale+0.5f);
    }

    /**
     * 根据手机的分辨率从 px单位 转成 dip
     * @param context
     * @param data
     * @return
     */
    public static int px2dip(Context context, float data) {
        float scale = context.getResources().getDisplayMetrics().density;
        //0.5f 四舍五入
        //px=缩放*dp
        return (int) (data/scale+0.5f);
    }
}
