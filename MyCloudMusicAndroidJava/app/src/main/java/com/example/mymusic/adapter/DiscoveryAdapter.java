package com.example.mymusic.adapter;

import static com.example.mymusic.util.Constant.TYPE_SHEET;
import static com.example.mymusic.util.Constant.TYPE_SONG;
import static com.example.mymusic.util.Constant.TYPE_TITLE;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mymusic.R;
import com.example.mymusic.domain.BaseMultiItemEntity;
import com.example.mymusic.domain.Sheet;
import com.example.mymusic.domain.Song;
import com.example.mymusic.domain.Title;
import com.example.mymusic.util.ImageUtil;

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
        switch (helper.getItemViewType()) {
            case TYPE_TITLE:
                //标题
                Title title = (Title) item;

                helper.setText(R.id.tv_title, title.getTitle());
                break;
            case TYPE_SHEET:
                //歌单
                Sheet sheet = (Sheet) item;

                //显示图片
                ImageUtil.show((Activity) mContext, helper.getView(R.id.iv_banner), sheet.getBanner());

                //设置歌单标题
                helper.setText(R.id.tv_title, sheet.getTitle());

                //播放量
                helper.setText(R.id.tv_info, String.valueOf(sheet.getClicks_count()));

                break;
            case TYPE_SONG:
                //单曲
                Song song = (Song) item;

                //显示封面
                ImageUtil.show((Activity) mContext, helper.getView(R.id.iv_banner), song.getBanner());

                //设置标题
                helper.setText(R.id.tv_title, song.getTitle());

                //播放量
                helper.setText(R.id.tv_info, String.valueOf(song.getClicks_count()));

                //歌手头像
                ImageUtil.showAvatar((Activity) mContext, helper.getView(R.id.iv_avatar), song.getSinger().getAvatar());

                //歌手昵称
                helper.setText(R.id.tv_nickname, song.getSinger().getNickname());

                break;
        }
    }
}
