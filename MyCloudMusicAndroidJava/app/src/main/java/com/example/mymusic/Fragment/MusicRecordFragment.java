package com.example.mymusic.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mymusic.R;
import com.example.mymusic.domain.Song;
import com.example.mymusic.domain.event.OnStartRecordEvent;
import com.example.mymusic.domain.event.OnStopRecordEvent;
import com.example.mymusic.util.Constant;
import com.example.mymusic.util.ImageUtil;
import com.example.mymusic.util.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 音乐黑胶唱片界面
 */
public class MusicRecordFragment extends BaseCommonFragment{

    private static final String TAG = "MusicRecordFragment";

    /**
     * 16毫秒
     * 每秒60帧计算出来的
     */
    private static final float ROTATION_PER = 0.2304F;
    /**
     * 黑胶唱片容器
     */
    @BindView(R.id.cl_content)
    ConstraintLayout cl_content;

    /**
     * 歌曲封面
     */
    @BindView(R.id.iv_banner)
    CircleImageView iv_banner;
    private Song data;
    private TimerTask timerTask;
    /**
     * 旋转角度
     */
    private float recordRotation;
    private Timer timer;

    /**
     * 返回要显示的布局
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    protected View getLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_record_music,container,false);
    }

    /**
     * 创建方法
     * @param data
     * @return
     */
    public static MusicRecordFragment newInstance(Song data) {

        Bundle args = new Bundle();

        //传递数据
        args.putSerializable(Constant.DATA,data);

        MusicRecordFragment fragment = new MusicRecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        if (!EventBus.getDefault().isRegistered(this)){
            //注册发布订阅框架
            EventBus.getDefault().register(this);
        }

        //获取传递的数据
        data = (Song) extraData();

        //显示封面
        ImageUtil.show(getMainActivity(),iv_banner,data.getBanner());
    }

    /**
     * 界面销毁时
     */
    @Override
    public void onDestroy() {

        if (EventBus.getDefault().isRegistered(this)){
            //取消注册
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    /**
     * 黑胶唱片开始滚动事件
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartRecordEvent(OnStartRecordEvent event){
        //通过事件传递当前音乐
        //如果当前音乐匹配
        //当前Fragment就是操作当前fragment
        if (event.getData()==data){
            LogUtil.d(TAG,"onStartRecordEvent:"+data.getTitle());

            startRecordRotate();
        }
    }

    /**
     * 黑胶唱片停止事件
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStopRecordEvent(OnStopRecordEvent event) {
        if (event.getData() == data) {
            LogUtil.d(TAG, "onStopRecordEvent:" + event.getData().getTitle());

            stopRecordRotate();
        }
    }

    /**
     * 开始旋转
     */
    private void startRecordRotate() {
        if (timerTask!=null){
            //已经启动了
            return;
        }
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //如果旋转的角度大于等于360
                if (recordRotation>=360){
                    //就设置为0
                    recordRotation=0;
                }

                //每次加旋转的偏移
                recordRotation+=ROTATION_PER;

                //旋转
                cl_content.setRotation(recordRotation);
            }
        };

        //创建定时器
        timer = new Timer();

        //启动任务
        timer.schedule(timerTask,0,16);
    }

    /**
     * 停止旋转
     */
    private void stopRecordRotate() {
        //停止定时器任务
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

        //停止定时器
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}

