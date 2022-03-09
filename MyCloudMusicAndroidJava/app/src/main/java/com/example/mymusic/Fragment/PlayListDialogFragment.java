package com.example.mymusic.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.mymusic.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * 迷你控制器 播放列表(即更多按钮)
 */
public class PlayListDialogFragment extends BottomSheetDialogFragment {
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
        return inflater.inflate(R.layout.fragment_dialog_play_list,null);
    }

    /**
     * View创建了
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 构造方法
     * @return
     */
    public static PlayListDialogFragment newInstance() {
        
        Bundle args = new Bundle();
        
        PlayListDialogFragment fragment = new PlayListDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 显示对话框
     * @param fragmentManager
     */
    public static void show(FragmentManager fragmentManager){
        //创建fragment
        PlayListDialogFragment fragment = newInstance();

        fragment.show(fragmentManager,"song_play_list_dialog");
    }
}
