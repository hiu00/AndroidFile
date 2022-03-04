package com.example.mymusic.util;

import com.example.mymusic.listener.Consumer;

import java.util.List;

/**
 * 列表工具类
 */
public class ListUtil {

    public static <T> void eachListener(List<T> datum, Consumer<T> action){
        for (T listener:datum) {
            //将列表中每一个对象传递给action
            action.accept(listener);
        }
    }
}
