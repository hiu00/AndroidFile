package com.example.mymusic.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mymusic.R;
import com.example.mymusic.domain.Song;
import com.example.mymusic.util.Constant;

/**
 * 音乐黑胶唱片界面
 */
public class MusicRecordFragment extends BaseCommonFragment{
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
}
