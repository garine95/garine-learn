package com.garine.learn.myrpc.client;

import com.garine.learn.myrpc.registry.ServiceInfo;

import java.lang.reflect.Proxy;

/**
 * @author zhoujy
 * @date 2018年07月06日
 **/
public class RpcClientProxyFactory {

    <T> T createProxy(Class<T> tClass, ServiceInfo serviceInfo){
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class[]{tClass},new MyRpcInvocationHandler(serviceInfo));
    }
}
