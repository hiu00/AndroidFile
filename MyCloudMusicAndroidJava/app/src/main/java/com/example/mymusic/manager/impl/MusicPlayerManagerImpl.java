package com.example.mymusic.manager.impl;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.mymusic.domain.Song;
import com.example.mymusic.manager.MusicPlayerManager;

import java.io.IOException;

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
    private final MediaPlayer player;

    /**
     * 当前播放的音乐对象
     */
    private Song data;

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
        } catch (IOException e) {
            e.printStackTrace();
            //发生错误了
            //TODO 处理错误
        }
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
        }
    }

    @Override
    public void resume() {
        if (!isPlaying()) {
            //如果没有播放就播放
            player.start();
        }
    }
}
