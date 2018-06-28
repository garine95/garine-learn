package com.garine.learn.common.util;

/**
 * object工具类
 *
 * @author wugx
 * @date 2017/7/24 14:24
 */

import java.util.Comparator;


public final class Objects {


    public static <T> int compare(T a, T b, Comparator<? super T> c) {
        return (a == b) ? 0 : c.compare(a, b);
    }


    public static boolean isNull(Object obj) {
        return obj == null;
    }


    public static boolean nonNull(Object obj) {
        return obj != null;
    }

}

