package com.example.mymusic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mymusic.AppContext;
import com.example.mymusic.MainActivity;
import com.example.mymusic.R;
import com.example.mymusic.api.Api;
import com.example.mymusic.api.Service;
import com.example.mymusic.domain.Session;
import com.example.mymusic.domain.Sheet;
import com.example.mymusic.domain.SheetDetailWrapper;
import com.example.mymusic.domain.SheetListWrapper;
import com.example.mymusic.domain.User;
import com.example.mymusic.domain.response.DetailResponse;
import com.example.mymusic.domain.response.ListResponse;
import com.example.mymusic.listener.HttpObserver;
import com.example.mymusic.listener.ObserverAdapter;
import com.example.mymusic.util.Constant;
import com.example.mymusic.util.LoadingUtil;
import com.example.mymusic.util.LogUtil;
import com.example.mymusic.util.StringUtil;
import com.example.mymusic.util.ToastUtil;

import org.apache.commons.lang3.StringUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends BaseLoginActivity {
    private static final String TAG = "LoginActivity";
    /**
     * 用户名输入框
     */
    @BindView(R.id.et_username)
    EditText et_username;

    /**
     * 密码输入框
     */
    @BindView(R.id.et_password)
    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * 登录按钮点击了
     */
    @OnClick(R.id.bt_login)
    public void onLoginClick(Button view) {
        LogUtil.d(TAG, "onLoginClick");

        //请求歌单详情
        //用来测试网络请求框架

//        //初始化OkHttp
//        OkHttpClient.Builder OkHttpClientBuilder=new OkHttpClient.Builder();
//
//        //创建一个Retrofit
//        Retrofit retrofit=new Retrofit.Builder()
//                //让Retrofit使用OkHttp请求网络
//                .client(OkHttpClientBuilder.build())
//
//                //这里就是配置API地址
//                .baseUrl(Constant.ENDPOINT)
//
//                //适配RxJava
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//
//                //自动JSON转换
//                //包括请求参数和相应
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        //创建一个service
//        Service service = retrofit.create(Service.class);

//        //请求歌单详情
//        service.sheetDetail("1")
//                //在什么线程里去执行这个任务（子线程）
//                .subscribeOn(Schedulers.io())
//
//                //在哪里去观察（主线程）
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<SheetDetailWrapper>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull SheetDetailWrapper sheetDetailWrapper) {
//                        //请求成功
//                        LogUtil.d(TAG,"request sheet detail success:"+sheetDetailWrapper.getData().getTitle());
//
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        //请求失败
//                        LogUtil.d(TAG,"request sheet detail failed:"+e.getLocalizedMessage());
//
//                        //判断错误类型
//                        if (e instanceof UnknownHostException){
//                            ToastUtil.errorShortToast(R.string.error_network_unknow_host);
//                        }else if (e instanceof ConnectException){
//                            ToastUtil.errorShortToast(R.string.error_network_connect);
//                        }else if (e instanceof SocketTimeoutException){
//                            ToastUtil.errorShortToast(R.string.error_network_timeout);
//                        }else if (e instanceof HttpException){
//                            HttpException exception= (HttpException) e;
//
//                            //获取响应码
//                            int code=exception.code();
//                            if (code==401){
//                                ToastUtil.errorShortToast(R.string.error_network_not_auth);
//                            }else if (code == 403) {
//                                ToastUtil.errorShortToast(R.string.error_network_not_permission);
//                            } else if (code == 404) {
//                                ToastUtil.errorShortToast(R.string.error_network_not_found);
//                            } else if (code >= 500) {
//                                ToastUtil.errorShortToast(R.string.error_network_server);
//                            } else {
//                                ToastUtil.errorShortToast(R.string.error_network_unknown);
//                            }
//                        } else{
//                            ToastUtil.errorShortToast(R.string.error_network_unknown);
//                        }
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

//        //使用重构后的Api
//        Api.getInstance()
//                .sheetDetail("1")
//                .subscribe(new Observer<SheetDetailWrapper>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//                //显示对话框
//                LoadingUtil.showLoading(getMainActivity());
//            }
//
//            @Override
//            public void onNext(@NonNull SheetDetailWrapper sheetDetailWrapper) {
//                //请求成功
//                LogUtil.d(TAG,"request sheet detail success:"+sheetDetailWrapper.getData().getTitle());
//
//                //隐藏对话框
//                LoadingUtil.hideLoading();
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                //隐藏对话框
//                LoadingUtil.hideLoading();
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//
//        //测试加载提示框
//        LoadingUtil.showLoading(getMainActivity());

        //请求歌单列表
//        Api.getInstance().sheets()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<SheetListWrapper>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull SheetListWrapper sheetListWrapper) {
//                        LogUtil.d(TAG,"onNext:"+sheetListWrapper.getData().size());
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

//        //使用DetailResponse
//        Api.getInstance().sheetDetail("1")
//                .subscribe(new Observer<DetailResponse<Sheet>>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull DetailResponse<Sheet> sheetDetailResponse) {
//                        LogUtil.d(TAG,""+sheetDetailResponse.getData().getTitle());
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

        //使用ListResponse
//        Api.getInstance().sheets()
//                .subscribe(new Observer<ListResponse<Sheet>>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull ListResponse<Sheet> sheetListResponse) {
//                        LogUtil.d(TAG,"onNext:"+sheetListResponse.getData().size());
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

//        //使用ObserverAdapter
//        Api.getInstance().sheetDetail("1")
//                .subscribe(new ObserverAdapter<DetailResponse<Sheet>>(){
//                    @Override
//                    public void onNext(@NonNull DetailResponse<Sheet> sheetDetailResponse) {
//                        super.onNext(sheetDetailResponse);
//                        LogUtil.d(TAG,"onNext:"+sheetDetailResponse.getData().getTitle());
//                    }
//                });
//
//        //使用HttpObserver
//        Api.getInstance()
//                .sheetDetail("1")
//                .subscribe(new HttpObserver<DetailResponse<Sheet>>() {
//                    @Override
//                    public void onSuccessed(DetailResponse<Sheet> data) {
//                        LogUtil.d(TAG,"onSuccessed:"+data.getData().getTitle());
//                    }
//                });

        //3秒后自动隐藏
        //因为显示后无法点击后面的按钮
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                LoadingUtil.hideLoading();
//            }
//        },3000);

        //测试自动显示加载对话框
//        Api.getInstance().sheetDetail("1")
//                .subscribe(new HttpObserver<DetailResponse<Sheet>>(getMainActivity(),false) {
//                    @Override
//                    public void onSucceeded(DetailResponse<Sheet> data) {
//                        LogUtil.d(TAG, "onNext:" + data.getData().getTitle());
//                    }
//                });

        //获取用户名
        String username = et_username.getText().toString().trim();
        if (StringUtils.isBlank(username)){
            LogUtil.w(TAG,"onLoginClick user empty");
            //Toast.makeText(getMainActivity(), R.string.enter_username, Toast.LENGTH_SHORT).show();
            //Toasty.error(getMainActivity(),R.string.enter_username,Toasty.LENGTH_SHORT).show();

            ToastUtil.errorShortToast(R.string.enter_username);
            return;
        }

        //判断用户名格式

        //如果用户名
        //不是手机号也不是邮箱
        //就是格式错误
        if (!(StringUtil.isPhone(username)||StringUtil.isEmail(username))){
            ToastUtil.errorShortToast(R.string.error_username_format);
            return;
        }

        //获取密码
        String password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)){
            LogUtil.w(TAG,"onLoginClick password empty");
            //Toast.makeText(getMainActivity(), R.string.enter_password, Toast.LENGTH_SHORT).show();

            ToastUtil.errorLongToast(R.string.enter_password);
            return;
        }

        //判断密码格式
        if(!(StringUtil.isPassword(password))){
            ToastUtil.errorShortToast(R.string.error_password_format);
            return;
        }

        //判断是手机号还是邮箱
        String phone = null;
        String email = null;

        if(StringUtil.isPhone(username)){
            //手机号
            phone=username;
        }else {
            //邮箱
            email=username;
        }

//        User user = new User();
//
//        //这里虽然同时传递了手机号和邮箱
//        //但服务端登录的时候有先后顺序
//        user.setPhone(phone);
//        user.setEmail(email);
//        user.setPassword(password);
//
//        //调用登录接口
//        Api.getInstance().login(user)
//                .subscribe(new HttpObserver<DetailResponse<Session>>() {
//                    @Override
//                    public void onSucceeded(DetailResponse<Session> data) {
//                       LogUtil.d(TAG,"onLoginClick success:"+data.getData());
//
//                        //把登录成功的事件通知到AppContext
//                        AppContext.getInstance().login(sp,data.getData());
//
//                        ToastUtil.successLongToast(R.string.login_sucess);
//
//                        //关闭当前界面并启动主界面
//                        startActivoityAfterFinishThis(MainActivity.class);
//                    }
//                });

        //调用父类的登录方法
        login(phone,email,password);
    }

    /**
     * 忘记密码按钮点击了
     */
    @OnClick(R.id.bt_forget_password)
    public void onForgetPasswordClick() {
        LogUtil.d(TAG, "onForgetPasswordClick");

        //跳转到找回密码界面
        startActivity(ForgetPasswordActivity.class);
    }
}