package com.garine.learn.myrpc.server;

import lombok.extern.slf4j.Slf4j;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 监听请求
 * rpc服务发布类
 * @author zhoujy
 */
@Slf4j
public class RpcServerPublisher {
    //创建一个线程池
    private static final ExecutorService executorService= Executors.newCachedThreadPool();

    // 存放服务名称和服务对象之间的关系
    public static Map<String,Object> handlerMap=new HashMap<>();

    /**
     * 启动线程循环监听提供服务的端口
     * @throws Exception
     */
    public void publish() throws Exception {
        //监听服务端口
        log.info("\n发布服务列表:{}\n启动监听端口:{}", RpcServerPublisher.handlerMap.keySet(),8888);
        ServerSocket serverSocket = new ServerSocket(8888);
        while (true){
            Socket socket = serverSocket.accept();
            executorService.execute(new RequestHandler(socket));
        }
    };
}
