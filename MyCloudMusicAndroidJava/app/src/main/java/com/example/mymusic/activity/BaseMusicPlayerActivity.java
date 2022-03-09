package com.example.mymusic.activity;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mymusic.R;
import com.example.mymusic.domain.Song;
import com.example.mymusic.listener.ListManager;
import com.example.mymusic.listener.MusicPlayerListener;
import com.example.mymusic.manager.MusicPlayerManager;
import com.example.mymusic.service.MusicPlayerService;
import com.example.mymusic.util.ImageUtil;

import butterknife.BindView;

/**
 * 通用音乐播放界面
 * 主要是显示迷你控制栏播放状态
 */
public class BaseMusicPlayerActivity extends BaseTitleActivity implements MusicPlayerListener {

    /**
     * 列表管理器
     */
    protected ListManager listManager;
    protected MusicPlayerManager musicPlayerManager;

    /**
     * 迷你播放控制器 容器
     */
    @BindView(R.id.ll_play_control_small)
    LinearLayout ll_play_control_small;

    /**
     * 迷你播放控制器 封面
     */
    @BindView(R.id.iv_banner_small_control)
    ImageView iv_banner_small_control;

    /**
     * 迷你播放控制器 标题
     */
    @BindView(R.id.tv_title_small_control)
    TextView tv_title_small_control;

    /**
     * 迷你播放控制器 播放暂停按钮
     */
    @BindView(R.id.iv_play_small_control)
    ImageView iv_play_small_control;

    /**
     * 迷你播放控制器 进度条
     */
    @BindView(R.id.pb_progress_small_control)
    ProgressBar pb_progress_small_control;

    @Override
    protected void initDatum() {
        super.initDatum();

        //初始化列表管理器
        listManager = MusicPlayerService.getListManager(getApplicationContext());

        //初始化音乐播放管理器
        musicPlayerManager = MusicPlayerService.getMusicPlayerManager(getApplicationContext());
    }

    /**
     * 界面显示了回调
     */
    @Override
    protected void onResume() {
        super.onResume();
        //添加播放管理器监听器
        musicPlayerManager.addMusicPlayerListener(this);

        //显示迷你播放控制器数据
        showSmallPlayControlData();
    }

    /**
     * 界面隐藏了
     */
    @Override
    protected void onPause() {
        super.onPause();

        //移除播放管理器监听器
        musicPlayerManager.removeMusicPlayerListener(this);
    }

    /**
     * 显示迷你播放控制器数据
     */
    private void showSmallPlayControlData() {
        if (listManager.getDatum()!=null && listManager.getDatum().size()>0){
            //有音乐

            //显示迷你控制器
            ll_play_control_small.setVisibility(View.VISIBLE);

            //获取当前播放的音乐
            Song data = listManager.getData();

            if (data!=null){
                //显示初始化数据
                showInitData(data);

                //显示音乐时长
                showDuration(data);

                //显示播放进度
                showProgress(data);

                //显示播放状态
                showMusicPlayStatus();;
            }
        }else{
            //隐藏迷你控制器
            ll_play_control_small.setVisibility(View.GONE);
        }
    }

    /**
     * 显示播放状态
     */
    private void showMusicPlayStatus() {
        if (musicPlayerManager.isPlaying()){
            showPauseStatus();
        }else {
            showPlayStatus();
        }
    }

    /**
     * 显示播放状态
     */
    private void showPlayStatus() {
        //这种图片切换可以使用Selector来实现
        iv_play_small_control.setSelected(false);
    }

    /**
     * 显示暂停状态
     */
    private void showPauseStatus() {
        iv_play_small_control.setSelected(true);
    }

    /**
     * 显示播放进度
     * @param data
     */
    private void showProgress(Song data) {
        //设置到进度条
        pb_progress_small_control.setProgress((int) data.getProgress());
    }

    /**
     * 显示音乐时长
     */
    private void showDuration(Song data) {
        //获取当前音乐时长
        int end = (int)data.getDuration();

        //设置到进度条
        pb_progress_small_control.setMax(end);
    }

    /**
     * 显示初始化数据
     */
    private void showInitData(Song data) {
        //封面
        ImageUtil.show(getMainActivity(),iv_banner_small_control,data.getBanner());

        //显示标题
        tv_title_small_control.setText(data.getTitle());
    }

    @Override
    public void onPaused(Song data) {
        showPlayStatus();
    }

    @Override
    public void onPlaying(Song data) {
        showPauseStatus();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer, Song data) {
        showInitData(data);
    }

    @Override
    public void onProgress(Song data) {
        showProgress(data);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}