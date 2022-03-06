package com.example.mymusic.manager.impl;

import static com.example.mymusic.util.Constant.MODEL_LOOP_LIST;
import static com.example.mymusic.util.Constant.MODEL_LOOP_ONE;
import static com.example.mymusic.util.Constant.MODEL_LOOP_RANDOM;

import android.content.Context;

import com.example.mymusic.domain.Song;
import com.example.mymusic.listener.ListManager;
import com.example.mymusic.manager.MusicPlayerManager;
import com.example.mymusic.service.MusicPlayerService;
import com.example.mymusic.util.LogUtil;
import com.example.mymusic.util.ResourceUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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

    /**
     * 音乐播放管理器
     */
    private final MusicPlayerManager musicPlayerManager;

    /**
     * 正在播放
     */
    private boolean isPlay;

    /**
     * 歌曲循环模式
     */
    private int model = MODEL_LOOP_LIST;

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

        //标记已经播放了
        isPlay = true;

        //保存数据
        this.data=song;

        //播放音乐
        musicPlayerManager.play(ResourceUtil.resourceUri(data.getUri()),data);
    }

    @Override
    public void pause() {
        LogUtil.d(TAG,"pause");

        musicPlayerManager.pause();
    }

    @Override
    public void resume() {
        LogUtil.d(TAG,"resume");

        if(isPlay){
            //原来已经播放过
            //也就说播放器已经初始化了
            musicPlayerManager.resume();
        }else {
            //到这里，是应用开启后，第一次点继续播放
            //而这时内部其实还没有准备播放，所以应该调用播放
            play(data);
        }
    }

    @Override
    public Song next() {

        //音乐索引
        int index = 0;

        //判断循环模式
        switch (model){
            case MODEL_LOOP_RANDOM:
                //随机循环

                //在0~datum.size()中
                //不包含datum.size()
                index = new Random().nextInt(datum.size());
                break;
            default:
                //找到当前音乐索引
                index = datum.indexOf(data);

                if (index!=-1){
                    //找到了

                    //如果当前播放是列表第一个
                    if(index == datum.size()-1){
                        //最后一首音乐

                        //那就从0开始播放
                        index = 0;
                    }else {
                        index++;
                    }
                }else {
                    //抛出异常
                    //因为正常情况下是能找到的
                    throw new IllegalArgumentException("Cant'found current song");
                }
                break;
        }
        return datum.get(index);
    }

    @Override
    public Song previous() {
        //音乐索引
        int index = 0;

        //判断循环模式
        switch (model) {
            case MODEL_LOOP_RANDOM:
                //随机循环

                //在0~datum.size()中
                //不包含datum.size()
                index = new Random().nextInt(datum.size());
                break;
            default:
                //找到当前音乐索引
                index = datum.indexOf(data);

                if (index != -1) {
                    //找到了

                    //如果当前播放是列表第一个
                    if (index == 0) {
                        //第一首音乐

                        //那就从最后开始播放
                        index = datum.size() - 1;
                    } else {
                        index--;
                    }
                } else {
                    //抛出异常
                    //因为正常情况下是能找到的
                    throw new IllegalArgumentException("Cant't find current song");
                }
                break;
        }

        //获取音乐
        return datum.get(index);
    }

    @Override
    public int changeLoopModel() {
        //循环模式+1
        model++;

        //判断循环模式边界
        if (model > MODEL_LOOP_RANDOM) {
            //如果当前循环模式
            //大于最后一个循环模式
            //就设置为第0个循环模式
            model = MODEL_LOOP_LIST;
        }

        //判断是否是单曲循环
        if(MODEL_LOOP_ONE == model){
            //单曲循环模式
            //设置到mediaPlay
            musicPlayerManager.setLooping(true);
        }else {
            //其他模式关闭循环
            musicPlayerManager.setLooping(false);
        }

        //返回最终的循环模式
        return model;
    }

    @Override
    public int getLoopModel() {
        return model;
    }
}
