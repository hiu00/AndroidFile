package com.example.mymusic.listener;

/**
 * 消费者接口
 *
 * 重构播放器状态分发逻辑
 */
public interface Consumer<T> {

    void accept(T t);
}
