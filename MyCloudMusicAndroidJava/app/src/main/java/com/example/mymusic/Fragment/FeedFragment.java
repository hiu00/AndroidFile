package com.example.mymusic.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mymusic.R;

/**
 * 首页-朋友 界面
 */
public class FeedFragment extends BaseCommonFragment{

    /**
     * 构造方法
     * 固定写法
     * @return
     */
    public static FeedFragment newInstance() {

        Bundle args = new Bundle();

        FeedFragment fragment = new FeedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 返回布局文件
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    protected View getLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed,null);
    }
}
