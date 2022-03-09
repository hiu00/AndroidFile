package com.example.mymusic.listener;

import com.example.mymusic.domain.Song;

import java.util.List;

/**
 * 音乐列表管理器
 * 主要是封装了列表相关的操作：
 * 上一曲，下一曲，循环模式
 */
public interface ListManager {
    /**
     * 设置播放列表
     * @param datum
     */
    void setDatum(List<Song> datum);

    /**
     * 获取播放列表
     * @return
     */
    List<Song> getDatum();

    void play(Song song);

    void pause();

    void resume();

    /**
     * 获取下一首音乐
     * @return
     */
    Song next();

    /**
     * 获取上一首音乐
     * @return
     */
    Song previous();

    /**
     * 更改循环模式
     * @return
     */
    int changeLoopModel();

    /**
     * 获取循环模式
     * @return
     */
    int getLoopModel();

    /**
     * 获取当前播放的音乐
     * @return
     */
    Song getData();

    /**
     * 删除音乐
     * @param position
     */
    void delete(int position);
}
