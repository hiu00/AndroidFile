package com.example.mymusic.manager.impl;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.mymusic.domain.Song;
import com.example.mymusic.listener.Consumer;
import com.example.mymusic.listener.MusicPlayerListener;
import com.example.mymusic.manager.MusicPlayerManager;
import com.example.mymusic.util.ListUtil;
import com.example.mymusic.util.LogUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 播放管理器默认实现
 */
public class MusicPlayerManagerImpl implements MusicPlayerManager {
    private static final String TAG = "MusicPlayerManagerImpl";
    /**
     * 单例实例对象
     */
    private static MusicPlayerManagerImpl instance;

    /**
     * 上下文
     */
    private final Context context;
    private final MediaPlayer player;

    /**
     * 当前播放的音乐对象
     */
    private Song data;

    /**
     * 播放器状态监听器
     */
    private List<MusicPlayerListener> listeners = new ArrayList<>();

    /**
     * 私有构造方法
     * 初始化播放器
     * 这里外部就不能通过new方法来创建对象了
     * @param context
     */
    public MusicPlayerManagerImpl(Context context) {
        //保存context
        //因为后面可能用到
        this.context=context.getApplicationContext();

        //初始化播放器
        player = new MediaPlayer();

        //设置播放器监听器
        initListener();
    }

    /**
     * 设置播放器监听器
     */
    private void initListener() {
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            /**
             * 播放器准备开始播放
             *
             * 这里可以获取到音乐时长
             * 如果是视频还能获取到视频宽高等信息
             * @param mediaPlayer
             */
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                LogUtil.d(TAG,"onPrepared");

                //将音乐总时长保存到音乐对象
                data.setDuration(mediaPlayer.getDuration());

                //回调监听器
                ListUtil.eachListener(listeners,listener -> listener.onPrepared(mediaPlayer,data));
            }
        });
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

    /**
     * 播放音乐
     * @param uri 播放音乐的绝对地址
     * @param data 音乐对象
     */
    @Override
    public void play(String uri, Song data) {
        try {
            //保存音乐对象
            this.data=data;

            //释放player
            player.reset();

            //设置数据源
            player.setDataSource(uri);

            //同步准备
            player.prepare();

            //开始播放
            player.start();

            //回调监听器
            publishPlayingStatus();
        } catch (IOException e) {
            e.printStackTrace();
            //发生错误了
            //TODO 处理错误
        }
    }

    /**
     * 发布播放中状态
     */
    private void publishPlayingStatus(){
//        for (MusicPlayerListener listener:
//                listeners) {
//            listener.onPlaying(data);
//        }

        //使用重构后的方法
        ListUtil.eachListener(listeners, new Consumer<MusicPlayerListener>() {
            @Override
            public void accept(MusicPlayerListener musicPlayerListener) {
                musicPlayerListener.onPlaying(data);
            }
        });
    }

    /**
     * 是否在播放
     * @return
     */
    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }

    @Override
    public void pause() {
        if (isPlaying()) {
            //如果在播放就暂停
            player.pause();

            //回调监听器
//            for (MusicPlayerListener listener:
//                    listeners) {
//                listener.onPaused(data);
//            }

            //使用重构后的方法
            ListUtil.eachListener(listeners,listener -> listener.onPaused(data));
        }
    }

    @Override
    public void resume() {
        if (!isPlaying()) {
            //如果没有播放就播放
            player.start();

            //回调监听器
            publishPlayingStatus();
        }
    }

    @Override
    public void addMusicPlayerListener(MusicPlayerListener listener) {
        if (!listeners.contains(listener)){
            listeners.add(listener);
        }
    }

    @Override
    public void removeMusicPlayerListener(MusicPlayerListener listener) {
        listeners.remove(listener);
    }

    @Override
    public Song getData() {
        return data;
    }
}
