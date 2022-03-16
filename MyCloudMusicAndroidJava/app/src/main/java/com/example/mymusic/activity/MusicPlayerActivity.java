package com.example.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.mymusic.R;

/**
 * 黑胶唱片界面
 */
public class MusicPlayerActivity extends BaseTitleActivity {

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
}