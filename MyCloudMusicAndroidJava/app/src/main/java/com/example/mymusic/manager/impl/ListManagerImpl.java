package com.example.mymusic.manager.impl;

import android.content.Context;

import com.example.mymusic.domain.Song;
import com.example.mymusic.listener.ListManager;
import com.example.mymusic.manager.MusicPlayerManager;
import com.example.mymusic.service.MusicPlayerService;
import com.example.mymusic.util.LogUtil;
import com.example.mymusic.util.ResourceUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * 列表管理器的默认实现类
 */
public class ListManagerImpl implements ListManager {
    private static final String TAG = "ListManagerImpl";
    /**
     * 实例对象
     */
    private static ListManagerImpl instance;

    /**
     * 播放列表
     * ArreyList：遍历比较高效
     * LinkedList:删除比较高效
     */
    private List<Song> datum=new LinkedList<>();

    /**
     * 上下文
     */
    private final Context context;

    /**
     * 当前音乐对象
     */
    private Song data;
    private final MusicPlayerManager musicPlayerManager;

    /**
     * 构造方法
     * @param context
     */
    private ListManagerImpl(Context context) {
        this.context=context.getApplicationContext();

        //初始化列表管理器
        musicPlayerManager = MusicPlayerService.getMusicPlayerManager(this.context);
    }

    /**
     * 获取列表管理器
     * @param context
     * @return
     */
    public static synchronized ListManagerImpl getInstance(Context context) {
        if (instance == null){
          instance=  new ListManagerImpl(context);
        }
        return instance;
    }

    @Override
    public void setDatum(List<Song> datum) {
        LogUtil.d(TAG,"setDatum");

        //清空原来的数据
        this.datum.clear();

        //添加新的数据
        this.datum.addAll(datum);
    }

    @Override
    public List<Song> getDatum() {
        LogUtil.d(TAG,"getDatum");
        return datum;
    }

    @Override
    public void play(Song song) {
        LogUtil.d(TAG,"play");
        //保存数据
        this.data=song;

        //播放音乐
        musicPlayerManager.play(ResourceUtil.resourceUri(data.getUri()),data);
    }

    @Override
    public void pause() {
        LogUtil.d(TAG,"resume");
    }

    @Override
    public void resume() {
        LogUtil.d(TAG,"resume");
    }
}
