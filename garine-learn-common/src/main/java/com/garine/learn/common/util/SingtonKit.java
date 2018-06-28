package com.garine.learn.common.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 单例工具类
 *
 * @author zhangh
 * @date 2016年9月20日
 */
public class SingtonKit {
    private final static Map<String, Object> MAP = Maps.newHashMap();

    /**
     * 根据类名实例化一个类，保存在Map中，如果已存在直接返回(不关心并发等情况)。
     *
     * @param className 类名
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String className) {
        Preconditions.checkNotNull(className);
        if (MAP.containsKey(className)) {
            return (T) MAP.get(className);
        }
        T obj = ReflectKit.create(className);
        if (obj != null) {
            MAP.put(className, obj);
        }
        return obj;
    }
}
