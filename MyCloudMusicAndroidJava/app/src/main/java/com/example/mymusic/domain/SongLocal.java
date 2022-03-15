package com.example.mymusic.domain;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 保存歌曲信息
 */
public class SongLocal extends RealmObject {
    /**
     * 歌曲Id
     * 数据库主键
     */
    @PrimaryKey
    private String id;

    /**
     * 歌曲标题
     */
    private String title;

    /**
     * 歌曲封面
     */
    private String banner;

    /**
     * 歌曲路径
     */
    private String uri;

    /**
     * 歌手Id
     */
    private String singer_id;

    /**
     * 歌手名称
     */
    private String singer_nickname;

    /**
     * 歌手头像
     * 可选值
     */
    private String singer_avatar;

    /**
     * 是否在播放列表中
     * true：在
     */
    private boolean playList;


    /**
     * 音乐来源
     */
    private int source;

    //定义两个常量
    /**
     * 其他来源音乐
     * 包括在线的，下载的
     */
    public static final int SOURCE_OTHER = 0;

    /**
     * 本地音乐
     */
    public static final int SOURCE_LOCAL = 1;


    //播放音乐后才有值
    /**
     * 总进度
     * 单位：毫秒
     */
    private long duration;

    /**
     * 音乐播放进度
     */
    private long progress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getSinger_id() {
        return singer_id;
    }

    public void setSinger_id(String singer_id) {
        this.singer_id = singer_id;
    }

    public String getSinger_nickname() {
        return singer_nickname;
    }

    public void setSinger_nickname(String singer_nickname) {
        this.singer_nickname = singer_nickname;
    }

    public String getSinger_avatar() {
        return singer_avatar;
    }

    public void setSinger_avatar(String singer_avatar) {
        this.singer_avatar = singer_avatar;
    }

    public boolean isPlayList() {
        return playList;
    }

    public void setPlayList(boolean playList) {
        this.playList = playList;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }
}
