package com.example.mymusic.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mymusic.R;
import com.example.mymusic.domain.Lyric.Line;

/**
 * 播放界面-歌词列表适配器
 */
public class LyricAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {
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

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Object item) {
        //使用TextView实现
        if (item instanceof String) {
            //字符串
            //用来填充占位符
            helper.setText(R.id.tv,"");
        } else {
            //真实数据
            Line data= (Line) item;

            //显示歌词
            helper.setText(R.id.tv,data.getData());
        }

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
