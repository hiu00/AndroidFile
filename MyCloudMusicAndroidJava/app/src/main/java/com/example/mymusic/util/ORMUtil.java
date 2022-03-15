package com.example.mymusic.util;

import android.content.Context;

import com.example.mymusic.domain.Song;
import com.example.mymusic.domain.SongLocal;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * 数据库工具类
 */
public class ORMUtil {
    private static final String TAG = "ORMUtil";
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
        //将Song转为SongLocal对象
        SongLocal songLocal = data.toSongLocal();

        //获取数据库对象
        Realm realm = getInstance();

        //开启事务
        realm.beginTransaction();

        //新增或者更新
        realm.copyToRealmOrUpdate(songLocal);

        //提交事务
        realm.commitTransaction();

        //关闭数据库
        realm.close();

        //关闭数据库
        realm.close();

        LogUtil.d(TAG,"saveSong:"+songLocal.getTitle());
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

    /**
     * 保存所有音乐
     * @param datum
     */
    public void saveAll(List<Song> datum) {
        //获取数据库对象
        Realm realm = getInstance();

        //开启事务
        realm.beginTransaction();

        SongLocal songLocal = null;
        for (Song data : datum) {
            //将Song转为SongLocal对象
            songLocal = data.toSongLocal();

            //新增或者更新
            realm.copyToRealmOrUpdate(songLocal);
        }

        //提交事务
        realm.commitTransaction();

        //关闭数据库
        realm.close();
    }
}
