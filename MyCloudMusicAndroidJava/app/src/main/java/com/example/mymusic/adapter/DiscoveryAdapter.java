package com.example.mymusic.adapter;

import static com.example.mymusic.util.Constant.TYPE_SHEET;
import static com.example.mymusic.util.Constant.TYPE_SONG;
import static com.example.mymusic.util.Constant.TYPE_TITLE;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mymusic.R;
import com.example.mymusic.domain.BaseMultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现界面适配器
 */
public class DiscoveryAdapter extends BaseMultiItemQuickAdapter<BaseMultiItemEntity, BaseViewHolder> {

    /**
     * 构造方法
     */
    public DiscoveryAdapter() {
        //第一次他要传入数据
        //而这时候我们还没有准备好数据
        //所以传递一个空列表
        super(new ArrayList<>());

        //添加多类型布局

        //添加标题类型
        addItemType(TYPE_TITLE, R.layout.item_title);

        //添加歌单类型
        addItemType(TYPE_SHEET, R.layout.item_sheet);

        //添加单曲类型
        addItemType(TYPE_SONG, R.layout.item_song);
    }

    /**
     * 绑定数据方法
     * @param helper
     * @param item
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, BaseMultiItemEntity item) {

    }
}
