package com.example.mymusic.activity;

import android.content.Intent;

/**
 * 通用界面逻辑
 */
public class BaseCommonActivity extends BaseActivity{
    /**
     * 启动界面
     * @param clazz
     */
    protected void startActivity(Class<?> clazz){
        Intent intent = new Intent(getMainActivity(), clazz);
        startActivity(intent);
    }

    /**
     * 启动界面并关闭当前界面
     * @param clazz
     */
    protected void startActivoityAfterFinishThis(Class<?> clazz) {
        startActivity(clazz);
        finish();
    }

    public BaseCommonActivity getMainActivity(){
        return this;
    }
}
