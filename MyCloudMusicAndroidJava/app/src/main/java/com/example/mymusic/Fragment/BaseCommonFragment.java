package com.example.mymusic.Fragment;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.mymusic.activity.BaseCommonActivity;
import com.example.mymusic.util.Constant;
import com.example.mymusic.util.ORMUtil;
import com.example.mymusic.util.PreferenceUtil;

import butterknife.ButterKnife;

/**
 * 通用公共Fragment
 */
public abstract class BaseCommonFragment extends BaseFragment{

    private PreferenceUtil sp;

    /**
     * 数据库对象
     */
    protected ORMUtil orm;

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

        //初始化数据库对象
        orm = ORMUtil.getInstance(getMainActivity());
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
     * 启动界面，可以传递一个字符串参数
     * @param clazz
     * @param id
     */
    protected void startActivityExtraId(Class<?> clazz,String id){
        //创建Intent
        Intent intent = new Intent(getMainActivity(), clazz);

        //传递数据
        if (!TextUtils.isEmpty(id)) {
            //不为空才传递
            intent.putExtra(Constant.ID, id);
        }

        //启动界面
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
