package com.garine.source.analy.demo.transaction.impl;

import com.garine.source.analy.demo.transaction.TestService;
import com.garine.source.analy.demo.transaction.entity.Test;
import com.garine.source.analy.demo.transaction.exception.NeedToInterceptException;
import com.garine.source.analy.demo.transaction.mapper.TestMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zhoujy
 * @date 2018年12月06日
 **/
@Component
public class TestServiceImpl implements TestService {
    @Resource
    TestMapper testMapper;

    @Override
    @Transactional
    public void insertTest() {
        int re = testMapper.insert(new Test(10,20,30));
        if (re > 0) {
            throw new NeedToInterceptException("need intercept");
        }
        testMapper.insert(new Test(210,20,30));
    }
}
