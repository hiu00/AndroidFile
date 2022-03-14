package com.example.mymusic.manager.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;

import com.example.mymusic.domain.Song;
import com.example.mymusic.listener.MusicPlayerListener;
import com.example.mymusic.manager.MusicPlayerManager;
import com.example.mymusic.service.MusicPlayerService;
import com.example.mymusic.util.Constant;
import com.example.mymusic.util.LogUtil;
import com.example.mymusic.util.NotificationUtil;

/**
 * 通知管理器
 */
public class MusicNotificationManager implements MusicPlayerListener {
    private static final String TAG = "MusicNotificationManager";
    /**
     * 实例
     */
    private static MusicNotificationManager instance;

    /**
     * 上下文
     */
    private final Context context;
    private final MusicPlayerManager musicPlayerManager;
    private BroadcastReceiver musicNotificationBroadcastReceiver;

    /**
     * 构造方法
     * @param context
     */
    public MusicNotificationManager(Context context) {
        this.context=context.getApplicationContext();

        //初始化音乐播放管理器
        musicPlayerManager = MusicPlayerService.getMusicPlayerManager(this.context);

        //添加播放管理器监听器
        musicPlayerManager.addMusicPlayerListener(this);

        //初始化音乐通知广播接收器
        initMusicNotificationReceiver();
    }

    /**
     * 初始化音乐通知广播接收器
     */
    private void initMusicNotificationReceiver() {
        musicNotificationBroadcastReceiver = new BroadcastReceiver() {
            /**
             * 发生了广播
             * @param context
             * @param intent
             */
            @Override
            public void onReceive(Context context, Intent intent) {
                //获取action
                String action = intent.getAction();

                if (Constant.ACTION_LIKE.equals(action)){
                    //点赞
                    LogUtil.d(TAG, "like");
                }else if (Constant.ACTION_PREVIOUS.equals(action)) {
                    //上一曲
                    LogUtil.d(TAG, "previous");
                } else if (Constant.ACTION_PLAY.equals(action)) {
                    //播放
                    LogUtil.d(TAG, "play");
                } else if (Constant.ACTION_NEXT.equals(action)) {
                    //下一曲
                    LogUtil.d(TAG, "next");
                } else if (Constant.ACTION_LYRIC.equals(action)) {
                    //歌词
                    LogUtil.d(TAG, "lyric");
                }
            }
        };

        //创建过滤器
        //目的是接收这些事件
        IntentFilter intentFilter = new IntentFilter();

        //添加一些动作
        intentFilter.addAction(Constant.ACTION_LIKE);
        intentFilter.addAction(Constant.ACTION_PREVIOUS);
        intentFilter.addAction(Constant.ACTION_PLAY);
        intentFilter.addAction(Constant.ACTION_NEXT);
        intentFilter.addAction(Constant.ACTION_LYRIC);

        //注册广播接受者
        context.registerReceiver(musicNotificationBroadcastReceiver,intentFilter);
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
