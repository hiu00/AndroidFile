package com.example.mymusic.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mymusic.R;
import com.example.mymusic.domain.Lyric.Line;

/**
 * 播放界面-歌词列表适配器
 */
public class LyricAdapter extends BaseQuickAdapter<Line, BaseViewHolder> {
    /**
     * 选中索引
     */
    private int selectedIndex;

    public void setSelectedIndex(int selectedIndex) {
        notifyItemChanged(this.selectedIndex);

        this.selectedIndex = selectedIndex;

        notifyItemChanged(this.selectedIndex);
    }

    /**
     * 构造方法
     * @param layoutResId
     */
    public LyricAdapter(int layoutResId) {
        super(layoutResId);
    }

    /**
     * 绑定数据
     * @param helper
     * @param item
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, Line item) {
        //使用TextView实现
        //显示歌词
        helper.setText(R.id.tv,item.getData());

        //处理选中状态
        if (selectedIndex==helper.getAdapterPosition()){
            //选中行
            helper.setTextColor(R.id.tv,mContext.getResources().getColor(R.color.colorPrimary));
        }else {
            //未选中
            helper.setTextColor(R.id.tv,mContext.getResources().getColor(R.color.lyric_text_color));
        }
    }
}
