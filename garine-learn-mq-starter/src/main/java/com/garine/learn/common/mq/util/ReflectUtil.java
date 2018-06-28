package com.garine.learn.common.mq.util;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author wugx
 * @date 2018/4/9 17:40
 */
public class ReflectUtil {

    /**
     * 获取属性值
     * @param clazz
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    public static <T> List<String> getFieldValues(Class<T> clazz) throws IllegalAccessException {
        List<String> queueNames = Lists.newArrayList();
        Field[] fields = clazz.getFields();
        for(Field field : fields){
            queueNames.add(field.get(clazz).toString());
        }
        return queueNames;
    }
}
