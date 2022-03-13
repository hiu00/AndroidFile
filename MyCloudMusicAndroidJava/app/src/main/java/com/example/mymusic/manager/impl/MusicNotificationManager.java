package com.example.mymusic.manager.impl;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.mymusic.domain.Song;
import com.example.mymusic.listener.MusicPlayerListener;
import com.example.mymusic.manager.MusicPlayerManager;
import com.example.mymusic.service.MusicPlayerService;
import com.example.mymusic.util.NotificationUtil;

/**
 * 通知管理器
 */
public class MusicNotificationManager implements MusicPlayerListener {
    /**
     * 实例
     */
    private static MusicNotificationManager instance;

    /**
     * 上下文
     */
    private final Context context;
    private final MusicPlayerManager musicPlayerManager;

    public MusicNotificationManager(Context context) {
        this.context=context.getApplicationContext();

        //初始化音乐播放管理器
        musicPlayerManager = MusicPlayerService.getMusicPlayerManager(this.context);

        //添加播放管理器监听器
        musicPlayerManager.addMusicPlayerListener(this);
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

    @Override
    public void onPaused(Song data) {
        //显示通知
        NotificationUtil.showMusicNotification(context,data,false);
    }

    @Override
    public void onPlaying(Song data) {
        //显示通知
        NotificationUtil.showMusicNotification(context,data,true);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer, Song data) {
        //显示通知
        //NotificationUtil.showMusicNotification(context,data,true);
    }

    @Override
    public void onProgress(Song data) {

    }
}
