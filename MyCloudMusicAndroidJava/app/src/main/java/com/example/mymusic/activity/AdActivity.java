package com.example.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;

import com.example.mymusic.MainActivity;
import com.example.mymusic.R;
import com.example.mymusic.util.Constant;
import com.example.mymusic.util.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 广告界面
 */
public class AdActivity extends BaseCommonActivity {

    private static final String TAG = "AdActivity";
    /**
     * 跳过广告按钮
     */
    @BindView(R.id.bt_skip_ad)
    Button bt_skip_ad;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //全屏
        fullScreen();
    }

    /**
     * 界面以及显示出来了调用
     * 从后台切换到前台还会调用
     */
    @Override
    protected void onResume() {
        super.onResume();

        //倒计时的总时间,间隔
        //单位为毫秒
        countDownTimer = new CountDownTimer(5000, 1000) {
            /**
             * 每次间隔调用
             * @param l
             */
            @Override
            public void onTick(long l) {
                bt_skip_ad.setText(getString(R.string.count_second,l/1000));

            }

            /**
             * 倒计时完成
             */
            @Override
            public void onFinish() {
                //倒计时完成
                //执行下一步
                next();
            }
        };

        //启动定时器
        countDownTimer.start();
    }

    /**
     * 跳转到主页面
     */
    private void next() {
        //startActivoityAfterFinishThis(MainActivity.class);
        //显示网页
        WebViewActivity.start(getMainActivity(),"活动详情","http://www.ixuea.com");
    }

    /**
     * 广告点击了
     */
    @OnClick(R.id.bt_ad)
    public void onAdClick() {
        //取消倒计时
        cancelCountDown();

        //创建意图
        Intent intent = new Intent(getMainActivity(), MainActivity.class);

        //添加广告地址
        intent.putExtra(Constant.URL,"http://www.ixuea.com");

        //要跳转到广告界面
        //先启动主界面的
        //好处是
        //用户在广告界面
        //返回正好看到的主界面
        //这样才符合应用逻辑
        intent.setAction(Constant.ACTION_AD);

        //启动界面
        startActivity(intent);

        //关闭当前界面
        finish();
    }

    /**
     * 跳过广告按钮
     */
    @OnClick(R.id.bt_skip_ad)
    public void onSkipAd() {
        LogUtil.d(TAG, "onSkipAd");

        //取消倒计时
        cancelCountDown();

        //跳转到首页
        next();
    }

    /**
     * 销毁定时器
     */
    @Override
    protected void onDestroy() {
        cancelCountDown();

        super.onDestroy();
    }

    /**
     * 取消倒计时
     */
    private void cancelCountDown() {
        if (countDownTimer!=null){
            countDownTimer.cancel();
        }
    }
}