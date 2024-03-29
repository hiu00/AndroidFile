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

}
