package com.example.gupaolearn.rmi.example;

import com.example.gupaolearn.Util.CommonUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {

    protected HelloServiceImpl() throws RemoteException{
        super();
    }
    @Override
    public void sayHello() throws RemoteException{
        CommonUtil.println("你好啊，接收到调用");
    }
}
