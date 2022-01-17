package com.example.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.mymusic.R;
import com.example.mymusic.util.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 忘记密码界面
 */
public class ForgetPasswordActivity extends BaseTitleActivity {
    private static final String TAG = "ForgetPasswordActivity";
    //查找控件

    /**
     * 用户名输入框
     */
    @BindView(R.id.et_username)
    EditText et_username;

    /**
     * 验证码
     */
    @BindView(R.id.et_code)
    EditText et_code;

    /**
     * 发送验证码按钮
     */
    @BindView(R.id.bt_send_code)
    Button bt_send_code;

    /**
     * 密码
     */
    @BindView(R.id.et_password)
    EditText et_password;

    /**
     * 确认密码
     */
    @BindView(R.id.et_confirm_password)
    EditText et_confirm_password;

    //end查找控件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
    }

    //按钮点击方法

    /**
     * 发送验证码按钮点击
     */
    @OnClick(R.id.bt_send_code)
    public void onSendCodeClick() {
        LogUtil.d(TAG, "onSendCodeClick");
    }

    /**
     * 重置密码按钮点击
     */
    @OnClick(R.id.bt_forget_password)
    public void onForgetPasswordClick() {
        LogUtil.d(TAG, "onForgetPasswordClick");
    }
}