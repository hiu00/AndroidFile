package com.example.mymusic.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mymusic.R;
import com.example.mymusic.domain.Song;

/**
 * 迷你控制器-播放列表adapter
 */
public class PlayListAdapter extends BaseQuickAdapter<Song, BaseViewHolder> {
    /**
     * 构造方法
     * 列表管理器
     * @param layoutResId
     */
    public PlayListAdapter(int layoutResId) {
        super(layoutResId);
    }

    /**
     * 显示数据
     * @param helper
     * @param data
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, Song data) {
        //显示标题
        helper.setText(R.id.tv_title,String.format("%s - %s",data.getTitle(),data.getSinger().getNickname()));
    }
}
