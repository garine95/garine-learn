/*
 * Copyright (c) 2017 4PX Information Technology Co.,Ltd. All rights reserved.
 */
package com.garine.learn.common.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuchao
 * @date 2017年5月16日
 */
public class CollectionKit {

    /**
     * 集合去重
     * 适合普通类型、String或重写了equals方法的自定义类型
     *
     * @return 去重后的集合
     */
    public static <T> List<T> distinct(List<T> c) {
        return distinct(c, null);
    }

    /**
     * 集合去重
     * 需通过继承比较器接口实现自定义比较规则
     * 如比较器为空，则采用默认equals进行比较筛选
     *
     * @param c       去重集合
     * @param compare 实现自ICompare比较器接口
     */
    public static <T> List<T> distinct(List<T> c, ICompare<T> compare) {
        if (null == c || c.size() == 0) {
            return c;
        }
        List<T> result = new ArrayList<T>();
        for (T e : c) {
            if (!exist(result, e, compare)) {
                result.add(e);
            }
        }
        return result;
    }

    /**
     * 是否存在集合中
     *
     * @param c 集合
     * @param o 是否存在的对象
     * @return true或false
     */
    public static <T> boolean exist(Collection<T> c, T o) {
        return exist(c, o, null);
    }

    /**
     * 是否存在集合中
     *
     * @param c 集合
     * @param o 检查的对象
     * @return true或false
     */
    public static <T> boolean exist(Collection<T> c, T o, ICompare<T> compare) {
        if (null == o) {
            for (T e : c) {
                if (null == e) {
                    return true;
                }
            }
        } else if (null == compare) {
            for (T e : c) {
                if (o.equals(e)) {
                    return true;
                }
            }
        } else {
            for (T e : c) {
                if (compare.equals(o, e)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * LIST 转为 Map
     *
     * eg:list2Map(list,"getId",DemoClass.class);
     */
    public static <K, V> Map<K, V> list2Map(List<V> list, String keyMethodName, Class<V> c) {
        Map<K, V> map = new HashMap<K, V>();
        if (list != null) {
            try {
                Method methodGetKey = c.getMethod(keyMethodName);
                for (int i = 0; i < list.size(); i++) {
                    V value = list.get(i);
                    @SuppressWarnings("unchecked")
                    K key = (K) methodGetKey.invoke(list.get(i));
                    map.put(key, value);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("field can't match the key!");
            }
        }

        return map;
    }

    /**
     * 比较器协定
     *
     * @author liuchao
     */
    interface ICompare<T> {
        /**
         * @return boolean
         */
        boolean equals(T o, T s);
    }
}
