package com.example.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;

import com.example.mymusic.R;
import com.example.mymusic.api.Api;
import com.example.mymusic.domain.BaseModel;
import com.example.mymusic.domain.User;
import com.example.mymusic.domain.response.BaseResponse;
import com.example.mymusic.domain.response.DetailResponse;
import com.example.mymusic.listener.HttpObserver;
import com.example.mymusic.util.LogUtil;
import com.example.mymusic.util.StringUtil;
import com.example.mymusic.util.ToastUtil;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 忘记密码界面
 */
public class ForgetPasswordActivity extends BaseLoginActivity {
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

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;
    private CountDownTimer countDownTimer;

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

        //开始倒计时
        //startCountDown();

        //获取用户名
        String username = et_username.getText().toString().trim();
        if (StringUtils.isBlank(username)) {
            ToastUtil.errorShortToast(R.string.enter_username);
            return;
        }

        //判断用户名格式
        if (StringUtil.isPhone(username)) {
            //手机号
            sendSMSCode(username);
        } else if (StringUtil.isEmail(username)) {
            //邮箱
            sendEmailCode(username);
        } else {
            ToastUtil.errorShortToast(R.string.error_username_format);
        }
    }


    /**
     * 发送手机号验证码
     * @param value
     */
    private void sendSMSCode(String value) {
        User data = new User();
        data.setPhone(value);

        //调用接口
        Api.getInstance().sendSMSCode(data)
                .subscribe(new HttpObserver<DetailResponse<BaseModel>>() {
                    @Override
                    public void onSucceeded(DetailResponse<BaseModel> data) {
                        //发送成功了

                        //开始倒计时
                        startCountDown();
                    }
                });
    }

    /**
     * 发送邮箱验证码
     * @param value
     */
    private void sendEmailCode(String value) {

    }



    /**
     * 开始倒计时
     * 现在没有保存退出的状态
     * 也就说，返回在进来就可以点击了
     */
    private void startCountDown() {
        //倒计时的总时间,间隔
        //单位是毫秒
        countDownTimer = new CountDownTimer(60000, 1000) {
            /**
             * 每间隔时间调用
             * @param l
             */
            @Override
            public void onTick(long l) {
                bt_send_code.setText(getString(R.string.count_second,l/1000));
            }

            @Override
            public void onFinish() {
                bt_send_code.setText(R.string.send_code);

                //启动按钮
                bt_send_code.setEnabled(true);
            }
        };

        //启动
        countDownTimer.start();

        //禁用按钮
        bt_send_code.setEnabled(false);
    }

    /**
     * 界面销毁时调用
     */
    @Override
    protected void onDestroy() {
        //销毁定时器
        if (countDownTimer!=null){
            countDownTimer.cancel();;
            countDownTimer=null;
        }
        super.onDestroy();
    }

    /**
     * 重置密码按钮点击
     */
    @OnClick(R.id.bt_forget_password)
    public void onForgetPasswordClick() {
        LogUtil.d(TAG, "onForgetPasswordClick");

        //获取用户
        String username = et_username.getText().toString().trim();
        if (StringUtils.isBlank(username)) {
            ToastUtil.errorShortToast(R.string.enter_username);
            return;
        }

        //如果用户名
        //不是手机号也不是邮箱
        //就是格式错误
        if (!(StringUtil.isPhone(username) || StringUtil.isEmail(username))) {
            ToastUtil.errorShortToast(R.string.error_username_format);
            return;
        }

        //验证码
        String code = et_code.getText().toString().trim();
        if (StringUtils.isBlank(code)) {
            ToastUtil.errorShortToast(R.string.enter_code);
            return;
        }

        //验证码格式
        if (!StringUtil.isCode(code)) {
            ToastUtil.errorShortToast(R.string.error_code_format);
            return;
        }

        //获取密码
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

        //获取确认密码
        String confirmPassword = et_confirm_password.getText().toString().trim();
        if (StringUtils.isBlank(confirmPassword)) {
            ToastUtil.errorShortToast(R.string.enter_confirm_password);
            return;
        }

        //判断密码和确认密码是否一样
        if (!password.equals(confirmPassword)) {
            ToastUtil.errorShortToast(R.string.error_confirm_password);
            return;
        }

        //判断是手机号还是邮箱
        if (StringUtil.isPhone(username)) {
            //手机号
            phone = username;
        } else {
            //邮箱
            email = username;
        }

        //发送重置密码请求
        User data = new User();

        //将信息设置到对象上
        data.setPhone(phone);
        data.setEmail(email);
        data.setCode(code);
        data.setPassword(password);

        //调用接口
        Api.getInstance().resetPassword(data)
                .subscribe(new HttpObserver<BaseResponse>() {
                    @Override
                    public void onSucceeded(BaseResponse data) {
                        //重置成功

                        //调用父类登录方法
                        login(phone, email, password);
                    }
                });

    }
}
