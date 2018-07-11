package com.garine.learn.myrpc.client;

import com.garine.learn.myrpc.registry.ServiceInfo;
import lombok.Data;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * 动态代理类FactoryBean，用于工厂方式实例化代理类bean
 * @author zhoujy
 * @date 2018年07月06日
 **/
@Data
public class RpcClientProxyFactory<T> implements FactoryBean<T>{

    private Class<T> interfaceClass;

    private ServiceInfo serviceInfo;

    @Override
    public T getObject() throws Exception {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass},new MyRpcInvocationHandler(serviceInfo));
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
