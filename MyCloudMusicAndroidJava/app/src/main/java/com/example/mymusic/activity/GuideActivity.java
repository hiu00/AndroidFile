package com.example.mymusic.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.mymusic.Fragment.GuideFragment;
import com.example.mymusic.MainActivity;
import com.example.mymusic.R;
import com.example.mymusic.adapter.GuideAdapter;
import com.example.mymusic.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * 引导界面
 */
public class GuideActivity extends BaseCommonActivity implements View.OnClickListener {

    private static final String TAG = "GuideActivity";
    private Button bt_login_or_register;
    private Button bt_enter;
    private ViewPager vp;
    private GuideAdapter adapter;
    private CircleIndicator ci;

    /**
     *当前界面创建的时候
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //关联布局
        setContentView(R.layout.activity_guide);

    }

    @Override
    protected void initViews() {
        super.initViews();

        //隐藏状态栏
        hideStatusBar();

        //找控件
        //找指示器
        ci = findViewById(R.id.ci);

        //找ViewPager控件
        vp=findViewById(R.id.vp);
        //找登陆注册按钮
        bt_login_or_register = findViewById(R.id.bt_login_or_register);
        //找立即体验按钮
        bt_enter = findViewById(R.id.bt_enter);

        //测试显示Fragment
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.container,GuideFragment.newInstance(R.drawable.guide1))
//                .commit();
    }

    @Override
    protected void initDatum() {
        super.initDatum();

        //创建适配器
        adapter = new GuideAdapter(getMainActivity(),getSupportFragmentManager());

        //设置适配器到控件
        vp.setAdapter(adapter);

        //让指示器根据列表控件配合工作
        ci.setViewPager(vp);

        //适配器注册数据源观察者
        adapter.registerDataSetObserver(ci.getDataSetObserver());

        //准备数据
        List<Integer> datum=new ArrayList<>();
        datum.add(R.drawable.guide1);
        datum.add(R.drawable.guide2);
        datum.add(R.drawable.guide3);
        datum.add(R.drawable.guide4);
        datum.add(R.drawable.guide5);

        //设置数据到适配器
        adapter.setDatum(datum);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        //设置点击事件
        //登陆注册按钮
        bt_login_or_register.setOnClickListener(this);

        //立即体验按钮
        bt_enter.setOnClickListener(this);
    }

    /**
     * 点击回调事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        //因为有很多个按钮都设置同一个监听器
        //所以这里需要区分到底是哪一个按钮点击
        switch (view.getId()){
            case R.id.bt_login_or_register:
                //点击登陆注册按钮
                Log.d(TAG,"onClick login or register");
                startActivoityAfterFinishThis(LoginOrRegisterActivity.class);
                setShowGuide();
                break;
            case R.id.bt_enter:
                //点击立即体验按钮
                Log.d(TAG,"onClick enter");
                startActivoityAfterFinishThis(MainActivity.class);
                setShowGuide();
                break;
        }

    }

    /**
     * 设置不在显示了引导界面
     */
    private void setShowGuide() {
        //PreferenceUtil.getInstance(getMainActivity()).setShowGuide(false);
        sp.setShowGuide(false);
    }
}
