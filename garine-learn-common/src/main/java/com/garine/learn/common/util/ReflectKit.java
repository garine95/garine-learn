package com.garine.learn.common.util;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 反射工具类
 *
 * @author zhanghao
 * @date 2017年5月3日
 * @since
 */
public class ReflectKit {


    /**
     * 根据类名实例化一个对象
     *
     * @param className 类名
     */
    @SuppressWarnings("unchecked")
    public static <T> T create(String className) {
        try {
            return (T) Class.forName(className).newInstance();
        } catch (Exception e) {
            Throwables.throwIfUnchecked(e);
        }
        return null;
    }

    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     * 排除序列化的处理
     */
    public static List<Map<String, Object>> getFiledsInfo(Object target) {
        Field[] fields = target.getClass().getDeclaredFields();
        List<Map<String, Object>> list = Lists.newArrayList();
        for (Field field : fields) {
            if (!"serialVersionUID".equals(field.getName())) {
                Map<String, Object> infoMap = Maps.newHashMap();
                infoMap.put("type", field.getType().toString());
                infoMap.put("name", field.getName());
                infoMap.put("value", ReflectionUtils.getField(field, target));
                list.add(infoMap);
            }
        }
        return list;
    }

    /**
     * 获取类属性名
     */
    public static String[] getFieldNames(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<String> list = Lists.newArrayList();
        for (Field field : fields) {
            if (!"serialVersionUID".equals(field.getName())) {
                list.add(field.getName());
            }
        }
        return list.toArray(new String[list.size()]);
    }
}
