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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mymusic.Fragment.MusicPlayerAdapter;
import com.example.mymusic.Fragment.PlayListDialogFragment;
import com.example.mymusic.R;
import com.example.mymusic.adapter.LyricAdapter;
import com.example.mymusic.domain.Lyric.Lyric;
import com.example.mymusic.domain.Song;
import com.example.mymusic.domain.event.OnPlayEvent;
import com.example.mymusic.domain.event.OnRecordClickEvent;
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
import com.example.mymusic.util.lyric.LyricUtil;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * 黑胶唱片界面
 */
public class MusicPlayerActivity extends BaseTitleActivity implements MusicPlayerListener, SeekBar.OnSeekBarChangeListener, ViewPager.OnPageChangeListener, ValueAnimator.AnimatorUpdateListener, BaseQuickAdapter.OnItemClickListener, ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "MusicPlayerActivity";
    /**
     * 歌词填充多个占位数据
     */
    private int lyricPlaceholderSize;

    /**
     * 背景
     */
    @BindView(R.id.iv_background)
    ImageView iv_background;

    /**
     * 歌词容器
     */
    @BindView(R.id.rl_lyric)
    View rl_lyric;

    /**
     * 歌词拖拽容器
     */
    @BindView(R.id.ll_lyric_drag)
    View ll_lyric_drag;

    /**
     * 歌词列表控件
     */
    @BindView(R.id.rv)
    RecyclerView rv;

    /**
     * 黑胶唱片容器
     */
    @BindView(R.id.cl_record)
    View cl_record;

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
    private LyricAdapter lyricAdapter;
    private int lineNumber;
    private int lyricOffsetX;
    private LinearLayoutManager layoutManager;
    private boolean isDrag;
    private TimerTask lyricTimerTask;
    private Timer lyricTimer;

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

        //尺寸固定
        rv.setHasFixedSize(true);

        //设置布局管理器
        layoutManager = new LinearLayoutManager(getMainActivity());
        rv.setLayoutManager(layoutManager);
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

        //创建歌词列表适配器
        lyricAdapter = new LyricAdapter(R.layout.item_lyric);

        //设置歌词点击事件
        //lyricAdapter.setOnItemClickListener(this);

        //设置适配器
        rv.setAdapter(lyricAdapter);
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

        //设置歌词点击事件
        lyricAdapter.setOnItemClickListener(this);

        //添加布局监听器
        rv.getViewTreeObserver().addOnGlobalLayoutListener(this);

        //添加歌词列表滚动事件
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * 滚动状态改变了
             * 状态ViewPager一样
             *
             * @param recyclerView
             * @param newState
             */
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (SCROLL_STATE_DRAGGING == newState) {
                    //拖拽状态
                    showDragView();
                } else if (SCROLL_STATE_IDLE == newState) {
                    //空闲状态
                    prepareScrollLyricView();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    /**
     * 准备滚动歌词
     */
    private void prepareScrollLyricView() {
        //延迟几秒钟后隐藏

        //取消隐藏歌词拖拽效果原来的任务
        cancelLyricTask();

        //创建任务
        //转换到主线程
        lyricTimerTask = new TimerTask() {
            @Override
            public void run() {
                //转换到主线程
                vp.post(new Runnable() {
                    @Override
                    public void run() {
                        enableScrollLyric();
                    }
                });
            }
        };

        //创建定时器
        lyricTimer = new Timer();

        //开启一个倒计时
        lyricTimer.schedule(lyricTimerTask, Constant.LYRIC_HIDE_DRAG_TIME);

    }

    /**
     * 开始歌词滚动
     */
    private void enableScrollLyric() {
        isDrag = false;

        //隐藏歌词拖拽效果
        ll_lyric_drag.setVisibility(View.GONE);
    }

    /**
     * 取消隐藏歌词拖拽效果原来的任务
     */
    private void cancelLyricTask() {
        if (lyricTimerTask!=null){
            lyricTimerTask.cancel();
            lyricTimerTask=null;
        }

        if (lyricTimer!=null){
            lyricTimer.cancel();
            lyricTimer=null;
        }
    }

    /**
     * 显示歌词拖拽控件
     */
    private void showDragView() {
        if (isLyricEmpty()) {
            //没有歌词不能拖拽
            return;
        }

        //拖拽状态
        isDrag = true;

        //显示歌词拖拽控件
        ll_lyric_drag.setVisibility(View.VISIBLE);
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
     * 黑胶唱片点击事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecordClickEvent(OnRecordClickEvent event) {
        if (isLyricEmpty()){
            //没有歌词
            ToastUtil.errorShortToast(R.string.lyric_empty);
            return;
        }

        //隐藏黑胶唱片
        cl_record.setVisibility(View.GONE);

        //显示歌词
        rl_lyric.setVisibility(View.VISIBLE);
    }

    private boolean isLyricEmpty() {
        return lyricAdapter.getItemCount()==0;
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

        //显示歌词
        showLyricData();
    }

    /**
     * 显示歌词
     */
    private void showLyricData() {
        if (lyricPlaceholderSize > 0) {
            //已经计算了填充数量
            next();
        } else{
            //还没有计算

            //使用异步任务获取
            vp.post(new Runnable() {
                @Override
                public void run() {
                    //计算占位数
                    int height = vp.getMeasuredHeight();

                    lyricPlaceholderSize= (int) Math.ceil(height / 1.0 / 2 / DensityUtil.dip2px(getMainActivity(), 40));

                    //执行下一步操作
                    next();
                }
            });
        }

    }

    /**
     * 设置歌词数据
     */
    private void next() {
        //获取当前播放的音乐
        Song data = listManager.getData();

        if (data == null || data.getParsedLyric() == null) {
            //清空原来的歌词
            lyricAdapter.replaceData(new ArrayList<>());
        } else {
            //创建列表
            ArrayList<Object> datum = new ArrayList<>();

            //添加占位数据
            addLyricFillData(datum);

            //添加真实数据
            datum.addAll(data.getParsedLyric().getDatum());

            //添加占位数据
            addLyricFillData(datum);

            //设置歌词数据到歌词控件
            lyricAdapter.replaceData(datum);
        }
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

        //显示歌词进度
        showLyricProgress(listManager.getData().getProgress());
    }

    /**
     * 显示歌词进度
     *
     * @param progress
     */
    private void showLyricProgress(long progress) {
        Lyric lyric = listManager.getData().getParsedLyric();
        if (lyric==null){
            return;
        }
        if (isDrag){
            return;
        }
        //获取当前时间对应的歌词索引
        int newLineNumber = LyricUtil.getLineNumber(lyric, progress)+lyricPlaceholderSize;

        if (newLineNumber!=lineNumber){
            //滚动到当前行
            scrollLyricPosition(newLineNumber);

            lineNumber=newLineNumber;
        }
    }

    /**
     * 滚动到当前歌词行
     *
     * @param lineNumber
     */
    private void scrollLyricPosition(int lineNumber) {
        rv.post(new Runnable() {
            @Override
            public void run() {
                //选中当前行歌词
                lyricAdapter.setSelectedIndex(lineNumber);

                //该方法会将指定item滚动到顶部
                //offset是滚动到顶部后，在向下(+)偏移多少
                //如果我们想让一个Item在RecyclerView中间
                //那么偏移为RecyclerView.height/2

                //动态获取RecyclerView.height
                //兼容性更好
                if (lyricOffsetX > 0) {
                    //大于0才滚动
                    layoutManager.scrollToPositionWithOffset(lineNumber, lyricOffsetX);
                }
            }
        });
    }

    /**
     * 歌词改变了
     * @param data
     */
    @Override
    public void onLyricChanged(Song data) {
        //显示歌词
        showLyricData();
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

    /**
     * 歌词点击事件
     *
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //显示黑胶唱片
        cl_record.setVisibility(View.VISIBLE);

        //隐藏歌词
        rl_lyric.setVisibility(View.GONE);
    }

    /**
     * 布局改变了
     */
    @Override
    public void onGlobalLayout() {
        //获取控件高度
        lyricOffsetX = vp.getHeight() / 2 - (DensityUtil.dip2px(getMainActivity(), 40) / 2);

        //用完以后要移出
        //因为界面会一直变动
        vp.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    //播放器回调end

    /**
     * 添加歌词占位数据
     * @param datum
     */
    private void addLyricFillData(List<Object> datum) {
        for (int i = 0; i < lyricPlaceholderSize; i++) {
            datum.add("fill");
        }
    }
}