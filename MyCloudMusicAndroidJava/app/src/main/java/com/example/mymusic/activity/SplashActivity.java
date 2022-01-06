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

        //使用重构后的方法
        startActivoityAfterFinishThis(GuideActivity.class);
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

        //上面的handler是一个异步线程  等待三面之后出发run（）方法

        //actvity也有一个线程--->UI线程


        //多个线程执行互不影响  你走你的阳关道，我走我的独木桥

        //继承的意思
     }

    @Override
    protected void initDatum() {
        super.initDatum();
        //测试使用偏好设置
        //获取偏好设置对象
        SharedPreferences preferences=getSharedPreferences("ixuea",MODE_PRIVATE);

        //保存一个字符串"我们是爱学啊"
        //存储的键为：username
        preferences.edit().putString("username","我们是爱学啊").commit();

        //通过键找到上面存储的值
        String username = preferences.getString("username", null);

        //打印出来，方便调试
        Log.d(TAG, "initDatum: "+"第一次获取的值："+username);

        //删除该key对应的值
        preferences.edit().remove("username").commit();

        //再次获取
        username=preferences.getString("username",null);

        Log.d(TAG, "initDatum: "+"删除后再次获取的值："+username);
    }
}