package com.example.mymusic.domain.Lyric;

import com.example.mymusic.domain.BaseModel;

/**
 * 一行歌词
 */
public class Line extends BaseModel {
    /**
     * 整行歌词
     */
    private String data = null;

    /**
     * 开始时间
     * 单位毫秒
     */
    private long startTime = 0;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
