package com.example.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mymusic.R;

/**
 * 登陆注册界面
 */
public class LoginOrRegisterActivity extends BaseCommonActivity implements View.OnClickListener {

    private static final String TAG = "LoginOrRegisterActivity";
    private Button bt_login;
    private Button bt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //找登陆控件
        bt_login = findViewById(R.id.bt_login);
        //找注册控件
        bt_register = findViewById(R.id.bt_register);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        //设置登录按钮监听器
        bt_login.setOnClickListener(this);
        //设置注册按钮监听器
        bt_register.setOnClickListener(this);
    }

    /**
     * 按钮点击回调
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_login:
                //登录按钮点击
                Log.d(TAG, "onClick: login");



                startActivity(LoginActivity.class);
                break;
            case R.id.bt_register:
                //注册按钮点击
                Log.d(TAG, "onClick: register");

                startActivity(RegisterActivity.class);
                break;
        }

    }
}