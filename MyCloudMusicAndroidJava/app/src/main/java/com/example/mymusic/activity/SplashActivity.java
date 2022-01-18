package com.example.mymusic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.mymusic.MainActivity;
import com.example.mymusic.R;
import com.example.mymusic.util.Constant;
import com.example.mymusic.util.LogUtil;
import com.example.mymusic.util.PreferenceUtil;

public class SplashActivity extends BaseCommonActivity {

    private static final String TAG = "SplashActivity";

    /**
     * 下一步常量
     */
    private static final int MESSAGE_NEXT = 100;

    /**
     * 默认延时时间
     */
    private static final long DEFAULT_DELAY_TIME = 3000;

    /**
     * 创建Handler
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_NEXT:
                    next();
                    break;
            }
        }
    };

    //下一步
    private void next() {
        Log.d(TAG, "next");

//        Intent intent = new Intent(this, GuideActivity.class);
//        startActivity(intent);
//
//        finish();

        if(sp.isShowGuide()) {

            //显示引导界面
              startActivoityAfterFinishThis(GuideActivity.class);
        }else if(sp.isLogin()){
            //已经登录了

            //就显示广告页面；在广告页面再进入主界面
            //可以根据自己的需求来更改
            //同时只有用户登录了
            //才显示也给用户有更好的体验
            startActivoityAfterFinishThis(AdActivity.class);
        }else {
            //跳转到登录/注册页面
            startActivoityAfterFinishThis(LoginOrRegisterActivity.class);

       }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //设置全屏
        fullScreen();

        //延时3秒
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(MESSAGE_NEXT);
            }
        }, DEFAULT_DELAY_TIME);

     }

    @Override
    protected void initDatum() {
        super.initDatum();
        //测试productFlavors
        //获取ENDPOINT常量
        LogUtil.d(TAG, "initDatum:" + Constant.ENDPOINT);

    }
}