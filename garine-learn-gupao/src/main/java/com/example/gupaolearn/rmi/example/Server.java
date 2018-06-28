package com.example.gupaolearn.rmi.example;

import com.example.gupaolearn.Util.CommonUtil;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

    public static void main(String[] argds) throws RemoteException, MalformedURLException {
        HelloServiceImpl helloService = new HelloServiceImpl();
        LocateRegistry.createRegistry(1099);
        Naming.rebind("rmi://127.0.0.1/Hello", helloService);
        CommonUtil.println("服务启动");
    }
}
