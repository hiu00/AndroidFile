package com.example.mymusic.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mymusic.activity.BaseCommonActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.ButterKnife;

/**
 * 所有BottomSheetDialogFragment通用父类
 */
public abstract class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment {
    /**
     * 找控件
     */
    protected void initViews() {

    }

    /**
     * 设置数据
     */
    protected void initDatum() {
    }

    /**
     * 绑定监听器
     */
    protected void initListeners() {
    }

    /**
     * view创建了
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initDatum();
        initListeners();
    }

    /**
     * 返回要显示的控件
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取view
        View view = getLayoutView(inflater,container,savedInstanceState);

        //初始化注解找控件
        ButterKnife.bind(this,view);

        return view;
    }

    /**
     * 返回要显示的View
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected abstract View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 获取activity
     * @return
     */
    protected BaseCommonActivity getMainActivity(){
        return (BaseCommonActivity) getActivity();
    }
}
