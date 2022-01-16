package com.example.mymusic.activity;

import com.example.mymusic.AppContext;
import com.example.mymusic.MainActivity;
import com.example.mymusic.R;
import com.example.mymusic.api.Api;
import com.example.mymusic.domain.Session;
import com.example.mymusic.domain.User;
import com.example.mymusic.domain.response.DetailResponse;
import com.example.mymusic.listener.HttpObserver;
import com.example.mymusic.util.LogUtil;
import com.example.mymusic.util.ToastUtil;

/**
 * 用户登录相关功能
 */
public class BaseLoginActivity extends BaseTitleActivity{
    private static final String TAG = "BaseLoginActivity";

    /**
     * 登录
     * @param phone
     * @param email
     * @param password
     */
    protected void login(String phone,String email, String password){
        User user = new User();

        //这里虽然同时传递了手机号和邮箱
        //但服务端登录的时候有先后顺序
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(password);

        //调用登录接口
        Api.getInstance().login(user)
                .subscribe(new HttpObserver<DetailResponse<Session>>() {
                    @Override
                    public void onSucceeded(DetailResponse<Session> data) {
                        LogUtil.d(TAG,"onLoginClick success:"+data.getData());

                        //把登录成功的事件通知到AppContext
                        AppContext.getInstance().login(sp,data.getData());

                        ToastUtil.successLongToast(R.string.login_sucess);

                        //关闭当前界面并启动主界面
                        startActivoityAfterFinishThis(MainActivity.class);
                    }
                });
    }
}
