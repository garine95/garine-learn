package com.example.gupaolearn.rpc.bean;

import com.example.gupaolearn.Util.CommonUtil;
import com.example.gupaolearn.rpc.service.HelloService;

/**
 * 普通bean实现
 * @author zhoujy
 * @date 2018年07月10日
 **/
public class HelloServiceImpl implements HelloService{
    @Override
    public String sayHello(String str) {
        CommonUtil.println("\nsay:" + str);
        return "back";
    }
}
