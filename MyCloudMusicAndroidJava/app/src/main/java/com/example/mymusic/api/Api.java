package com.example.mymusic.api;

import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.example.mymusic.AppContext;
import com.example.mymusic.domain.Ad;
import com.example.mymusic.domain.BaseModel;
import com.example.mymusic.domain.Session;
import com.example.mymusic.domain.Sheet;
import com.example.mymusic.domain.Song;
import com.example.mymusic.domain.User;
import com.example.mymusic.domain.response.BaseResponse;
import com.example.mymusic.domain.response.DetailResponse;
import com.example.mymusic.domain.response.ListResponse;
import com.example.mymusic.util.Constant;
import com.example.mymusic.util.LogUtil;
import com.example.mymusic.util.PreferenceUtil;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.apache.commons.lang3.StringUtils;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;

/**
 * 网络请求接口包装类
 * 对外部提供一个和框架无关的接口
 */
public class Api {
    private static final String TAG = "Api";
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

        //公共请求参数
        OkHttpClientBuilder.addNetworkInterceptor(chain -> {
            //获取到偏好设置工具类
            PreferenceUtil sp = PreferenceUtil.getInstance(AppContext.getInstance());

            //获取到request
            Request request = chain.request();

            if (sp.isLogin()) {
                //登录了

                //获取出用户Id和token
                String user = sp.getUserId();
                String session = sp.getSession();

                //打印日志是方便调试
                LogUtil.d(TAG, "Api user:" + user + "," + session);

                //将用户id和token设置到请求头
                request.newBuilder()
                        .addHeader("User", user)
                        .addHeader("Authorization", session)
                        .build();
            }

            //继续执行网络请求
            return chain.proceed(request);
        });

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

    /**
     * 单曲
     * @return
     */
    public Observable<ListResponse<Song>> songs(){
        return service.songs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 广告列表
     * @return
     */
    public Observable<ListResponse<Ad>> ads(){
        return service.ads()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 收藏歌单
     * @param id
     * @return
     */
    public Observable<Response<Void>> collect(String id){
        return service.collect(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    /**
     * 取消收藏歌单
     * @param id
     * @return
     */
    public Observable<Response<Void>> deleteCollect(String id){
        return service.deleteCollect(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
