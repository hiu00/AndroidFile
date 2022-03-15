package com.example.mymusic.util;

import com.example.mymusic.domain.Song;

import java.util.List;

/**
 * 数据处理工具类
 */
public class DataUtil {
    /**
     * 更改是否在播放列表字段
     * @param datum
     * @param value
     */
    public static void changePlayListFlag(List<Song> datum, boolean value) {
        for (Song data : datum) {
            data.setPlayList(value);
        }
    }
}
