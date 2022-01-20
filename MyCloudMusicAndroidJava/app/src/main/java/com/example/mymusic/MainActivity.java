package com.example.mymusic;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mymusic.R;
import com.example.mymusic.activity.BaseCommonActivity;
import com.example.mymusic.activity.BaseTitleActivity;
import com.example.mymusic.activity.WebViewActivity;
import com.example.mymusic.api.Api;
import com.example.mymusic.domain.User;
import com.example.mymusic.domain.response.DetailResponse;
import com.example.mymusic.listener.HttpObserver;
import com.example.mymusic.util.Constant;
import com.example.mymusic.util.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseTitleActivity {

    /**
     * 找侧滑控件
     */
    @BindView(R.id.dl)
    DrawerLayout dl;

    /**
     * 头像
     */
    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;

    /**
     * 昵称
     */
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;

    /**
     * 描述
     */
    @BindView(R.id.tv_description)
    TextView tv_description;

    /**
     * 用户容器点击
     */
    @OnClick(R.id.ll_user)
    public void onUserClick() {
        LogUtil.d(TAG,"onUserClick");
    }

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.d(TAG,"onCreate");

        //处理动作
        processIntent(getIntent());
    }

    @Override
    protected void initViews() {
        super.initViews();

        //侧滑配置
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //侧滑监听器
        dl.addDrawerListener(toggle);

        //同步状态
        toggle.syncState();
    }

    @Override
    protected void initDatum() {
        super.initDatum();

        //获取用户信息
        //当然可以在用户要显示侧滑的时候
        //才获取用户信息
        //这样可以减少请求
        fetchData();
    }

    /**
     * 请求数据
     */
    private void fetchData() {
        Api.getInstance().userDetail(sp.getUserId())
                .subscribe(new HttpObserver<DetailResponse<User>>() {
                    @Override
                    public void onSucceeded(DetailResponse<User> data) {
                        next(data.getData());
                    }
                });
    }

    /**
     * 显示数据
     * @param data
     */
    private void next(User data) {
        //显示头像

        if (TextUtils.isEmpty(data.getAvatar())){
            //没有头像

            //显示默认头像
            //iv_avatar.setImageResource(R.drawable.placeholder);

            Glide
                    .with(this)
                    .load(R.drawable.placeholder)
                    .into(iv_avatar);
        }else {
            //有头像

            if (data.getAvatar().startsWith("http")){
                //绝对路径
                Glide
                        .with(this)
                        .load(data.getAvatar())
                        .into(iv_avatar);
            }else {
                //相对路径(只有图片的名称)

                //将图片地址转为绝对地址
                String uri=String.format(Constant.RESOURCE_ENDPOINT,data.getAvatar());

                //显示图片
                Glide
                        .with(this)
                        .load(uri)
                        .into(iv_avatar);
            }
        }

        //显示昵称
        tv_nickname.setText(data.getNickname());

        //显示描述
        tv_description.setText(data.getDescriptionFormat());
    }
    /**
     * 处理动作
     *
     * @param intent
     */
    private void processIntent(Intent intent) {
        if (Constant.ACTION_AD.equals(intent.getAction())){
            //广告点击

            //显示广告界面
            WebViewActivity.start(getMainActivity(),"活动详情",intent.getStringExtra(Constant.URL));
        }
    }

    /**
     * 界面已经显示了
     * 不需要再次创建新界面的时候调用
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.d(TAG, "onNewIntent");

        //处理动作
        processIntent(intent);
    }
}