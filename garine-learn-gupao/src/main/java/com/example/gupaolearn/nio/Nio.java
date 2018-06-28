package com.example.gupaolearn.nio;

import com.example.gupaolearn.Util.CommonUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class Nio {
    // 本地字符集
    private static final String LocalCharSetName = "UTF-8";

    // 本地服务器监听的端口
    private static final int Listenning_Port = 8888;

    // 缓冲区大小
    private static final int Buffer_Size = 1024;

    // 超时时间,单位毫秒
    private static final int TimeOut = 3000;

    public static void main(String[] args) throws IOException {
        // 创建一个在本地端口进行监听的服务Socket信道.并设置为非阻塞方式
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.socket().bind(new InetSocketAddress(Listenning_Port));
        serverChannel.configureBlocking(false);
        // 创建一个选择器并将serverChannel注册到它上面
        Selector selector = Selector.open();
        //设置为客户端请求连接时，默认客户端已经连接上
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            // 轮询监听key，select是阻塞的，accept()也是阻塞的
            if (selector.select(TimeOut) == 0) {
                System.out.println(".");
                continue;
            }
            // 有客户端请求，被轮询监听到
            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
            while (keyIter.hasNext()) {
                SelectionKey key = keyIter.next();
                if (key.isAcceptable()) {
                    SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
                    clientChannel.configureBlocking(false);
                    //客户端信道往selector注册可读key，接下来可以进行读取
                    clientChannel.register(selector, SelectionKey.OP_READ,
                            ByteBuffer.allocate(Buffer_Size));
                }
                else if (key.isReadable()) {
                    //selector发现这个key是可读的（就是上次咱自己往上面注册的key呢）
                    SocketChannel clientChannel = (SocketChannel) key.channel();
                    // 接下来是java缓冲区io操作，避免io堵塞
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    buffer.clear();
                    long bytesRead = clientChannel.read(buffer);
                    CommonUtil.println("laila");
                    if (bytesRead == -1) {
                        // 没有读取到内容的情况
                        clientChannel.close();
                    } else {
                        // 将缓冲区准备为数据传出状态
                        buffer.flip();
                        // 将获得字节字符串(使用Charset进行解码)
                        String receivedString = Charset
                                .forName(LocalCharSetName).newDecoder().decode(buffer).toString();
                        System.out.println("接收到信息:" + receivedString);
                        String sendString = "你好,客户端. 已经收到你的信息" + receivedString;
                        buffer = ByteBuffer.wrap(sendString.getBytes(LocalCharSetName));

                        clientChannel.write(buffer);
                        key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    }
                }

                keyIter.remove();
            }
        }

    }
}
