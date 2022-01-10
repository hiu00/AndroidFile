package com.example.mymusic.util;

import static com.example.mymusic.util.Constant.REGEX_PHONE;

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
    public static boolean isPhone(String value){
        //TODO 实现手机号格式判断
        return value.matches(REGEX_PHONE);
    }
}
