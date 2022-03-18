package com.example.mymusic.util;

import java.io.Serializable;

/**
 * 日期时间格式化
 */
public class TimeUtil {

    private static String data;

    /**
     * 将毫秒格式化为 分：秒
     *
     * @param data
     * @return
     */
    public static String formatMinuteSecond(int data) {
        if (data == 0) {
            return "00:00";
        }

        //转为秒
        data /= 1000;

        //计算分钟
        int minute = data / 60;

        //秒
        int second = data - (minute * 60);

        return String.format("%02d:%02d", minute, second);
    }

    /**
     * 将分秒毫秒数据转换为毫秒
     *
     * @param data 格式为00：00.000
     * @return
     */
    public static long parseToInt(String data) {
        //将：替换成.号
        data = data.replace(":", ".");

        //使用.拆分
        String[] strings = data.split("\\.");

        //分别取出分，秒，毫秒
        int m = Integer.parseInt(strings[0]);
        int s = Integer.parseInt(strings[1]);
        int ms = Integer.parseInt(strings[2]);

        //统一转为毫秒
        return (m * 60 + s) * 1000 + ms;
    }
}
