package com.example.mymusic;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mymusic.R;
import com.example.mymusic.activity.BaseCommonActivity;
import com.example.mymusic.activity.BaseTitleActivity;
import com.example.mymusic.activity.WebViewActivity;
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