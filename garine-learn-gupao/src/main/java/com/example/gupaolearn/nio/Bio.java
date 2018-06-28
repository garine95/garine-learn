package com.example.gupaolearn.nio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Bio {
    public static void main(String[] args) throws IOException {
            ServerSocket server = new ServerSocket(8888);
            System.out.println("服务器已经启动！");
            // 接收客户端发送的信息
            while(true){
                Socket socket = null;
                try {
                    socket = server.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Socket finalSocket = socket;
                new Thread(() ->{
                    try {
                        InputStream is = finalSocket.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));

                        String info = null;
                        while ((info = br.readLine()) != null) {
                            System.out.println(info);
                        }
                        // 向客户端写入信息
                        OutputStream os = finalSocket.getOutputStream();
                        String str = "欢迎登陆到server服务器!";
                        os.write(str.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
    }
}