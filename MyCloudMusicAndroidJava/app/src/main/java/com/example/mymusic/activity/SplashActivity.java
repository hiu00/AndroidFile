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

import com.example.mymusic.R;
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


    }
}