package com.example.kanmeitu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.kanmeitu.activity.BaseActivity;
import com.example.kanmeitu.activity.LoginActivity;
import com.example.kanmeitu.util.PreferenceUtil;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
    }

    /**
     * 退出
     * @param view
     */
    public void onLogout(View view) {
        //关闭偏好设置
        sp.setLogin(false);

        //退出后，跳转到登录界面
        //当然大家可以根据业务逻辑调整
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);

        sp.getEmail();



        //关闭
        finish();
    }
}