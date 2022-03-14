package com.example.mymusic.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.mymusic.MainActivity;
import com.example.mymusic.R;
import com.example.mymusic.domain.Song;

/**
 * 通知工具类
 */
public class NotificationUtil {
    /**
     * 通知渠道
     */
    private static final String IMPORTANCE_LOW_CHANNEL_ID = "IMPORTANCE_LOW_CHANNEL_ID";
    private static final String TAG = "NotificationUtil";

    /**
     * 通知管理器实例
     */
    private static NotificationManager notificationManager;

    public static Notification getServiceForeground(Context context) {//获取通知管理器
        getNotificationManager(context);

        //创建通知渠道
        createNotificationChannel();


        //创建一个通知
        //内容随便写
        Notification notification = new NotificationCompat.Builder(context, IMPORTANCE_LOW_CHANNEL_ID)
                //通知标题
                .setContentTitle("我是晏传利")

                //通知内容
                .setContentText("好好学习，天天向上！")

                //通知小图标
                .setSmallIcon(R.mipmap.ic_launcher)

                //通知大图标
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))

                //创建一个通知
                .build();

        //返回
        return notification;
    }

    /**
     * 创建通知渠道
     */
    private static void createNotificationChannel() {
        //因为这个API是8.0及以上版本才有
        //所以要这样判断版本
        //不然低版本会奔溃
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            //创建渠道
            //可以多次创建
            //但Id一样只会创建一个
            NotificationChannel channel = new NotificationChannel(IMPORTANCE_LOW_CHANNEL_ID,"重要通知", NotificationManager.IMPORTANCE_HIGH);

            //创建一个渠道
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * 获取通知管理器
     */
    private static void getNotificationManager(Context context) {
        if (notificationManager==null){
            notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    /**
     * 显示通知
     * @param id
     * @param notification
     */
    public static void showNotification(int id, Notification notification) {
        notificationManager.notify(id,notification);
    }

    /**
     * 显示音乐通知
     * @param context
     * @param data
     * @param isPlaying
     */
    public static void showMusicNotification(Context context, Song data, boolean isPlaying) {
        LogUtil.d(TAG, "showMusicNotification:" + data.getTitle() + "," + isPlaying);

        //先加载图片
        //因为我们的图片是在线的
        //而显示通知时没法直接显示网络图片
        //所以需要我们先把图片下载下来

        //创建请求选项
        RequestOptions options = ImageUtil.getCommonRequestOptions();

        //下载图片
        Glide.with(context)
                .asBitmap()
                .load(ResourceUtil.resourceUri(data.getBanner()))
                .apply(options)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        //图片下载完成
                        showMusicNotification(context,data,isPlaying,resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

    }

    /**
     * 显示音乐通知
     * @param context
     * @param data
     * @param isPlaying
     * @param resource
     */
    public static void showMusicNotification(Context context, Song data, boolean isPlaying, Bitmap resource) {
        //创建通知渠道
        createNotificationChannel();

        //创建RemoteView
        //显示自定义通知固定写法
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification_music_play);

        //显示数据
        setData(data, contentView, isPlaying,resource);

        //设置通知点击事件
        setClick(context, contentView);

        //创建大通知
        RemoteViews contentBigView = new RemoteViews(context.getPackageName(), R.layout.notification_music_play_large);

        //显示数据
        setData(data, contentBigView, isPlaying,resource);

        //设置通知点击事件
        setClick(context, contentBigView);

        //点赞
        PendingIntent likePendingIntent = PendingIntent.getBroadcast(context, Constant.ACTION_LIKE.hashCode(), new Intent(Constant.ACTION_LIKE), PendingIntent.FLAG_UPDATE_CURRENT);
        contentBigView.setOnClickPendingIntent(R.id.iv_like,likePendingIntent);

        //上一首
        PendingIntent previousPendingIntent = PendingIntent.getBroadcast(context,
                Constant.ACTION_PREVIOUS.hashCode(),
                new Intent(Constant.ACTION_PREVIOUS),
                PendingIntent.FLAG_UPDATE_CURRENT);
        contentBigView.setOnClickPendingIntent(R.id.iv_previous, previousPendingIntent);

        //设置通知点击后启动的界面
        Intent intent = new Intent(context, MainActivity.class);

        //传递一个动作
        intent.setAction(Constant.ACTION_MUSIC_PLAY_CLICK);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //创建通知点击的PendingIntent
        PendingIntent contentPendingIntent = PendingIntent.getActivity(context, Constant.ACTION_MUSIC_PLAY_CLICK.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //创建NotificationCompat.Builder
        //这是构建者设计模式
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, IMPORTANCE_LOW_CHANNEL_ID)

                //点击后不消失
                .setAutoCancel(false)

                //小图标
                .setSmallIcon(R.mipmap.ic_launcher)

                //设置样式
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())

                //自定义内容view
                .setCustomContentView(contentView)

                //设置大内容view
                .setCustomBigContentView(contentBigView)

                //点击后执行的动作
                .setContentIntent(contentPendingIntent);

        //显示通知
        NotificationUtil.notify(context,Constant.NOTIFICATION_MUSIC_ID,builder.build());
    }

    /**
     * 设置通知点击事件
     * @param context
     * @param contentView
     */
    private static void setClick(Context context, RemoteViews contentView) {
        //播放按钮点击事件
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(context, Constant.ACTION_PLAY.hashCode(), new Intent(Constant.ACTION_PLAY), PendingIntent.FLAG_UPDATE_CURRENT);
        //设置到播放按钮
        contentView.setOnClickPendingIntent(R.id.iv_play,playPendingIntent);

        //下一曲点击事件
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(context, Constant.ACTION_NEXT.hashCode(), new Intent(Constant.ACTION_NEXT), PendingIntent.FLAG_UPDATE_CURRENT);
        contentView.setOnClickPendingIntent(R.id.iv_next,nextPendingIntent);

        //歌词点击事件
        PendingIntent lyricPendingIntent = PendingIntent.getBroadcast(context, Constant.ACTION_LYRIC.hashCode(), new Intent(Constant.ACTION_LYRIC), PendingIntent.FLAG_UPDATE_CURRENT);
        contentView.setOnClickPendingIntent(R.id.iv_lyric,lyricPendingIntent);
    }

    /**
     * 显示数据
     * @param data
     * @param contentView
     * @param isPlaying
     * @param resource
     */
    private static void setData(Song data, RemoteViews contentView, boolean isPlaying, Bitmap resource) {
        //封面
        contentView.setImageViewBitmap(R.id.iv_banner,resource);

        //标题
        contentView.setTextViewText(R.id.tv_title,data.getTitle());

        //信息
        //由于服务端没有实现专辑
        //所以就显示测试信息
        contentView.setTextViewText(R.id.tv_info,String.format("%s - 这是专辑1",data.getSinger().getNickname()));

        //显示播放按钮
        int playButtonResourceId = isPlaying?R.drawable.ic_music_notification_pause:R.drawable.ic_music_notification_play;

        contentView.setImageViewResource(R.id.iv_play,playButtonResourceId);
    }

    /**
     * 显示通知
     * @param context
     * @param id
     * @param notification
     */
    public static void notify(Context context, int id, Notification notification) {
        //获取通知管理器
        getNotificationManager(context);

        //显示通知
        notificationManager.notify(id, notification);
    }
}
