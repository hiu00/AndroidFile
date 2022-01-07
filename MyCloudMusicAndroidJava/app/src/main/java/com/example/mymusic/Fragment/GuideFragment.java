package com.example.mymusic.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mymusic.R;
import com.example.mymusic.util.Constant;

/**
 * 引导界面Fragment
 */
public class GuideFragment extends BaseCommonFragment {

    private ImageView iv;

    /**
     * 构造方法
     */
    public GuideFragment() {
    }

    /**
     * 创建方法
     */
    public static GuideFragment newInstance(int id) {
        //创建fragment
        GuideFragment fragment = new GuideFragment();

        //创建Bundle方法
        Bundle args = new Bundle();

        //添加ID
        args.putInt(Constant.ID,id);

        //将Bundle设置到fragment
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * 创建
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    /**
     * 返回要显示的View
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    protected View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    protected void initViews() {
        super.initViews();
        //找控件
        iv = findViewById(R.id.iv);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        //取出传递的数据
        int id = getArguments().getInt(Constant.ID, -1);

        if(id==-1){
            //如果图片不存在
            //就关闭当前界面
            //但在我们这里不会发生该情况
            getActivity().finish();
            return;
        }

        //显示图片
        iv.setImageResource(id);
    }

}