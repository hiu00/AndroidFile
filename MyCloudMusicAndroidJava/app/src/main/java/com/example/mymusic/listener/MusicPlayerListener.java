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


}
