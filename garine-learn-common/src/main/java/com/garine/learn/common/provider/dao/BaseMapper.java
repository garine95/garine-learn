package com.garine.learn.common.provider.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 基础公用的 mybatis mapper, 拥有一些常用的数据层接口。
 * 自定义的 mapper 接口可以继承此接口获得基础操作数据的功能。
 *
 * @author linxiaomin
 * @date 2017年02月27日
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
