package com.example.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mymusic.R;
import com.example.mymusic.domain.Song;
import com.example.mymusic.listener.MusicPlayerListener;
import com.example.mymusic.manager.MusicPlayerManager;
import com.example.mymusic.service.MusicPlayerService;
import com.example.mymusic.util.LogUtil;
import com.example.mymusic.util.NotificationUtil;
import com.example.mymusic.util.TimeUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 简单播放器
 */
public class SimplePlayerActivity extends BaseTitleActivity implements SeekBar.OnSeekBarChangeListener, MusicPlayerListener {
    private static final String TAG = "SimplePlayerActivity";
    /**
     * 列表
     */
    @BindView(R.id.rv)
    RecyclerView rv;

    /**
     * 音乐标题
     */
    @BindView(R.id.tv_title)
    TextView tv_title;

    /**
     * 音乐播放开始时间
     */
    @BindView(R.id.tv_start)
    TextView tv_start;

    /**
     * 进度条
     */
    @BindView(R.id.sb_progress)
    SeekBar sb_progress;

    /**
     * 音乐播放结束时间
     */
    @BindView(R.id.tv_end)
    TextView tv_end;

    /**
     * 播放按钮
     */
    @BindView(R.id.bt_play)
    Button bt_play;

    /**
     * 循环模式按钮
     */
    @BindView(R.id.bt_loop_model)
    Button bt_loop_model;

    /**
     * 音乐播放管理器
     */
    private MusicPlayerManager musicPlayerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_player);
    }

    @Override
    protected void initDatum() {
        super.initDatum();

        //使用MusicPlayerService获取音乐播放管理器
        musicPlayerManager = MusicPlayerService.getMusicPlayerManager(getApplicationContext());

        //测试播放音乐
        String songUrl = "http://dev-courses-misuc.ixuea.com/assets/s1.mp3";

        Song song = new Song();
        song.setUri(songUrl);

        //播放音乐
        musicPlayerManager.play(songUrl,song);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        //设置进度条监听器
        sb_progress.setOnSeekBarChangeListener(this);
    }

    /**
     * 进度条改变了
     * @param seekBar
     * @param progress 当前改变后的进度
     * @param fromUser 是否是用户触发的
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        LogUtil.d(TAG, "onProgressChanged"+progress+","+fromUser);
    }

    /**
     * 开始拖拽进度条
     *
     * @param seekBar
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        LogUtil.d(TAG, "onStartTrackingTouch");
    }

    /**
     * 停止拖拽进度条
     * @param seekBar
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        LogUtil.d(TAG, "onStopTrackingTouch");

    }

    /**
     * 上一曲点击
     */
    @OnClick(R.id.bt_previous)
    public void onPreviousClick() {
        LogUtil.d(TAG, "onPreviousClick");
    }

    /**
     * 播放点击
     */
    @OnClick(R.id.bt_play)
    public void onPlayClick() {
        LogUtil.d(TAG, "onPlayClick");

        //测试通知渠道
        //该通知没有任何实际意义

        //获取通知
        Notification notification= NotificationUtil.getServiceForeground(getApplicationContext());

        //或者通知管理器
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //显示通知
        //Id没什么实际意义
        //只是相同Id的通知会被替换
        NotificationUtil.showNotification(100,notification);

        playOrPause();
    }

    /**
     * 播放或者暂停
     */
    private void playOrPause() {
        if (musicPlayerManager.isPlaying()) {
            musicPlayerManager.pause();
        } else {
            musicPlayerManager.resume();

        }
    }

    /**
     * 进入了前台(即界面可见)
     */
    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(TAG, "onResume");

        //设置播放监听器
        musicPlayerManager.addMusicPlayerListener(this);

        //显示音乐时长
        showDuration();

        //显示播放状态
        showMusicPlayStatus();;

        //显示播放进度
        showProgress();
    }

    /**
     * 进入了后台(即界面不可见了)
     */
    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(TAG,"onPause");

        //取消播放器监听器
        musicPlayerManager.removeMusicPlayerListener(this);
    }

    /**
     * 下一曲点击
     */
    @OnClick(R.id.bt_next)
    public void onNextClick() {
        LogUtil.d(TAG, "onNextClick");

    }

    /**
     * 音乐循环模式点击
     */
    @OnClick(R.id.bt_loop_model)
    public void onLoopModelClick() {
        LogUtil.d(TAG, "onLoopModelClick");
    }

    /**
     * 启动界面方法
     * @param activity 宿主activity
     */
    public static void start(Activity activity) {
        //创建意图
        //意图：就是要干什么
        Intent intent = new Intent(activity, SimplePlayerActivity.class);

        //启动界面
        activity.startActivity(intent);
    }


    //播放管理器监听器
    @Override
    public void onPaused(Song data) {
        LogUtil.d(TAG, "onPaused");
        showPlayStatus();
    }

    /**
     * 显示播放状态
     */
    private void showPlayStatus() {
        bt_play.setText("播放");

    }

    //第一次进入界面时播放按钮显示状态
    private void showMusicPlayStatus(){
        if(musicPlayerManager.isPlaying() ) {
            showPauseStatus();
        } else {
            showPlayStatus();
        }
    }

    @Override
    public void onPlaying(Song data) {
        LogUtil.d(TAG, "onPlaying");

        showPauseStatus();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer, Song data) {
        LogUtil.d(TAG, "onPrepared:" + data.getProgress()+","+data.getDuration());

        //显示时长
        showDuration();
    }

    /**
     * 播放进度回调方法
     * @param data
     */
    @Override
    public void onProgress(Song data) {
        LogUtil.d(TAG, "onProgress:" + data.getProgress() + "," + data.getDuration());

        //显示播放进度
        showProgress();
    }

    /**
     * 显示播放进度
     */
    private void showProgress(){
        //获取播放进度
        long progress = musicPlayerManager.getData().getProgress();

        //格式化进度
        tv_start.setText(TimeUtil.formatMinuteSecond((int) progress));

        //设置到进度条
        sb_progress.setProgress((int) progress);
    }

    /**
     * 显示时长
     */
    private void showDuration() {
        //获取当前正在播放的音乐
        long end = musicPlayerManager.getData().getDuration();

        //将秒格式化为分钟:秒
        tv_end.setText(TimeUtil.formatMinuteSecond((int)end));

        //设置到进度条
        sb_progress.setMax((int) end);
    }

    /**
     * 显示暂停状态
     */
    private void showPauseStatus() {
        bt_play.setText("暂停");

    }
    //--end播放管理器监听器
}