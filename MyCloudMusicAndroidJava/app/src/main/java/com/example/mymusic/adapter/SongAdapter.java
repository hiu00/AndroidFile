package com.example.mymusic.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mymusic.R;
import com.example.mymusic.domain.Song;

/**
 * 歌单详情-歌曲适配器
 */
public class SongAdapter extends BaseQuickAdapter<Song, BaseViewHolder> {
    /**
     * 构造方法
     * @param layoutResId 布局Id
     */
    public SongAdapter(int layoutResId) {
        super(layoutResId);
    }

    /**
     * 显示数据
     * @param helper
     * @param data
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, Song data) {
        //显示位置
        helper.setText(R.id.tv_position,String.valueOf(helper.getAdapterPosition()));

        //显示标题
        helper.setText(R.id.tv_title,data.getTitle());

        //显示歌手信息
        helper.setText(R.id.tv_info,data.getSinger().getNickname());
    }
}
