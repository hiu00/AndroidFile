package com.example.mymusic;

import android.app.Application;

import com.example.mymusic.util.ToastUtil;

import es.dmoral.toasty.Toasty;

/**
 * 全局Application
 */
public class AppContext extends Application {
    /**
     * 创建了
     */
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化第三方toast工具类
        Toasty.Config.getInstance()
                .apply();

        //初始化toast工具类
        ToastUtil.init(getApplicationContext());
    }
}
