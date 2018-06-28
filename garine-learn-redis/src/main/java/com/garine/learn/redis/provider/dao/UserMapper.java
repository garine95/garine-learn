package com.garine.learn.redis.provider.dao;

import com.garine.learn.common.provider.dao.BaseMapper;
import com.garine.learn.redis.provider.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User>{
}
