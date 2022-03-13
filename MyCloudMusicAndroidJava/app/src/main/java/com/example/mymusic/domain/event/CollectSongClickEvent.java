package com.example.mymusic.domain.event;

import com.example.mymusic.domain.Song;

/**
 * 收藏歌曲到歌单点击事件
 */
public class CollectSongClickEvent {
    /**
     * 音乐
     */
    private Song data;

    /**
     * 构造方法
     * @param data
     */
    public CollectSongClickEvent(Song data) {
        this.data=data;
    }

    public Song getData() {
        return data;
    }

    public void setData(Song data) {
        this.data = data;
    }
}
