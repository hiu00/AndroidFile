package com.example.mymusic.util;

import com.example.mymusic.domain.event.PlayListChangedEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 发布通知框架工具类
 */
public class EventBusUtil {
    public static void postPlayListChangedEvent() {
        EventBus.getDefault().post(new PlayListChangedEvent());
    }
}
