package com.garine.learn.dubbo.impl;

import com.garine.dubbo.api.HelloService;

public class HelloServiceImpl implements HelloService{
    @Override
    public String sayhi(String str) {
        System.out.println("接收到客户端信息：" + str);
        return "我是服务器";
    }
}
