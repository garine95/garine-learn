package com.garine.learn.servertest.server;

import com.garine.learn.myrpc.registry.zookeeper.ZkRegisterCenter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhoujy
 * @date 2018年07月06日
 **/
@Component
public class Test {
    @Resource
    ZkRegisterCenter zkRegisterCenter;

    public void test(){
        zkRegisterCenter.registry(null);
    }
}
