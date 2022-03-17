package com.example.mymusic.domain.event;

import com.example.mymusic.domain.Song;

/**
 * 黑胶唱片停止通知
 */
public class OnStopRecordEvent {
    /**
     * 音乐
     */
    private Song data;

    public OnStopRecordEvent(Song data) {
        this.data=data;
    }

    public Song getData() {
        return data;
    }

    public void setData(Song data) {
        this.data = data;
    }
}
