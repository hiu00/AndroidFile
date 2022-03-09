package com.example.mymusic.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mymusic.R;
import com.example.mymusic.domain.Song;
import com.example.mymusic.listener.ListManager;

/**
 * 迷你控制器-播放列表adapter
 */
public class PlayListAdapter extends BaseQuickAdapter<Song, BaseViewHolder> {
    /**
     * 列表管理器
     */
    private final ListManager listManager;

    /**
     * 构造方法
     * 列表管理器
     * @param layoutResId
     * @param listManager
     */
    public PlayListAdapter(int layoutResId, ListManager listManager) {
        super(layoutResId);
        this.listManager=listManager;
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

        //处理选中状态
        if (data.getId().equals(listManager.getData().getId())) {
            //选中

            //颜色设置为主色调
            helper.setTextColor(R.id.tv_title, mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            //未选中

            //颜色设置为黑色
            helper.setTextColor(R.id.tv_title, mContext.getResources().getColor(R.color.text));
        }
    }
}
