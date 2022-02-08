package com.example.mymusic.domain;

import static com.example.mymusic.util.Constant.TYPE_SHEET;

/**
 * 歌单详情
 */
public class Sheet extends BaseMultiItemEntity{

    /**
     * 标题
     */
    private String title;

    /**
     * 封面
     */
    private String banner;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    @Override
    public int getItemType() {
        return TYPE_SHEET;
    }

    /**
     * 一行占多少列
     *
     * @return
     */
    @Override
    public int getSpanSize() {
        return 1;
    }
}
