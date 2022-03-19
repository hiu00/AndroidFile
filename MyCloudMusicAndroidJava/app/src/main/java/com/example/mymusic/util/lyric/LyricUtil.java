package com.example.mymusic.util.lyric;

import com.example.mymusic.domain.Lyric.Line;
import com.example.mymusic.domain.Lyric.Lyric;

import java.util.List;

/**
 * 歌词相关
 */
public class LyricUtil {
    /**
     * 计算当前播放时间是那一行歌词
     *
     * @param lyric    歌词对象
     * @param position 播放时间
     * @return
     */
    public static int getLineNumber(Lyric lyric, long position){
        //先获取歌词列表
        List<Line> datum = lyric.getDatum();

        //倒序遍历每一行歌词
        Line line = null;
        for (int i = datum.size() - 1; i >= 0; i--) {
            line = datum.get(i);

            //如果当前时间正好大于等于改行开始时间
            //就是该行
            if (position >= line.getStartTime()) {
                return i;
            }
        }

        //默认第0行
        return 0;
    }
}
