package com.example.mymusic.domain;

import static com.example.mymusic.util.Constant.TYPE_SHEET;

import java.util.List;

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

    /**
     * 描述
     */
    private String description;

    /**
     * 点击数
     */
    private int clicks_count;

    /**
     * 收藏数
     */
    private int collections_count;

    /**
     * 评论数
     */
    private int comments_count;

    /**
     * 音乐数
     */
    private int songs_count;

    /**
     * 歌单创建者
     */
    private User user;

    /**
     * 歌曲
     */
    private List<Song> songs;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getClicks_count() {
        return clicks_count;
    }

    public void setClicks_count(int clicks_count) {
        this.clicks_count = clicks_count;
    }

    public int getCollections_count() {
        return collections_count;
    }

    public void setCollections_count(int collections_count) {
        this.collections_count = collections_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getSongs_count() {
        return songs_count;
    }

    public void setSongs_count(int songs_count) {
        this.songs_count = songs_count;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
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
