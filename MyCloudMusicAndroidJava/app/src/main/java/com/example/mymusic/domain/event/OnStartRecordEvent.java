package com.example.mymusic.domain.event;

import com.example.mymusic.domain.Song;

/**
 * 黑胶唱片开始滚动事件
 */
public class OnStartRecordEvent {
    /**
     * 当前音乐
     */
    private Song data;

    /**
     * 构造方法
     * @param data
     */
    public OnStartRecordEvent(Song data) {
        this.data=data;
    }

    public Song getData() {
        return data;
    }

    public void setData(Song data) {
        this.data = data;
    }
}
