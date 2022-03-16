package com.example.mymusic.activity;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.mymusic.R;
import com.example.mymusic.domain.Song;
import com.example.mymusic.listener.ListManager;
import com.example.mymusic.manager.MusicPlayerManager;
import com.example.mymusic.service.MusicPlayerService;
import com.example.mymusic.util.ImageUtil;
import com.example.mymusic.util.ResourceUtil;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * 黑胶唱片界面
 */
public class MusicPlayerActivity extends BaseTitleActivity {
    /**
     * 背景
     */
    @BindView(R.id.iv_background)
    ImageView iv_background;
    private ListManager listManager;
    private MusicPlayerManager musicPlayerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
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
                        iv_background.setImageDrawable(resource);
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
    }
}