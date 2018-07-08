package com.garine.learn.myrpc.sdemo.service.impl;

import com.garine.learn.myrpc.api.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHi(String str) {
        System.out.print("recevive client msg:" + str);
        return "received";
    }
}
