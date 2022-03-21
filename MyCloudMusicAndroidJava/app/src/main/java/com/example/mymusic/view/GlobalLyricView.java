package com.example.mymusic.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.mymusic.R;

/**
 * 全局（桌面）歌词
 */
public class GlobalLyricView extends LinearLayout {
    public GlobalLyricView(Context context) {
        super(context);

        init();
    }

    public GlobalLyricView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GlobalLyricView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GlobalLyricView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * 初始化方法
     */
    private void init() {

        initViews();
    }

    /**
     * 初始化view
     */
    private void initViews() {
        //设置背景
        setBackground();

        //从xml加载view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_global_lyric, this, false);

        //添加到当前容器
        addView(view);
    }

    /**
     * 设置背景
     */
    private void setBackground() {
        setBackgroundColor(getResources().getColor(R.color.global_lyric_bakcground));
    }
}
