package com.example.mymusic.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.mymusic.util.PreferenceUtil;

import butterknife.ButterKnife;

/**
 * 通用界面逻辑
 */
public class BaseCommonActivity extends BaseActivity{

    //偏好设置实例
    protected PreferenceUtil sp;

    @Override
    protected void initViews() {
        super.initViews();

        //初始化注解找控件
        //绑定方法框架
        if(isBindView()){
            bindView();
        }
    }


    /**
     * 是否绑定View
     *
     * @return
     */
    protected boolean isBindView() {
        return true;
    }

    /**
     * 绑定View
     */
    protected void bindView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        sp = PreferenceUtil.getInstance(getMainActivity());
    }

    /**
     * 全屏
     */
    protected void fullScreen(){
        //设置界面全屏
        //获取decorView
        View decorView = getWindow().getDecorView();

        //判断版本
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            //11~18版本
            decorView.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //19及以上版本
            //SYSTEM_UI_FLAG_HIDE_NAVIGATION:隐藏导航栏
            //SYSTEM_UI_FLAG_IMMERSIVE_STICKY:从状态栏下拉会半透明悬浮显示一会儿状态栏和导航栏
            //SYSTEM_UI_FLAG_FULLSCREEN:全屏
            int options = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                    View.SYSTEM_UI_FLAG_FULLSCREEN;

            //设置到控件
            decorView.setSystemUiVisibility(options);
        }
    }

    /**
     * 隐藏状态栏
     */
    protected void hideStatusBar(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 状态栏文字显示白色
     * 内容显示到状态栏下
     */
    protected void lightStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //状态栏颜色设置为透明
            Window window = getWindow();

            //背景颜色透明
            window.setStatusBarColor(Color.TRANSPARENT);

            //去除半透明状态栏(如果有)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            //SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：让内容显示到状态栏
            //SYSTEM_UI_FLAG_LAYOUT_STABLE：状态栏文字显示白色
            //SYSTEM_UI_FLAG_LIGHT_STATUS_BAR：状态栏文字显示黑色
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }


    /**
     * 启动界面
     * @param clazz
     */
    protected void startActivity(Class<?> clazz){
        Intent intent = new Intent(getMainActivity(), clazz);
        startActivity(intent);
    }

    /**
     * 启动界面并关闭当前界面
     * @param clazz
     */
    protected void startActivoityAfterFinishThis(Class<?> clazz) {
        startActivity(clazz);
        finish();
    }

    /**
     * 获取字符串
     * @param key
     * @return
     */
    protected String extraString(String key) {
        return getIntent().getStringExtra(key);
    }

    /**
     * 获取界面方法
     * @return
     */
    public BaseCommonActivity getMainActivity(){
        return this;
    }
}
