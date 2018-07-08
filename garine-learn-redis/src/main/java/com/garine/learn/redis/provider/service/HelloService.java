package com.garine.learn.redis.provider.service;


import com.garine.learn.redis.provider.MyRpcClient;

@MyRpcClient
public interface HelloService {
    String sayHi(String str);
}
