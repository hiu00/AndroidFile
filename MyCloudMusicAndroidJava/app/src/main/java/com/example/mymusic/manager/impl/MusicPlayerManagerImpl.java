package com.example.mymusic.manager.impl;

import static com.example.mymusic.util.Constant.DEFAULT_TIME;
import static com.example.mymusic.util.Constant.MESSAGE_PROGRESS;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.mymusic.api.Api;
import com.example.mymusic.domain.Song;
import com.example.mymusic.domain.response.DetailResponse;
import com.example.mymusic.listener.Consumer;
import com.example.mymusic.listener.HttpObserver;
import com.example.mymusic.listener.MusicPlayerListener;
import com.example.mymusic.manager.MusicPlayerManager;
import com.example.mymusic.util.Constant;
import com.example.mymusic.util.HandlerUtil;
import com.example.mymusic.util.ListUtil;
import com.example.mymusic.util.LogUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 播放管理器默认实现
 */
public class MusicPlayerManagerImpl implements MusicPlayerManager, MediaPlayer.OnCompletionListener {
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
     * 定时器任务
     */
    private TimerTask timerTask;
    private Timer timer;

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

        //设置播放完毕监听器
        player.setOnCompletionListener(this);
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

            //启动播放进度通知
            startPublishProgress();

            //如果是本地音乐
            //就不获取歌词了
            if (data.isLocal()){
                return;
            }

            //歌词处理
            if (data.getLyric()==null){
                //没有歌词才请求
                Api.getInstance()
                        .songDetail(data.getId())
                        .subscribe(new HttpObserver<DetailResponse<Song>>() {
                            @Override
                            public void onSucceeded(DetailResponse<Song> songDetailResponse) {
                                if (songDetailResponse != null && songDetailResponse.getData() != null){
                                    //将数据设置到歌曲对象上
                                    data.setStyle(songDetailResponse.getData().getStyle());
                                    data.setLyric(songDetailResponse.getData().getLyric());
                                }
                                //通知歌词改变了
                                onLyricChanged();
                            }
                        });
            }else {
                //通知歌词改变了
                onLyricChanged();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //发生错误了
            //TODO 处理错误
        }
    }

    /**
     * 通知歌词改变了
     */
    public void onLyricChanged() {
        //这里只是通知歌词数据改变了
        //但不一定有歌词

        //不管有没有歌词都要回调
        ListUtil.eachListener(listeners,listener -> listener.onLyricChanged(data));
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

            //停止进度通知
            stopPublishProgress();
        }
    }

    @Override
    public void resume() {
        if (!isPlaying()) {
            //如果没有播放就播放
            player.start();

            //回调监听器
            publishPlayingStatus();

            //启动播放进度通知
            startPublishProgress();
        }
    }

    @Override
    public void addMusicPlayerListener(MusicPlayerListener listener) {
        if (!listeners.contains(listener)){
            listeners.add(listener);

            //启动进度通知
            startPublishProgress();
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

    @Override
    public void seekTo(int progress) {
        player.seekTo(progress);
    }

    @Override
    public void setLooping(boolean looping) {
        player.setLooping(looping);
    }

    /**
     * 启动播放进度通知
     */
    private void startPublishProgress(){
        //没有进度回调就不启动
        if (isEmptyListeners()){
            return;
        }

        if(!isPlaying()){
            //没有播放音乐就不启动定时器
            return;
        }

        if (timerTask!=null){
            //已经启动了
            return;
        }

        //创建一个任务
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //如果没有监听器了就停止定时器
                if (isEmptyListeners()){
                    stopPublishProgress();
                    return;
                }

                //LogUtil.d(TAG, "time task progress");

                //这里是子线程
                //不能直接操作UI
                //为了方便外部
                //在内部就切换到主线程
                handler.obtainMessage(MESSAGE_PROGRESS).sendToTarget();
            }
        };

        //创建一个定时器
        timer = new Timer();

        //启动一个持续的任务

        //16毫秒
        //为什么是16毫秒？
        //因为后面我们要实现卡拉OK歌词
        //为了画面的连贯性
        //应该保持1秒60帧
        //所以1/60；就是一帧时间
        //如果没有卡拉OK歌词
        //那么每秒钟刷新一次即可
        timer.schedule(timerTask,0,DEFAULT_TIME);
    }

    /**
     * 是否没有进度监听器
     * @return
     */
    private boolean isEmptyListeners() {
        return listeners.size() == 0;
    }

    /**
     * 停止进度通知
     */
    private void stopPublishProgress() {
        //停止定时器任务
        if (timerTask!=null){
            timerTask.cancel();
            timerTask = null;
        }

        //停止定时器
        if (timer!=null){
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 创建handler
     * 作用：将事件转换到主线程
     */
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MESSAGE_PROGRESS:
                    //播放进度回调

                    //将进度设置到音乐对象
                    data.setProgress(player.getCurrentPosition());

                    //回调监听
                    ListUtil.eachListener(listeners,listener -> listener.onProgress(data));
                    break;
            }
        }
    };

    /**
     * 音乐播放完毕了监听器
     * @param mediaPlayer
     */
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        //回调完成监听器
        ListUtil.eachListener(listeners,listener -> listener.onCompletion(mediaPlayer));
    }
}
