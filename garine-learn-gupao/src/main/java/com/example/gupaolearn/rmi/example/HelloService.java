package com.example.gupaolearn.rmi.example;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloService extends Remote{

    void sayHello() throws RemoteException;
}
