package com.example.mymusic.manager;

/**
 * 全局（桌面）歌词管理器
 */
public interface GlobalLyricManager {
    /**
     * 显示桌面歌词
     */
    void show();

    /**
     * 隐藏桌面歌词
     */
    void hide();

    /**
     * 桌面歌词是否显示
     */
    boolean isShowing();

    /**
     * 尝试隐藏
     * 为什么是尝试隐藏呢？
     * 因为有可能当前都没有显示桌面歌词
     */
    void tryHide();

    /**
     * 尝试显示
     */
    void tryShow();


}
