package com.garine.learn.myrpc.transport.socket;

import com.garine.learn.myrpc.Request;
import com.garine.learn.myrpc.transport.Transport;

import java.io.*;
import java.net.Socket;

/**
 * @author zhoujy
 * @date 2018年07月06日
 **/
public class SocketTransport implements Transport{
    @Override
    public Object sendMessage(Request request){
        Socket socket = null;
        OutputStream out = null;
        ObjectOutputStream objOut = null;
        try {
            socket = new Socket(request.getHostName(), request.getPort());
            out = socket.getOutputStream();
            objOut = new ObjectOutputStream(out);
            objOut.writeObject(request);
            out.flush();
            objOut.flush();

            socket.shutdownOutput();
            InputStream in = socket.getInputStream();
            ObjectInputStream objIn = new ObjectInputStream(in);
            Object result = objIn.readObject();
            objOut.close();
            out.close();
            objIn.close();
            in.close();

            return result;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Request receiveMessage(Object... ins) throws RuntimeException {
        if (ins.length <= 0){
            throw new RuntimeException("通信通道输入非法");
        }
        Socket socket = (Socket)ins[0];
        InputStream in = null;
        try {
            in = socket.getInputStream();
            ObjectInputStream objIn = new ObjectInputStream(in);
            Request request = (Request) objIn.readObject();
            objIn.close();
            in.close();
            return request;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
