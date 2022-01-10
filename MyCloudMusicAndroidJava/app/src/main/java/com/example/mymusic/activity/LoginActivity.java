package com.example.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.mymusic.R;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseTitleActivity {
    private static final String TAG = "LoginActivity";
    /**
     * 用户名输入框
     */
    @BindView(R.id.et_username)
    EditText et_username;

    /**
     * 密码输入框
     */
    @BindView(R.id.et_password)
    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * 登录按钮点击了
     */
    @OnClick(R.id.bt_login)
    public void onLoginClick(Button view) {
        Log.d(TAG, "onLoginClick");
    }

    /**
     * 忘记密码按钮点击了
     */
    @OnClick(R.id.bt_forget_password)
    public void onForgetPasswordClick() {
        Log.d(TAG, "onForgetPasswordClick");
    }

}