package com.example.mymusic.util;

import static com.example.mymusic.util.Constant.REGEX_EMAIL;
import static com.example.mymusic.util.Constant.REGEX_PHONE;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串相关工具类
 */
public class StringUtil {

    /**
     * 是否是手机号
     *
     * @param value
     * @return
     */
    public static boolean isPhone(String value) {
        //TODO 实现手机号格式判断
        return value.matches(REGEX_PHONE);
    }

    public static boolean isEmail(String value) {
        return value.matches(REGEX_EMAIL);
    }

    public static boolean isPassword(String value) {
        return value.length() >= 6 && value.length() <= 15;
    }

    public static boolean isNickname(String value) {
        return value.length() >= 2 && value.length() <= 10;
    }

    public static boolean isCode(String value) {
        return value.length() == 4;
    }

    /**
     * 将一行字符串拆分为单个字
     * 只实现了中文
     *
     * @param data
     * @return
     */
    public static String[] words(String data) {
        //创建一个列表
        List<String> results = new ArrayList<>();

        //转为char数组
        char[] chars = data.toCharArray();

        //循环每一个字符
        for (char c : chars
        ) {
            //转为字符串
            //添加到列表
            results.add(String.valueOf(c));
        }

        //转为数组
        return results.toArray(new String[results.size()]);
    }
}
