package com.garine.learn.myrpc.client;

import com.garine.learn.myrpc.Request;
import com.garine.learn.myrpc.registry.ServiceInfo;
import com.garine.learn.myrpc.transport.Transport;
import com.garine.learn.myrpc.transport.socket.SocketTransport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author zhoujy
 * @date 2018年07月06日
 **/
public class MyRpcInvocationHandler implements InvocationHandler{
    private ServiceInfo serviceInfo;

    public MyRpcInvocationHandler(ServiceInfo serviceInfo){
        this.serviceInfo = serviceInfo;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request = new Request();
        request.setServiceName(serviceInfo.getServiceName());
        request.setServiceVersion(serviceInfo.getServiceVersion());
        request.setArgs(args);
        request.setMethodName(method.getName());
        request.setHostName(serviceInfo.getHostName());
        request.setPort(Integer.parseInt(serviceInfo.getPort()));

        Transport socketTransport = new SocketTransport();
        Object result = socketTransport.sendMessage(request);
        return result;
    }
}
