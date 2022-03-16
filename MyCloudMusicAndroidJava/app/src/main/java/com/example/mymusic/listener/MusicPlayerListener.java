package com.example.mymusic.listener;

import android.media.MediaPlayer;

import com.example.mymusic.domain.Song;

/**
 * 播放器接口
 *
 * 音乐监听器
 */
public interface MusicPlayerListener {
    /**
     * 已经暂停了
     */
    void onPaused(Song data);

    /**
     * 已经播放了
     */
    void onPlaying(Song data);

    /**
     * 播放器准备完毕了
     * @param mediaPlayer
     * @param data
     */
    void onPrepared(MediaPlayer mediaPlayer, Song data);

    /**
     * 播放进度回调
     * @param data
     */
    void onProgress(Song data);

    /**
     * 播放完毕了监听器
     * @param mediaPlayer
     */
    default void onCompletion(MediaPlayer mediaPlayer){}

    /**
     * 歌词数据改变了
     * @param data
     */
    default void onLyricChanged(Song data){}
}
