package com.example.mymusic.util;

import android.content.Context;

import com.example.mymusic.domain.Song;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * 数据库工具类
 */
public class ORMUtil {
    /**
     * 单例
     */
    private static ORMUtil instance;

    /**
     * 上下文
     */
    private final Context context;
    private final PreferenceUtil sp;

    /**
     * 构造方法
     * @param context
     */
    public ORMUtil(Context context) {
        this.context=context.getApplicationContext();

        //初始化偏好设置
        sp = PreferenceUtil.getInstance(this.context);
    }

    /**
     * 获取数据工具类单例
     * @param context
     * @return
     */
    public static ORMUtil getInstance(Context context) {
        if (instance==null){
            instance=new ORMUtil(context);
        }
        return instance;
    }

    /**
     * 保存音乐
     * @param data
     */
    public void saveSong(Song data){
        //获取数据库对象
        Realm realm = getInstance();

        //关闭数据库
        realm.close();
    }

    /**
     * 获取数据库对象
     * @return
     */
    private Realm getInstance() {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(String.format("%s.realm", sp.getUserId()))

                .build();

        //返回数据库对象
        return Realm.getInstance(configuration);
    }

    /**
     * 销毁数据库对象
     */
    public static void destroy() {
        instance = null;
    }
}
