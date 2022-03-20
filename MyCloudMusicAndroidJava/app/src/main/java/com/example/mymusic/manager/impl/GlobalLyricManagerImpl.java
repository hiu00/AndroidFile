package com.example.mymusic.manager.impl;

import android.content.Context;

import com.example.mymusic.manager.GlobalLyricManager;

/**
 * 歌词管理器实现
 */
public class GlobalLyricManagerImpl implements GlobalLyricManager {
    /**
     * 实例
     */
    private static GlobalLyricManagerImpl instance;

    /**
     * 上下文
     */
    private static Context context;

    public GlobalLyricManagerImpl(Context context) {
        this.context=context.getApplicationContext();
    }

    /**
     * 获取全局歌词管理器对象
     * @return
     */
    public static GlobalLyricManager getInstance(Context context) {
        if (instance == null) {
            instance=new GlobalLyricManagerImpl(context);
        }
        return instance;
    }
    
    @Override
    public void show() {
        
    }

    @Override
    public void hide() {

    }

    @Override
    public boolean isShowing() {
        return false;
    }

    @Override
    public void tryHide() {

    }

    @Override
    public void tryShow() {

    }
}
