package com.example.mymusic.activity;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.example.mymusic.util.Constant.MODEL_LOOP_LIST;
import static com.example.mymusic.util.Constant.MODEL_LOOP_ONE;
import static com.example.mymusic.util.Constant.MODEL_LOOP_RANDOM;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.mymusic.Fragment.PlayListDialogFragment;
import com.example.mymusic.R;
import com.example.mymusic.domain.Song;
import com.example.mymusic.listener.ListManager;
import com.example.mymusic.listener.MusicPlayerListener;
import com.example.mymusic.manager.MusicPlayerManager;
import com.example.mymusic.service.MusicPlayerService;
import com.example.mymusic.util.LogUtil;
import com.example.mymusic.util.ResourceUtil;
import com.example.mymusic.util.SwitchDrawableUtil;
import com.example.mymusic.util.TimeUtil;
import com.example.mymusic.util.ToastUtil;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * 黑胶唱片界面
 */
public class MusicPlayerActivity extends BaseTitleActivity implements MusicPlayerListener, SeekBar.OnSeekBarChangeListener {
    private static final String TAG = "MusicPlayerActivity";
    /**
     * 背景
     */
    @BindView(R.id.iv_background)
    ImageView iv_background;

    /**
     * 下载按钮
     */
    @BindView(R.id.ib_download)
    ImageButton ib_download;

    /**
     * 开始位置
     */
    @BindView(R.id.tv_start)
    TextView tv_start;

    /**
     * 进度条
     */
    @BindView(R.id.sb_progress)
    SeekBar sb_progress;

    /**
     * 结束位置
     */
    @BindView(R.id.tv_end)
    TextView tv_end;

    /**
     * 循环模式按钮
     */
    @BindView(R.id.ib_loop_model)
    ImageButton ib_loop_model;

    /**
     * 播放按钮
     */
    @BindView(R.id.ib_play)
    ImageButton ib_play;

    private ListManager listManager;
    private MusicPlayerManager musicPlayerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
    }

    /**
     * 下载按钮点击
     */
    @OnClick(R.id.ib_download)
    public void onDownloadClick() {
        LogUtil.d(TAG, "onDownloadClick");
    }

    /**
     * 循环模式按钮点击
     */
    @OnClick(R.id.ib_loop_model)
    public void onLoopModelClick() {
        LogUtil.d(TAG, "onLoopModelClick");

        //更改循环模式
        listManager.changeLoopModel();

        //显示循环模式
        showLoopModel();
    }

    /**
     * 显示循环模式
     */
    private void showLoopModel() {
        //获取当前循环模式
        int model = listManager.getLoopModel();

        switch (model){
            case MODEL_LOOP_RANDOM:
                ib_loop_model.setImageResource(R.drawable.ic_music_repeat_random);
                break;
            case MODEL_LOOP_LIST:
                ib_loop_model.setImageResource(R.drawable.ic_music_repeat_list);
                break;
            case MODEL_LOOP_ONE:
                ib_loop_model.setImageResource(R.drawable.ic_music_repeat_one);
                break;
        }
    }

    /**
     * 上一曲按钮点击
     */
    @OnClick(R.id.ib_previous)
    public void onPreviousClick() {
        LogUtil.d(TAG, "onPreviousClick");

        listManager.play(listManager.previous());
    }

    /**
     * 播放按钮点击
     */
    @OnClick(R.id.ib_play)
    public void onPlayClick() {
        LogUtil.d(TAG, "onPlayClick");

        //播放或暂停
        playOrPause();
    }

    /**
     * 播放或暂停
     */
    private void playOrPause() {
        if (musicPlayerManager.isPlaying()){
            listManager.pause();
        }else {
            listManager.resume();
        }
    }

    /**
     * 下一曲按钮点击
     */
    @OnClick(R.id.ib_next)
    public void onNextClick() {
        LogUtil.d(TAG, "onNextClick");

        listManager.play(listManager.next());
    }

    /**
     * 播放列表按钮点击
     */
    @OnClick(R.id.ib_list)
    public void onListClick() {
        LogUtil.d(TAG, "onListClick");

        PlayListDialogFragment.show(getSupportFragmentManager());
    }

    /**
     * 创建菜单方法
     *
     * 有点类似显示布局要写到onCreate方法一样
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载布局文件
        getMenuInflater().inflate(R.menu.menu_music_player,menu);
        return true;
    }

    /**
     * 菜单点击了回调
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //获取点击的菜单按钮
        int id = item.getItemId();

        if (R.id.action_share==id){
            //分享按钮点击了
            ToastUtil.successShortToast("你点击了分享按钮！");

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 启动方法
     * @param activity
     */
    public static void start(Activity activity){
        Intent intent = new Intent(activity, MusicPlayerActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //显示亮色状态栏
        lightStatusBar();
    }

    @Override
    protected void initDatum() {
        super.initDatum();

        //初始化列表管理器
        listManager = MusicPlayerService.getListManager(getApplicationContext());

        //初始化播放管理器
        musicPlayerManager = MusicPlayerService.getMusicPlayerManager(getApplicationContext());
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        //设置拖拽进度控件监听器
        sb_progress.setOnSeekBarChangeListener(this);
    }

    /**
     * 进度改变了回调
     * @param seekBar
     * @param progress 当前进度
     * @param fromUser 是否是用户触发的
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser){
            //跳转到该位置开始播放
            musicPlayerManager.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /**
     * 显示初始化数据
     */
    public void showInitData(){
        //获取当前播放的音乐
        Song data = listManager.getData();

        //设置标题
        setTitle(data.getTitle());

        //设置子标题
        toolbar.setSubtitle(data.getSinger().getNickname());

        //显示背景
        //ImageUtil.show(getMainActivity(),iv_background,data.getBanner());

        //实现背景高斯模糊效果
        RequestBuilder<Drawable> requestBuilder = Glide.with(this).asDrawable();

        if (StringUtils.isBlank(data.getBanner())) {
            //没有封面图

            //使用默认封面图
            requestBuilder.load(R.drawable.default_album);
        } else {
            //使用真是图片
            requestBuilder.load(ResourceUtil.resourceUri(data.getBanner()));
        }

        //创建请求选项
        //传入了BlurTransformation
        //用来实现高斯模糊
        //radius:模糊半径；值越大越模糊
        //sampling:采样率；值越大越模糊
        RequestOptions options = bitmapTransform(new BlurTransformation(25, 3));

        //加载图片
        requestBuilder.apply(options)
                .into(new CustomTarget<Drawable>() {
                    /**
                     * 资源下载成功
                     * @param resource
                     * @param transition
                     */
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        //设置到背景控件上
                        //iv_background.setImageDrawable(resource);


                        //创建切换动画工具类
                        SwitchDrawableUtil switchDrawableUtil = new SwitchDrawableUtil(iv_background.getDrawable(), resource);

                        //设置drawable
                        iv_background.setImageDrawable(switchDrawableUtil.getDrawable());

                        //开始动画
                        switchDrawableUtil.start();
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    /**
     * 界面显示了
     */
    @Override
    protected void onResume() {
        super.onResume();

        //显示初始化数据
        showInitData();

        //显示音乐时长
        showDuration();

        //显示播放进度
        showProgress();

        //显示播放状态
        showMusicPlayStatus();

        //显示循环模式
        showLoopModel();

        //添加播放监听器
        musicPlayerManager.addMusicPlayerListener(this);
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
     * 界面隐藏了
     */
    @Override
    protected void onPause() {
        super.onPause();

        //移除播放监听器
        musicPlayerManager.removeMusicPlayerListener(this);
    }

    //播放器回调
    @Override
    public void onPaused(Song data) {
        //显示播放状态
        showPlayStatus();
    }

    @Override
    public void onPlaying(Song data) {
        //显示暂停状态
        showPauseStatus();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer, Song data) {
        //显示初始化数据
        showInitData();

        //显示时长
        showDuration();
    }

    @Override
    public void onProgress(Song data) {
        //显示播放进度
        showProgress();
    }

    /**
     * 显示播放状态
     */
    private void showPlayStatus() {
        ib_play.setImageResource(R.drawable.ic_music_play);
    }

    /**
     * 显示暂停状态
     */
    private void showPauseStatus() {
        ib_play.setImageResource(R.drawable.ic_music_pause);
    }

    /**
     * 显示时长
     */
    private void showDuration() {
        //获取播放时长
        long duration = listManager.getData().getDuration();

        //格式化
        tv_end.setText(TimeUtil.formatMinuteSecond((int) duration));

        //设置到进度条
        sb_progress.setMax((int) duration);
    }

    /**
     * 显示播放进度
     */
    private void showProgress() {
        //获取播放进度
        long progress = listManager.getData().getProgress();

        //格式化进度
        tv_start.setText(TimeUtil.formatMinuteSecond((int) progress));

        //设置到进度条
        sb_progress.setProgress((int) progress);
    }
    //播放器回调end
}