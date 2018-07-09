package com.garine.mock;

import com.garine.dubbo.api.HelloService;

public class TestMock implements HelloService {
    @Override
    public String sayhi(String str) {
        System.out.println("调用服务失败，服务降级处理，系统繁忙");
        return "系统繁忙";
    }
}
