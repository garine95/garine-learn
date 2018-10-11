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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Nio {
    // 本地字符集
    private static final String LocalCharSetName = "UTF-8";

    // 本地服务器监听的端口
    private static final int Listenning_Port = 8888;

    // 缓冲区大小
    private static final int Buffer_Size = 1024;

    // 超时时间,单位毫秒
    private static final int TimeOut = 3000;

    public static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException, InterruptedException {
        // 创建一个在本地端口进行监听的服务Socket信道.并设置为非阻塞方式
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.socket().bind(new InetSocketAddress(Listenning_Port));
        //TODO 设置为非阻塞，看看不阻塞的效果
        serverChannel.configureBlocking(false);
        // 创建一个选择器并将serverChannel注册到它上面，serverChannel负责预先接收客户端连接请求，让客户请求进行“排队”
        Selector selector = Selector.open();
        //设置为客户端请求连接时，默认客户端已经连接上
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        listen(selector);
        return;

    }

    private static void listen(Selector selector) throws InterruptedException, IOException {
        while (true) {
            // 轮询监听key，select是阻塞的，accept()也是阻塞的
            if (selector.select(TimeOut) == 0) {
                System.out.println(".");
                continue;
            }
            // 监听到在selector上有新的事件注册，Reactor模式；新来的客户端请求可以先和服务器建立联系，排队。等相应事件空闲就可以进行操作建立通道。
            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
            while (keyIter.hasNext()) {
                processKey(selector, keyIter);
                keyIter.remove();
            }
        }
    }

    private static void processKey(Selector selector, Iterator<SelectionKey> keyIter) throws IOException, InterruptedException {
        SelectionKey key = keyIter.next();
        if (key.isAcceptable()) {
            //可与客户端建立连接事件
            SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
            clientChannel.configureBlocking(false);
            //客户端信道往selector注册可读key，接下来可以进行读取
            clientChannel.register(selector, SelectionKey.OP_READ,
                    ByteBuffer.allocate(Buffer_Size));
            //重置这个key表示可以接受其他客户端建立连接
            key.interestOps(SelectionKey.OP_ACCEPT);
        }
        else if (key.isReadable()) {
            //通道可读事件
            //selector发现这个key是可读的（就是上次咱自己往上面注册的key呢）
            SocketChannel clientChannel = (SocketChannel) key.channel();
            //TODO 接下来是java缓冲区io操作，避免io堵塞；面向缓冲区可是事先把数据写到缓冲区，我们读取时可以直接读取缓冲区数据，不需要用的时候再读取
            ByteBuffer buffer = ByteBuffer.allocate(2048);
            buffer.clear();
            long bytesRead = clientChannel.read(buffer);
            //TODO 已经写数据到缓冲区，尝试在这里开始处理下一个key，异步处理业务逻辑，看效率--->不能异步处理回写数据，不用回写的业务处理倒是可以分离
            CommonUtil.println("缓冲写完毕，异步处理业务");
            //new LogicHandler(buffer,bytesRead,clientChannel).run();
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
                //异步无效，同一时间只允许一个channel进行读写，异步写的话channel可能已经关闭了，不行；只能数据读取和业务逻辑同步处理
                //executorService.execute(new LogicHandler(buffer, bytesRead,clientChannel));
                key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            }

        }
    }

    static class LogicHandler implements Runnable{
        private ByteBuffer buffer;
        private long bytesRead;
        private SocketChannel clientChannel;
        public LogicHandler(ByteBuffer buffer, long bytesRead, SocketChannel clientChannel){
            this.buffer = buffer;
            this.bytesRead = bytesRead;
            this.clientChannel = clientChannel;
        }

        @Override
        public void run() {
            try {
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
                    Thread.sleep(100);
                    //clientChannel.write(buffer);

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
