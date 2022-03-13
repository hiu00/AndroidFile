package com.example.mymusic.manager.impl;

import android.content.Context;

/**
 * 通知管理器
 */
public class MusicNotificationManager {
    /**
     * 实例
     */
    private static MusicNotificationManager instance;

    /**
     * 上下文
     */
    private final Context context;

    public MusicNotificationManager(Context context) {
        this.context=context.getApplicationContext();
    }

    /**
     * 获取通知管理器实例
     * @param context
     * @return
     */
    public static MusicNotificationManager getInstance(Context context) {
        if (instance == null){
            instance = new MusicNotificationManager(context);
        }
        return instance;
    }
}
