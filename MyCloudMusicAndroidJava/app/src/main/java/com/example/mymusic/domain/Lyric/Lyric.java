package com.example.mymusic.domain.Lyric;

import java.io.Serializable;
import java.util.List;

/**
 * 解析后的歌词模型
 */
public class Lyric implements Serializable {
    /**
     * 所有的歌词
     */
    private List<Line> datum;

    public List<Line> getDatum() {
        return datum;
    }

    public void setDatum(List<Line> datum) {
        this.datum = datum;
    }
}
