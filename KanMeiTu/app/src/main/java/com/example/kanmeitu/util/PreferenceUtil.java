package com.example.kanmeitu.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;

import com.example.kanmeitu.R;

/**
 * 工具类
 */
public class PreferenceUtil {
    private static final String KEY_LOGIN = "KEY_LOGIN";
    private static PreferenceUtil instance;   //静态  就代表只有一个  无论这个类实例化多少次这个静态的属性只有一个
    private final Context context;
    private final SharedPreferences perference;



    //万物皆对象-----对象就是类   万物揭示类







    //等这个类执行完毕就会创建一个文件
//配置文件----这个文件记录了你使用的习惯
    //比如   一个微信里面的设置   有所得设置都保存在这个文件里
//    比如说微信里面的深色模式   可以设置浅色  也可以设置黑色  要把这些用的设置记录到被指文件里
    /**
     * 构造方法
     * @param context
     */
    public PreferenceUtil(Context context) {
        this.context=context.getApplicationContext(); //上下文---指这个软件的进程

        //   如果要是Activity  的 context 指代是  当前Activity

        //创建配置文件
        perference = this.context.getSharedPreferences("ixuea", Context.MODE_PRIVATE);
    }

    //SharedPreferences--->操作配置文件的  有创建 等等



    //单例就是真个应程序当中或者是进程中只有一个
    /**
     * 获取偏好设置工具类（单例设计模式）
     * @param context
     * @return
     */
    public static PreferenceUtil getInstance(Context context) {
        if (instance==null){

            //一个类的构造函数  在实例化的时候就会被调用
            instance=new PreferenceUtil(context);
        }
        return instance;
    }

//    String  int<--->Integer       double<---->Double
//    float<---->Float    long<---->Long   short<------> Short
//    char 字符
//    boolean  true   false
    /**
     * 设置是否登陆
     * @param data
     *                     键----值   键值对
     * 配置文件的写入形式    key --- value
     * 以身份证为代表     晏传利 --- 身份证号
     * 性别    男
     * 年龄    14
     * qq号             账号 ------  密码
     *
     */
    public void setLogin(boolean data) {
        perference.edit().putBoolean(KEY_LOGIN,data).commit();
    }


    /**
     * 是否登陆
     * @return
     */
    public boolean isLogin() {
        return perference.getBoolean(KEY_LOGIN,false);
    }

    public void setEmail(String email) {
        perference.edit().putString(String.valueOf(R.id.et_email),email).commit();
    }

    public void getEmail() {
        perference.getString(String.valueOf(R.id.et_email),null);
    }


    //perference---就是对配置文件的操作类   创建配置文件   写入配置文件  读取配置

}
