package com.example.mymusic.activity;

import static com.example.mymusic.util.Constant.MODEL_LOOP_LIST;
import static com.example.mymusic.util.Constant.MODEL_LOOP_RANDOM;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.example.mymusic.R;
import com.example.mymusic.adapter.SimplePlayerAdapter;
import com.example.mymusic.domain.Song;
import com.example.mymusic.listener.ListManager;
import com.example.mymusic.listener.MusicPlayerListener;
import com.example.mymusic.manager.MusicPlayerManager;
import com.example.mymusic.service.MusicPlayerService;
import com.example.mymusic.util.LogUtil;
import com.example.mymusic.util.NotificationUtil;
import com.example.mymusic.util.TimeUtil;
import com.example.mymusic.util.ToastUtil;

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

    private ListManager listManager;
    private SimplePlayerAdapter adapter;
    private ItemDragAndSwipeCallback itemDragAndSwipeCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_player);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //高度固定
        rv.setHasFixedSize(true);

        //布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());
        rv.setLayoutManager(layoutManager);
    }

    @Override
    protected void initDatum() {
        super.initDatum();

        //初始化列表管理器
        listManager = MusicPlayerService.getListManager(getApplicationContext());

        //使用MusicPlayerService获取音乐播放管理器
        musicPlayerManager = MusicPlayerService.getMusicPlayerManager(getApplicationContext());

//        //测试播放音乐
//        String songUrl = "http://dev-courses-misuc.ixuea.com/assets/s1.mp3";
//
//        Song song = new Song();
//        song.setUri(songUrl);
//
//        //播放音乐
//        musicPlayerManager.play(songUrl,song);
        
        //创建适配器
        adapter = new SimplePlayerAdapter(android.R.layout.simple_list_item_1);

        //设置到控件
        rv.setAdapter(adapter);

        //设置数据
        adapter.replaceData(listManager.getDatum());

        //列表滑动删除

        //Item拖拽和滑动回调
        itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter){
            /**
             * 获取移动参数
             *
             * 主要就是告诉他是否开启拖拽，滑动
             * 什么方向可以拖拽，滑动
             *
             * @param recyclerView
             * @param viewHolder
             * @return
             */
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                //第一个参数控制拖拽
                //第二个参数控制滑动

                //禁用了拖拽
                //开启了从右边滑动到左边
                return isViewCreateByAdapter(viewHolder)?makeMovementFlags(ItemTouchHelper.ACTION_STATE_IDLE,ItemTouchHelper.ACTION_STATE_IDLE)
                        :makeMovementFlags(ItemTouchHelper.ACTION_STATE_IDLE,ItemTouchHelper.LEFT);
            }
        };

        //Item触摸帮助类
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);

        //将帮助类附加到RecyclerView
        itemTouchHelper.attachToRecyclerView(rv);

        // 开启滑动删除
        adapter.enableSwipeItem();

        //创建滑动监听器
        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener(){

            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int i) {

            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int i) {

            }

            /**
             * 当前侧滑完成时回调
             *
             * @param viewHolder
             * @param position
             */
            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int position) {
                //这个框架内部
                //已经从adapter对应的列表中移除了对应位置的数据

                //从播放列表中删除
                listManager.delete(position);

                //判断是否要关闭播放界面
                if (listManager.getData()==null){
                    //表示没有音乐了
                    finish();
                }
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float v, float v1, boolean b) {

            }
        };

        //设置滑动监听器
        adapter.setOnItemSwipeListener(onItemSwipeListener);
    }

    private boolean isViewCreateByAdapter(@NonNull RecyclerView.ViewHolder viewHolder) {
        int type = viewHolder.getItemViewType();
        return type == 273 || type == 546 || type == 819 || type == 1365;
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        //设置进度条监听器
        sb_progress.setOnSeekBarChangeListener(this);

        //设置Item点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //获取这首音乐
                Song data = listManager.getDatum().get(position);

                //播放音乐
                listManager.play(data);
            }
        });
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

        if (fromUser){
            //跳转到该位置播放
            musicPlayerManager.seekTo(progress);
        }
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

        listManager.play(listManager.previous());
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
            listManager.pause();
        } else {
            listManager.resume();

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

        //显示初始化数据
        showInitData();

        //显示音乐时长
        showDuration();

        //显示播放状态
        showMusicPlayStatus();;

        //显示播放进度
        showProgress();

        //选中当前音乐
        scrollPosition();
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

        //获取下一首音乐
        Song data = listManager.next();

        if (data!=null){
            listManager.play(data);
        }else {
            //正常情况下不能能走到这里
            //因为播放界面只有播放列表有数据时才能进入
            ToastUtil.errorShortToast(R.string.not_play_music);
        }
    }

    /**
     * 音乐循环模式点击
     */
    @OnClick(R.id.bt_loop_model)
    public void onLoopModelClick() {
        LogUtil.d(TAG, "onLoopModelClick");

        //改变循环模式
        listManager.changeLoopModel();

        //显示循环模式
        showLoopModel();
    }

    /**
     * 显示循环模式
     */
    private void showLoopModel() {
        ////获取当前循环模式
        int model = listManager.getLoopModel();

        //根据不同循环模式
        //显示不同的提示
        switch (model){
            case MODEL_LOOP_LIST:
                bt_loop_model.setText("列表循环");
                break;
            case MODEL_LOOP_RANDOM:
                bt_loop_model.setText("随机模式");
                break;
            default:
                bt_loop_model.setText("单曲循环");
                break;
        }
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

        //显示初始化数据
        showInitData();

        //显示时长
        showDuration();

        //选中当前音乐
        scrollPosition();
    }

    /**
     * 滚动到当前音乐位置
     */
    public void scrollPosition() {
        //选中当前播放的音乐
        rv.post(new Runnable() {
            @Override
            public void run() {
                //获取当前音乐的位置
                int index = listManager.getDatum().indexOf(listManager.getData());
                if (index != -1){
                    //滚动到该位置
                    rv.smoothScrollToPosition(index);

                    //选中
                    adapter.setSelectedIndex(index);
                }
            }
        });
    }

    /**
     * 显示初始化数据
     */
    private void showInitData() {
        //获取当前正在播放的音乐
        Song data = listManager.getData();

        //显示标题
        tv_title.setText(data.getTitle());
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
     * 音乐播放完毕了监听器
     * @param mediaPlayer
     */
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        LogUtil.d(TAG,"onCompletion");
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