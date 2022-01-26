package com.example.mymusic.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.mymusic.activity.BaseCommonActivity;
import com.example.mymusic.util.PreferenceUtil;

import butterknife.ButterKnife;

/**
 * 通用公共Fragment
 */
public abstract class BaseCommonFragment extends BaseFragment{

    private PreferenceUtil sp;

    @Override
    protected void initViews() {
        super.initViews();

        //初始化注解找控件
        //绑定方法框架
        if (isBindView()){
            bindView();
        }

    }

    /**
     * 绑定view
     */
    protected void bindView() {
        ButterKnife.bind(this,getView());
    }

    /**
     * 是否绑定view
     * @return
     */
    private boolean isBindView() {
        return true;
    }

    @Override
    protected void initDatum() {
        super.initDatum();

        //初始化偏好工具类
        sp = PreferenceUtil.getInstance(getMainActivity());
    }

    /**
     * 获取当前Fragment所在的Activity
     *
     * @return
     */
    public BaseCommonActivity getMainActivity() {
        return (BaseCommonActivity) getActivity();
    }

    /**
     * 启动界面
     *
     * @param clazz
     */
    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * 启动界面并关闭当前界面
     *
     * @param clazz
     */
    protected void startActivityAfterFinishThis(Class<?> clazz) {
        startActivity(new Intent(getMainActivity(), clazz));
        getActivity().finish();
    }


}
