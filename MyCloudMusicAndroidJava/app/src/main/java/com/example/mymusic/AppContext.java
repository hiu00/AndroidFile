package com.example.mymusic;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.multidex.MultiDexApplication;

import com.example.mymusic.activity.LoginOrRegisterActivity;
import com.example.mymusic.domain.Session;
import com.example.mymusic.domain.event.LoginSuccessEvent;
import com.example.mymusic.util.PreferenceUtil;
import com.example.mymusic.util.ToastUtil;
import com.facebook.stetho.Stetho;

import org.greenrobot.eventbus.EventBus;

import es.dmoral.toasty.Toasty;

/**
 * 全局Application
 */
public class AppContext extends MultiDexApplication {

    /**
     * 上下文
     */
    private static AppContext context;

    /**
     * 获取当前上下文
     * @return
     */
    public static AppContext getInstance() {
        return context;
    }

    /**
     * 创建了
     */
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;

        //初始化第三方toast工具类
        Toasty.Config.getInstance()
                .apply();

        //初始化toast工具类
        ToastUtil.init(getApplicationContext());

        //初始化Stetho抓包
        //使用默认参数初始化
        Stetho.initializeWithDefaults(this);
    }

    public void login(PreferenceUtil sp, Session data){
        //保存登录后的Session
        sp.setSession(data.getSession());

        //保存用户Id
        sp.setUserId(data.getUser());

        //初始化,其他登录后才需要初始化的内容
        onLogin();

        //发送登录成功通知
        //目的是一些界面需要接收该事件
        EventBus.getDefault().post(new LoginSuccessEvent());
    }

    private void onLogin() {

    }

    /**
     * 退出登录
     */
    public void logout() {
        //清除登录相关信息
        PreferenceUtil.getInstance(getApplicationContext()).logout();

        //TODO 清除第三方平台登录信息

        //退出后跳转到登录注册界面
        //因为我们的应用实现的是必须登录才能进入首页
        Intent intent = new Intent(getApplicationContext(), LoginOrRegisterActivity.class);

        //在Activity以外启动界面
        //都要写这个标识
        //具体的还比较复杂
        //基础课程中讲解
        //这里学会这样用就行了
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //启动界面
        startActivity(intent);

        //退出了通知
        onLogout();
    }

    /**
     * 退出了通知
     */
    private void onLogout() {

    }
}
