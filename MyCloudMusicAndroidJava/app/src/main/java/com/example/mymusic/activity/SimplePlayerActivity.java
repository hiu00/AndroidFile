package com.example.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.mymusic.R;

/**
 * 简单播放器
 */
public class SimplePlayerActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_player);
    }

    /**
     * 启动界面方法
     * @param activity 宿主activity
     */
    public static void start(Activity activity) {
        //创建意图
        //意图：就是要干什么
        Intent intent = new Intent(activity, SimplePlayerActivity.class);

        //启动界面
        activity.startActivity(intent);
    }
}