package com.example.mymusic.util;

import java.io.Serializable;

/**
 * 日期时间格式化
 */
public class TimeUtil {
    /**
     * 将毫秒格式化为 分：秒
     * @param data
     * @return
     */
    public static String formatMinuteSecond(int data) {
        if (data==0){
            return "00:00";
        }

        //转为秒
        data /= 1000;

        //计算分钟
        int minute=data/60;

        //秒
        int second = data-(minute*60);

        return String.format("%02d:%02d",minute,second);
    }
}
