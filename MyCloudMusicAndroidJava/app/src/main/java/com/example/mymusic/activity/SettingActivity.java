package com.example.mymusic.activity;

/**
 * 设置界面
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mymusic.AppContext;
import com.example.mymusic.R;

import butterknife.OnClick;

public class SettingActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    /**
     * 退出按钮点击了
     */
    @OnClick(R.id.bt_logout)
    public void onLogoutClick(){
        //退出
        AppContext.getInstance().logout();
    }

    /**
     * 退出了通知
     */
    private void onLogout() {

    }
}