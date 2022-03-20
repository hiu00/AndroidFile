package com.example.mymusic.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.example.mymusic.listener.ListManager;
import com.example.mymusic.manager.GlobalLyricManager;
import com.example.mymusic.manager.MusicPlayerManager;
import com.example.mymusic.manager.impl.GlobalLyricManagerImpl;
import com.example.mymusic.manager.impl.ListManagerImpl;
import com.example.mymusic.manager.impl.MusicNotificationManager;
import com.example.mymusic.manager.impl.MusicPlayerManagerImpl;
import com.example.mymusic.util.LogUtil;
import com.example.mymusic.util.NotificationUtil;
import com.example.mymusic.util.ServiceUtil;
import com.google.android.material.tabs.TabLayout;

/**
 *
 */
public class MusicPlayerService extends Service {
    private static final String TAG = "MusicPlayerService";
    private MusicNotificationManager musicNotificationManager;


    /**
     * 构造方法
     */
    public MusicPlayerService() {
    }

    /**
     * 获取音乐播放Manager
     * @return
     */
    public static MusicPlayerManager getMusicPlayerManager(Context context){
        context = context.getApplicationContext();
        //尝试启动service
        ServiceUtil.startService(context,MusicPlayerService.class);

        return MusicPlayerManagerImpl.getInstance(context);
    }

    /**
     * 获取列表管理器
     * @param context
     * @return
     */
    public static ListManager getListManager(Context context){
        context = context.getApplicationContext();

        //尝试启动service
        ServiceUtil.startService(context,MusicPlayerService.class);

        return ListManagerImpl.getInstance(context);
    }

    /**
     * Service创建了
     * 类似Activity的onCreate
     */
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d(TAG,"onCreate");

        //初始化音乐通知管理器
        musicNotificationManager = MusicNotificationManager.getInstance(getApplicationContext());

        //初始化全局歌词管理器
        GlobalLyricManager globalLyricManager = GlobalLyricManagerImpl.getInstance(getApplicationContext());
    }

    /**
     * 启动service调用
     *
     * 多次启动也调用该方法
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d(TAG,"onStartCommand");

        //因为这个API是8.0才有的
        //所以要这样判断版本
        //不然低版本会崩溃
        if (Build.VERSION.SDK_INT>=26){
            //设置service为前台service

            //获取通知
            Notification notification = NotificationUtil.getServiceForeground(getApplicationContext());

            //Id写0：这个通知就不会显示
            //对于我们这里来说
            //就需要不显示
            startForeground(0,notification);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 销毁时调用
     */
    @Override
    public void onDestroy() {
        LogUtil.d(TAG,"onDestroy");

        //停止前台服务
        //true:移除之前的通知
        stopForeground(true);

        super.onDestroy();

    }
}