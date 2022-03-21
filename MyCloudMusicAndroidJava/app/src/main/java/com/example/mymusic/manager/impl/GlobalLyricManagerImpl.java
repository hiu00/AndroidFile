package com.example.mymusic.manager.impl;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.mymusic.manager.GlobalLyricManager;
import com.example.mymusic.view.GlobalLyricView;

/**
 * 歌词管理器实现
 */
public class GlobalLyricManagerImpl implements GlobalLyricManager {
    /**
     * 实例
     */
    private static GlobalLyricManagerImpl instance;

    /**
     * 上下文
     */
    private static Context context;

    /**
     * 窗口管理器
     */
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private GlobalLyricView globalLyricView;

    public GlobalLyricManagerImpl(Context context) {
        this.context=context.getApplicationContext();

        //初始化窗口管理器
        //initWindowManager();

        //创建全局歌词View
        //initGlobalLyricView();

    }

    /**
     * 获取全局歌词管理器对象
     * @return
     */
    public static GlobalLyricManager getInstance(Context context) {
        if (instance == null) {
            instance=new GlobalLyricManagerImpl(context);
        }
        return instance;
    }

    /**
     * 初始化窗口管理器
     */
    private void initWindowManager(){
        if (windowManager == null){
            //获取系统窗口管理器
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

            //窗体的布局样式
            layoutParams = new WindowManager.LayoutParams();

            //设置窗体显示类型
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //6.0及以上版本要设置该类型
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            }

            //设置显示的模式
            layoutParams.format = PixelFormat.RGBA_8888;

            //设置对齐的方法
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;

            //设置窗体宽度和高度
            DisplayMetrics dm = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(dm);

            //和屏幕一样宽
            layoutParams.width = dm.widthPixels;

            //高度是包裹内容
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

            //从偏好设置中获取坐标
            layoutParams.y = 100;
        }
    }

    /**
     * 创建全局歌词View
     */
    private void initGlobalLyricView() {
//        //创建一个测试文本
//        TextView tv = new TextView(context);
//        tv.setText("这是一个简单的文本");
//
//        if (tv.getParent() == null) {
//            //如果没有添加
//            //就添加
//            //windowManager.addView(tv, layoutParams);
//        }

        if (globalLyricView==null){
            //创建全局歌词控件
            globalLyricView = new GlobalLyricView(context);
        }
        if (globalLyricView.getParent()==null){
            //如果没有添加
            //就添加
            windowManager.addView(globalLyricView,layoutParams);
        }
    }
    
    @Override
    public void show() {
        
    }

    @Override
    public void hide() {

    }

    @Override
    public boolean isShowing() {
        return false;
    }

    @Override
    public void tryHide() {

    }

    @Override
    public void tryShow() {

    }
}
