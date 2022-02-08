package com.example.mymusic.domain;

import static com.example.mymusic.util.Constant.TYPE_SONG;

public class Song extends BaseMultiItemEntity {
    @Override
    public int getItemType() {
        return TYPE_SONG;
    }
}
