package com.example.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.mymusic.AppContext;
import com.example.mymusic.MainActivity;
import com.example.mymusic.R;
import com.example.mymusic.api.Api;
import com.example.mymusic.domain.BaseModel;
import com.example.mymusic.domain.Session;
import com.example.mymusic.domain.User;
import com.example.mymusic.domain.response.DetailResponse;
import com.example.mymusic.listener.HttpObserver;
import com.example.mymusic.util.LogUtil;
import com.example.mymusic.util.StringUtil;
import com.example.mymusic.util.ToastUtil;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseLoginActivity {
    private static final String TAG = "RegisterActivity";
    /**
     * 昵称输入框
     */
    @BindView(R.id.et_nickname)
    EditText et_nickname;

    /**
     * 手机号输入框
     */
    @BindView(R.id.et_phone)
    EditText et_phone;

    /**
     * 邮箱输入框
     */
    @BindView(R.id.et_email)
    EditText et_email;

    /**
     * 密码输入框
     */
    @BindView(R.id.et_password)
    EditText et_password;

    /**
     * 确认密码输入框
     */
    @BindView(R.id.et_confirm_password)
    EditText et_confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    /**
     * 注册按钮点击了
     */
    @OnClick(R.id.bt_register)
    public void onRegisterClick(){
        //获取昵称
        String nickname = et_nickname.getText().toString().trim();
        if (StringUtils.isBlank(nickname)) {
            ToastUtil.errorShortToast(R.string.enter_nickname);
            return;
        }

        //判断昵称格式
        if (!StringUtil.isNickname(nickname)) {
            ToastUtil.errorShortToast(R.string.error_nickname_format);
            return;
        }

        //手机号
        String phone = et_phone.getText().toString().trim();
        if (StringUtils.isBlank(phone)) {
            ToastUtil.errorShortToast(R.string.enter_phone);
            return;
        }

        //手机号格式
        if (!StringUtil.isPhone(phone)) {
            ToastUtil.errorShortToast(R.string.error_phone_format);
            return;
        }

        //邮箱
        String email = et_email.getText().toString().trim();
        if (StringUtils.isBlank(email)) {
            ToastUtil.errorShortToast(R.string.enter_email);
            return;
        }

        //邮箱格式
        if (!StringUtil.isEmail(email)) {
            ToastUtil.errorShortToast(R.string.error_email_format);
            return;
        }

        //密码
        String password = et_password.getText().toString().trim();
        if (StringUtils.isBlank(password)) {
            ToastUtil.errorShortToast(R.string.enter_password);
            return;
        }

        //密码格式
        if (!StringUtil.isPassword(password)) {
            ToastUtil.errorShortToast(R.string.error_password_format);
            return;
        }

        //确认密码
        String confirmPassword = et_confirm_password.getText().toString().trim();
        if (StringUtils.isBlank(confirmPassword)) {
            ToastUtil.errorShortToast(R.string.enter_confirm_password);
            return;
        }

        //确认密码格式
        if (!StringUtil.isPassword(confirmPassword)) {
            ToastUtil.errorShortToast(R.string.error_confirm_password_format);
            return;
        }

        //判断密码和确认密码是否一样
        if (!password.equals(confirmPassword)) {
            ToastUtil.errorShortToast(R.string.error_confirm_password);
            return;
        }

        //调用注册接口完成用户注册
        User data = new User();

        //将输入框上的信息设置到对象上
        data.setNickname(nickname);
        data.setPhone(phone);
        data.setEmail(email);
        data.setPassword(password);

        //调用注册接口
        Api.getInstance().register(data)
                .subscribe(new HttpObserver<DetailResponse<BaseModel>>() {
                    @Override
                    public void onSucceeded(DetailResponse<BaseModel> data) {
                        LogUtil.d(TAG, "register success:" + data.getData().getId());

                        //调用父类自动登录方法
                        login(phone,email,password);
                    }
                });
    }
}