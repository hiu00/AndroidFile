package com.example.mymusic.util.lyric;

import com.example.mymusic.domain.Lyric.Lyric;

/**
 * 歌词解析器
 */
public class LyricParser {
    /**
     * 解析歌词
     *
     * @param type 歌词类型
     * @param content 歌词内容
     * @return 解析后的歌词对象
     */
    public static Lyric parse(int type, String content) {
        //默认解析LRC歌词
        return LRCLyricParser.parse(content);
    }
}
