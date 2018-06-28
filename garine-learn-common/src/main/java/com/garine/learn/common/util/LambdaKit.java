package com.garine.learn.common.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

/**
 * 常用java8的lambda工具类
 * 
 * @author zhangh
 * @date 2018年3月10日
 * @since
 */
public class LambdaKit {

    /**
     * List<T> 转为 Map<K,T>，过滤重复key值
     * <p>例：Lambdakit.listToMap(lstSku, OtaskSkuDTO::getSkuId)
     * @param lstObj
     * @param keyMapper
     * @return
     */
    public static <K, T> Map<K, T> listToMap(Collection<T> list, Function<T, K> keyMapper) {
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }
        return list.stream().collect(Collectors.toMap(keyMapper, Function.identity(), (key1, key2) -> key2));
    }
    
    /**
     * 合计
     * @param lstObj
     * @param mapper
     * @return
     */
    public static <T> Integer sum(Collection<T> list, ToIntFunction<? super T> mapper) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        return list.stream().mapToInt(mapper).sum();
    }
    
    /**
     * 合计
     * @param lstObj
     * @param mapper
     * @return
     */
    public static <T> Double total(Collection<T> list, ToDoubleFunction<? super T> mapper) {
        if (CollectionUtils.isEmpty(list)) {
            return 0d;
        }
        return list.stream().mapToDouble(mapper).sum();
    }
    
    /**
     * 过滤
     * <p>例：LambdaKit.filter(lstOtask, task -> task.getStatus().equals(taskStatus))
     * @param lstObj
     * @param predicate
     * @return
     */
    public static <T> List<T> filter(List<T> list, Predicate<? super T> predicate) {
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        return list.stream().filter(predicate).collect(Collectors.toList());
    }
    
    /**
     * 转换集合属性
     * @param lstObj
     * @param predicate
     * @return
     */
    public static <T, R> List<R> convert(Collection<T> list, Function<T, R> mapper) {
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        return list.stream().map(mapper).collect(Collectors.toList());
    }
    /**
     * 获取唯一属性
     * @param lstObj
     * @param predicate
     * @return
     */
    public static <T, R> List<R> convertUnique(Collection<T> list, Function<T, R> mapper) {
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        return list.stream().map(mapper).distinct().collect(Collectors.toList());
    }

    /**
     * 按属性分组(相同属性Map.get->List)
     * @param list
     * @param keyFunction
     * @return
     */
    public static <K, T> Multimap<K, T> group(Collection<T> list, com.google.common.base.Function<? super T, K> keyFunction) {
        if (CollectionUtils.isEmpty(list)) {
            list = Lists.newArrayList();
        }
        return Multimaps.index(list, keyFunction);
    }
    
}
