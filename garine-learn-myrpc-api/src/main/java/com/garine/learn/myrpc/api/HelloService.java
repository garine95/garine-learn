package com.garine.learn.myrpc.api;

import com.garine.learn.myrpc.annotation.MyRpcClient;
import com.garine.learn.myrpc.annotation.MyRpcServer;

@MyRpcClient
@MyRpcServer
public interface HelloService {
    String sayHi(String str);
}
