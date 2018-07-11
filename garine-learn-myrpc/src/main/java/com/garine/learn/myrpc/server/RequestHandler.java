package com.garine.learn.myrpc.server;

import com.garine.learn.myrpc.BeanRegistryUtil;
import com.garine.learn.myrpc.Request;
import com.garine.learn.myrpc.registry.ServiceInfo;
import com.garine.learn.myrpc.transport.Transport;
import com.garine.learn.myrpc.transport.socket.SocketTransport;
import lombok.extern.slf4j.Slf4j;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * 服务端处理请求类，选择出对应服务接口进行调用
 * @author zhoujy
 */
@Slf4j
public class RequestHandler implements Runnable{
    private Socket socket;

    public RequestHandler(Socket socket) {
        this.socket=socket;
    }
    @Override
    public void run() {
        try {
            Transport transport = new SocketTransport();
            Request request = transport.receiveMessage(socket);
            ServiceInfo serviceInfo = (ServiceInfo) RpcServerPublisher.handlerMap.get(request.getServiceName()+request.getServiceVersion());
            if (serviceInfo == null){
                log.error("服务{}不存在", request.getServiceName()+request.getServiceVersion());
                throw new Exception("服务不存在");
            }
            String className = serviceInfo.getServiceName();

            Class<?> serviceClass = Class.forName(className);
            Object serviceBean = BeanRegistryUtil.getBean(serviceClass);
            Object[] args = request.getArgs();
            Class<?>[] types=new Class[args.length];
            for(int i=0;i<args.length;i++){
                types[i]=args[i].getClass();
            }
            Method method=serviceClass.getMethod(request.getMethodName(),types);
            Object result = method.invoke(serviceBean,args);

            OutputStream out = socket.getOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(result);
            out.flush();
            objOut.flush();
            objOut.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
