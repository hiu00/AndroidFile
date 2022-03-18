package com.example.mymusic.activity;

import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_DRAGGING;
import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_IDLE;
import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.example.mymusic.util.Constant.MODEL_LOOP_LIST;
import static com.example.mymusic.util.Constant.MODEL_LOOP_ONE;
import static com.example.mymusic.util.Constant.MODEL_LOOP_RANDOM;
import static com.example.mymusic.util.Constant.THUMB_ROTATION_PAUSE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
import com.example.mymusic.Fragment.MusicPlayerAdapter;
import com.example.mymusic.Fragment.PlayListDialogFragment;
import com.example.mymusic.R;
import com.example.mymusic.domain.Song;
import com.example.mymusic.domain.event.OnPlayEvent;
import com.example.mymusic.domain.event.OnStartRecordEvent;
import com.example.mymusic.domain.event.OnStopRecordEvent;
import com.example.mymusic.domain.event.PlayListChangedEvent;
import com.example.mymusic.listener.ListManager;
import com.example.mymusic.listener.MusicPlayerListener;
import com.example.mymusic.manager.MusicPlayerManager;
import com.example.mymusic.service.MusicPlayerService;
import com.example.mymusic.util.Constant;
import com.example.mymusic.util.DensityUtil;
import com.example.mymusic.util.LogUtil;
import com.example.mymusic.util.ResourceUtil;
import com.example.mymusic.util.SwitchDrawableUtil;
import com.example.mymusic.util.TimeUtil;
import com.example.mymusic.util.ToastUtil;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * 黑胶唱片界面
 */
public class MusicPlayerActivity extends BaseTitleActivity implements MusicPlayerListener, SeekBar.OnSeekBarChangeListener, ViewPager.OnPageChangeListener, ValueAnimator.AnimatorUpdateListener {
    private static final String TAG = "MusicPlayerActivity";
    /**
     * 背景
     */
    @BindView(R.id.iv_background)
    ImageView iv_background;

    /**
     * 黑胶唱片列表控件
     */
    @BindView(R.id.vp)
    ViewPager vp;

    /**
     * 指针
     */
    @BindView(R.id.iv_record_thumb)
    ImageView iv_record_thumb;

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
    private MusicPlayerAdapter recordAdapter;
    private ObjectAnimator playThumbAnimator;
    private ValueAnimator pauseThumbAnimator;

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

        //缓存页面数量
        vp.setOffscreenPageLimit(3);

        //黑胶唱片指针旋转点
        //旋转点为15dp
        //而设置需要单位为px
        //所以要先转换
        int rotate = DensityUtil.dip2px(getMainActivity(), 15);
        iv_record_thumb.setPivotX(rotate);
        iv_record_thumb.setPivotY(rotate);
    }

    @Override
    protected void initDatum() {
        super.initDatum();

        //初始化列表管理器
        listManager = MusicPlayerService.getListManager(getApplicationContext());

        //初始化播放管理器
        musicPlayerManager = MusicPlayerService.getMusicPlayerManager(getApplicationContext());

        //创建黑胶唱片列表适配器
        recordAdapter = new MusicPlayerAdapter(getMainActivity(), getSupportFragmentManager());

        //设置到控件
        vp.setAdapter(recordAdapter);

        //设置数据
        recordAdapter.setDatum(listManager.getDatum());

        //从暂停到播放状态动画
        //从-25到0
        playThumbAnimator = ObjectAnimator.ofFloat(iv_record_thumb, "rotation", THUMB_ROTATION_PAUSE, Constant.THUMB_ROTATION_PLAY);

        //设置执行时间
        playThumbAnimator.setDuration(Constant.THUMB_DURATION);

        //从播放到暂停状态动画
        pauseThumbAnimator = ValueAnimator.ofFloat(Constant.THUMB_ROTATION_PLAY, THUMB_ROTATION_PAUSE);

        //设置执行时间
        pauseThumbAnimator.setDuration(Constant.THUMB_DURATION);

        //设置更新监听器
        pauseThumbAnimator.addUpdateListener(this);
    }

    /**
     * 更新监听器
     * @param animator
     */
    @Override
    public void onAnimationUpdate(ValueAnimator animator) {
        iv_record_thumb.setRotation((Float) animator.getAnimatedValue());
    }

    /**
     * 黑胶唱片指针默认状态动画（播放状态）
     */
    public void startRecordThumbRotate(){
        playThumbAnimator.start();
    }

    /**
     * 黑胶唱片指针旋转-25度动画（暂停状态）
     */
    public void stopRecordThumbRotate(){
        //获取黑胶唱片指针旋转的角度
        float thumbRotation = iv_record_thumb.getRotation();

        LogUtil.d(TAG, "stopRecordThumbRotate:" + thumbRotation);

        //如果不判断
        //当前已经停止了
        //还会执行动画
        //就有跳动问题
        if (THUMB_ROTATION_PAUSE == thumbRotation) {
            //已经是停止状态了

            //就返回
            return;
        }
        pauseThumbAnimator.start();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        //设置拖拽进度控件监听器
        sb_progress.setOnSeekBarChangeListener(this);

        //添加滚动监听事件
        vp.addOnPageChangeListener(this);
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

    //滚动事件改变了回调

    /**
     * 滚动中
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 滚动完成了
     * @param position
     */
    @Override
    public void onPageSelected(int position) {

    }

    /**
     * 滚动状态改变了
     *
     * @param state 滚动状态
     *
     * @see ViewPager#SCROLL_STATE_IDLE：空闲
     * @see ViewPager#SCROLL_STATE_DRAGGING：正在拖拽
     * @see ViewPager#SCROLL_STATE_SETTLING：滚动完成后
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        LogUtil.d(TAG, "onPageScrollStateChanged:" + state);

        if (SCROLL_STATE_DRAGGING==state){
            //拖拽状态

            //停止当前item滚动
            stopRecordRotate();
        }else if (SCROLL_STATE_IDLE == state){
            //空闲状态

            //判断黑胶唱片位置对应的音乐是否和现在播放的是一首
            Song song = listManager.getDatum().get(vp.getCurrentItem());
            if (listManager.getData().getId().equals(song.getId())){
                //一样

                //判断播放状态
                if (musicPlayerManager.isPlaying()){
                    startRecordRotate();
                }
            }else {
                //不一样

                //播放当前位置的音乐
                listManager.play(song);
            }
        }
    }

    /**
     * 开始滚动
     */
    private void startRecordRotate() {
        //旋转黑胶唱片指针
        startRecordThumbRotate();

        //获取当前播放的音乐
        Song data = listManager.getData();

        LogUtil.d(TAG,"startRecordRotate");

        //发送通知
        EventBus.getDefault().post(new OnStartRecordEvent(data));
    }

    /**
     * 播放器回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPlayEvent(OnPlayEvent event){
        //停止黑胶唱片滚动
        stopRecordRotate();
    }

    /**
     * 停止当前item滚动
     * 指针回到暂停状态
     */
    private void stopRecordRotate() {
        //停止旋转黑胶唱片指针
        stopRecordThumbRotate();

        //获取当前播放的音乐
        Song data = listManager.getData();

        LogUtil.d(TAG,"stopRecordRotate");

        //发送通知
        EventBus.getDefault().post(new OnStopRecordEvent(data));
    }
    //end滚动事件改变了回调

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

        //注册发布订阅框架
        EventBus.getDefault().register(this);

        //滚动到当前音乐位置
        scrollPosition();
    }

    /**
     * 滚动到当前音乐位置
     */
    private void scrollPosition() {
        //选中当前播放的音乐
        //异步执行
        vp.post(new Runnable() {
            @Override
            public void run() {
                //indexOf() 方法可返回某个指定的字符串值在字符串中首次出现的位置。
                //如果要检索的字符串值没有出现，则该方法返回 -1
                int index = listManager.getDatum().indexOf(listManager.getData());
                if (index!=-1){
                    //滚动到该位置
                    vp.setCurrentItem(index,false);
                }

                //判断是否需要旋转黑胶唱片
                if (musicPlayerManager.isPlaying()){
                    startRecordRotate();
                }else {
                    stopRecordRotate();
                }
            }
        });
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

        //解除注册发布订阅框架
        EventBus.getDefault().unregister(this);
    }

    /**
     * 播放列表改变了事件
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPlayListChangedEvent(PlayListChangedEvent event){
        if (listManager.getDatum().size() == 0) {
            //没有音乐了

            //关闭播放界面
            finish();
        }
    }

    //播放器回调
    @Override
    public void onPaused(Song data) {
        //显示播放状态
        showPlayStatus();

        //停止黑胶唱片滚动
        stopRecordRotate();
    }

    @Override
    public void onPlaying(Song data) {
        //显示暂停状态
        showPauseStatus();

        //开始黑胶唱片滚动
        startRecordRotate();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer, Song data) {
        //显示初始化数据
        showInitData();

        //显示时长
        showDuration();

        //滚动到当前音乐位置
        scrollPosition();
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