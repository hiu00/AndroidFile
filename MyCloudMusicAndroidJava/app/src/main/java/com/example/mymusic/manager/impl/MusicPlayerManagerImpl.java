package com.example.mymusic.manager.impl;

import android.content.Context;

import com.example.mymusic.manager.MusicPlayerManager;

/**
 * 播放管理器默认实现
 */
public class MusicPlayerManagerImpl implements MusicPlayerManager {
    /**
     * 单例实例对象
     */
    private static MusicPlayerManagerImpl instance;

    /**
     * 上下文
     */
    private final Context context;

    /**
     * 构造方法
     * @param context
     */
    public MusicPlayerManagerImpl(Context context) {
        this.context=context.getApplicationContext();
    }

    /**
     * 获取单例对象
     * @return
     */
    public static MusicPlayerManagerImpl getInstance(Context context) {
        if (instance==null){
            instance=new MusicPlayerManagerImpl(context);
        }
        return instance;
    }
}
