package com.example.mymusic.util;

import com.example.mymusic.BuildConfig;

/**
 * 常量类
 */
public class Constant {
    /**
     * 端点
     */
    public static final String ENDPOINT= BuildConfig.ENDPOINT;

    /**
     * 资源端点
     */
    public static final String RESOURCE_ENDPOINT = "http://course-music-dev.ixuea.com/%s";

    /**
     * ID常量
     */
    public static final String ID = "ID";

    /**
     * 手机号正则表达式
     * 移动：134 135 136 137 138 139 147 150 151 152 157 158 159 178 182 183 184 187 188 198
     * 联通：130 131 132 145 155 156 166 171 175 176 185 186
     * 电信：133 149 153 173 177 180 181 189 199
     * 虚拟运营商: 170
     */
    public static final String REGEX_PHONE = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";


    /**
     * 邮箱正则表达式
     */
    public static final String REGEX_EMAIL = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
    public static final String TITLE = "TITLE";
    public static final String URL = "URL";

    /**
     * 广告点击了
     */
    public static final String ACTION_AD = "com.example.mymusic.ACTION_AD";

    public static final String NICKNAME = "NICKNAME";

    /**
     * 标题
     */
    public static final int TYPE_TITLE=0;

    /**
     * 歌单
     */
    public static final int TYPE_SHEET=1;

    /**
     * 单曲
     */
    public static final int TYPE_SONG=2;

    /**
     * 歌单ID
     */
    public static final String SHEET_ID = "SHEET_ID";

    public static final int MESSAGE_PROGRESS = 0;

    public static final long DEFAULT_TIME = 16;

    /**
     * 列表循环
     */
    public static final int MODEL_LOOP_LIST = 0;

    /**
     * 单曲循环
     */
    public static final int MODEL_LOOP_ONE = 1;

    /**
     * 随机循环
     */
    public static final int MODEL_LOOP_RANDOM = 2;

    public static final String SHEET = "SHEET";
    public static final String SONG = "SONG";

    /**
     * 音乐播放通知id
     */
    public static final int NOTIFICATION_MUSIC_ID = 10000;

    public static final String ACTION_PLAY = "com.example.mymusic.ACTION_PLAY";
    public static final String ACTION_NEXT = "com.example.mymusic.ACTION_NEXT";
    public static final String ACTION_LYRIC = "com.example.mymusic.ACTION_LYRIC";
    public static final String ACTION_LIKE = "com.example.mymusic.ACTION_LIKE";
    public static final String ACTION_PREVIOUS = "com.example.mymusic.ACTION_PREVIOUS";
    public static final String ACTION_MUSIC_PLAY_CLICK = "com.example.mymusic.ACTION_MUSIC_PLAY_CLICK";

    /**
     * 保持播放进度间隔（毫秒）
     */
    public static final int SAVE_PROGRESS_TIME = 1000;
}
