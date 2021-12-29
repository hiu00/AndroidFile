package com.example.kanmeitu.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.kanmeitu.MainActivity;
import com.example.kanmeitu.R;
import com.example.kanmeitu.util.PreferenceUtil;

public class SplashActivity extends BaseActivity {


    //把子线程的东西转换成UI线程 子线程不能修改UI线程

    /**
     * 启动页面
     * <p>
     * 3秒钟后进入登录界面
     * <p>
     * 启动界面根据苹果开发者文档可以理解为是用来让用户加快启动的
     * 而不是在上面显示你的广告和商标的（Android开发我们暂时没有找到相关的定义）
     */
    private Handler handler =new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.i("TAG", "handler  接受消息");
            next();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //3秒钟后调用，这里
                //这里调用是在子线程，不能直接操作UI，需要用handler切换到主线程
                //用多个线程的目的解决，如果有耗时任务，那么就会卡界面
                //而用了多线程后，将耗时任务放到子线程，这样主线程(UI线程)就不会卡住
                handler.sendEmptyMessage(0);
            }
        },3000);
    }

    private void next() {
        finish();
        Intent intent;
        if (sp.isLogin()) {
            intent = new Intent(this, MainActivity.class);
        }else {
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);

    }

}