package com.example.mymusic;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.example.mymusic.domain.Session;
import com.example.mymusic.util.PreferenceUtil;
import com.example.mymusic.util.ToastUtil;
import com.facebook.stetho.Stetho;

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
    }

    private void onLogin() {

    }
}
