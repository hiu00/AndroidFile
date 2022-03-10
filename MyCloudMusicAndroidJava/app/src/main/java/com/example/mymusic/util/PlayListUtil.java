package com.example.mymusic.util;

import static com.example.mymusic.util.Constant.MODEL_LOOP_LIST;
import static com.example.mymusic.util.Constant.MODEL_LOOP_RANDOM;

import android.widget.Button;
import android.widget.TextView;

import com.example.mymusic.R;
import com.example.mymusic.listener.ListManager;
import com.example.mymusic.manager.impl.ListManagerImpl;

import butterknife.BindView;

/**
 * 播放列表工具类
 */
public class PlayListUtil {
    /**
     * 显示循环模式
     * @param listManager
     * @param textView
     */
    public static void showLoopModel(ListManager listManager, TextView textView) {
        //获取当前循环模式
        int model = listManager.getLoopModel();

        //根据不同循环模式
        //显示不同的提示
        switch (model){
            case MODEL_LOOP_LIST:
                textView.setText("列表循环");
                break;
            case MODEL_LOOP_RANDOM:
                textView.setText("随机模式");
                break;
            default:
                textView.setText("单曲循环");
                break;
        }
    }
}

