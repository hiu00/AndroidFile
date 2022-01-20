package com.example.mymusic.api;

import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.example.mymusic.AppContext;
import com.example.mymusic.domain.BaseModel;
import com.example.mymusic.domain.Session;
import com.example.mymusic.domain.Sheet;
import com.example.mymusic.domain.User;
import com.example.mymusic.domain.response.BaseResponse;
import com.example.mymusic.domain.response.DetailResponse;
import com.example.mymusic.domain.response.ListResponse;
import com.example.mymusic.util.Constant;
import com.example.mymusic.util.LogUtil;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求接口包装类
 * 对外部提供一个和框架无关的接口
 */
public class Api {
    /**
     * 实例
     */
    private static Api instance;

    /**
     * Service单例
     */
    private final Service service;

    /**
     * 返回当前对象的唯一实例
     *
     * 单例设计模式
     * 由于移动端很少有高并发
     * 所以这个就是简单判断
     *
     * @return
     */
    public static Api getInstance(){
        if(instance==null){
            instance=new Api();
        }
        return instance;
    }


    public Api() {
        //初始化OkHttp
        OkHttpClient.Builder OkHttpClientBuilder=new OkHttpClient.Builder();

        if (LogUtil.isDebug) {
            //调试模式

            //创建koHttp日志拦截器
            HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();

            //设置日志等级
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BASIC);

            //添加到网络框架中
            OkHttpClientBuilder.addInterceptor(loggingInterceptor);

            //添加Stetho抓包拦截器
            OkHttpClientBuilder.addNetworkInterceptor(new StethoInterceptor());

            //添加chucker实现应用内显示网络请求信息拦截器
            OkHttpClientBuilder.addInterceptor(new ChuckerInterceptor(AppContext.getInstance()));
        }

        //创建一个Retrofit
        Retrofit retrofit=new Retrofit.Builder()
                //让Retrofit使用OkHttp请求网络
                .client(OkHttpClientBuilder.build())

                //这里就是配置API地址
                .baseUrl(Constant.ENDPOINT)

                //适配RxJava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                //自动JSON转换
                //包括请求参数和相应
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //创建一个service
        service = retrofit.create(Service.class);
    }

    /**
     * 歌单列表
     *
     * @return
     */
    public Observable<ListResponse<Sheet>> sheets(){
        return service.sheets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 歌单详情
     *
     * @param id
     * @return
     */
    public Observable<DetailResponse<Sheet>> sheetDetail(String id) {
        return service.sheetDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 注册
     *
     * @param data
     * @return
     */
    public Observable<DetailResponse<BaseModel>> register(User data){
        return service.register(data)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 登录
     * @param data
     * @return
     */
    public Observable<DetailResponse<Session>> login(User data){
        return service.login(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 重置密码
     * @param data
     * @return
     */
    public Observable<BaseResponse> resetPassword(User data){
        return service.resetPassword(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 发送短信验证码
     * @param data
     * @return
     */
    public Observable<DetailResponse<BaseModel>> sendSMSCode(User data){
        return service.sendSMSCode(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 发送邮箱验证码
     * @param data
     * @return
     */
    public Observable<DetailResponse<BaseModel>> sendEmailCode(User data){
        return service.sendEmailCode(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 用户详情接口
     * @param id
     * @param nickname
     * @return
     */
    public Observable<DetailResponse<User>> userDetail(String id,String nickname){
        //添加查询参数
        HashMap<String,String> data=new HashMap<>();

        if (StringUtils.isNotBlank(nickname)){
            //如果昵称不为空才添加
            data.put(Constant.NICKNAME,nickname);
        }

        return service.userDetail(id,data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 用户详情
     * @param id
     * @return
     */
    public Observable<DetailResponse<User>> userDetail(String id){
        return userDetail(id,null);
    }
}
