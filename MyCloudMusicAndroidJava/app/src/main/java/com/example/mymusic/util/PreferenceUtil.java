package com.example.mymusic.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * 偏好设计工具类
 * 是否登录了，是否显示引导界面，用户Id
 */
public class PreferenceUtil {
    /**
     * 偏好设置文件名称
     */
    private static final String NAME = "my_cloud_music";

    /**
     * 是否显示引导界面key
     */
    private static final String SHOW_GUIDE = "SHOW_GUIDE";

    /**
     * 用户登录Session key
     */
    private static final String SESSION = "SESSION";

    /**
     * 用户登录Session key
     */
    private static final String USER_ID = "USER_ID";


    /**
     * 实例
     */
    private static PreferenceUtil instance;

    /**
     * 上下文
     */
    private final Context context;
    private final SharedPreferences preference;

    public PreferenceUtil(Context context) {
        //保存上下文
        this.context=context.getApplicationContext();

        //这样写有内存泄漏
        //因为当前工具类不会马上释放
        //如果当前工具类引用了界面实例
        //当界面关闭后
        //因为界面对应在这里还有引用
        //所以会导致界面对象不会被释放
        //this.context = context;

        //获取偏好设置
        preference = this.context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    /**
     * 获取偏好设置单例
     * @param context
     * @return
     */
    public static PreferenceUtil getInstance(Context context) {
        if (instance==null)
            instance=new PreferenceUtil(context);
        return instance;
    }


    /**
     * 是否显示引导界面
     * @return
     */
    public boolean isShowGuide() {
        return preference.getBoolean(SHOW_GUIDE,true);
    }

    /**
     * 设置是否显示引导界面
     * @param value
     */
    public void setShowGuide(boolean value) {
        putBoolean(SHOW_GUIDE,value);

    }

    /**
     * 获取登录session
     * @return
     */
    public String getSession() {
       return preference.getString(SESSION,null);
    }

    /**
     * 保存登录session
     * @param value
     */
    public void setSession(String value) {
        putString(SESSION,value);
    }


    /**
     * 获取用户Id
     * @return
     */
    public String getUserId() {
        return preference.getString(USER_ID,null);
    }


    /**
     * 设置用户Id
     * @param value
     */
    public void setUserId(String value) {
        putString(USER_ID,value);
    }

    /**
     * 退出
     */
    public void logout() {
        delete(USER_ID);
        delete(SESSION);
    }

    //删除内容
    private void delete(String key) {
        preference.edit().remove(key).commit();
    }

    //辅助方法
    /**
     * 保存字符串
     * @param key
     * @param value
     */
    private void putString(String key, String value) {
        preference.edit().putString(key, value).commit();
    }

    /**
     * 保存boolen值
     * @param key
     * @param value
     */
    private void putBoolean(String key, boolean value) {
        preference.edit().putBoolean(key,value).commit();
    }
    //end 辅助方法

    /**
     * 是否登录了
     *
     * @return
     */
    public boolean isLogin() {
        return !TextUtils.isEmpty(getSession());
    }


}
