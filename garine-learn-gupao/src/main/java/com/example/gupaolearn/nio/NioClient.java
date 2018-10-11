package com.example.gupaolearn.nio;

import org.springframework.util.StopWatch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class NioClient {

    public static void main(String[] args) throws UnknownHostException,
            IOException, InterruptedException {
        StopWatch stopWatch = new StopWatch();
        int loopTimes = 300;
        CountDownLatch countDownLatch = new CountDownLatch(loopTimes);
        stopWatch.start(loopTimes+"个请求任务处理耗时");
        for (int i=1;i<=loopTimes;i++){
            new Thread(() -> {
                try {
                    request();
                    countDownLatch.countDown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        countDownLatch.await();
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    private static void request() throws IOException {
        Socket s = new Socket("localhost", 8888);

        InputStream inStream = s.getInputStream();
        OutputStream outStream = s.getOutputStream();

        // 输出
        PrintWriter out = new PrintWriter(outStream, true);
        String baseStr = new Random(1000).nextInt() + "getPublicKey你好！";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1;i<=100;i++){
            stringBuilder.append(baseStr);
        }
        out.println(stringBuilder.toString());
        out.flush();
        s.shutdownOutput();// 输出结束

        // 输入
        Scanner in = new Scanner(inStream);
        StringBuilder sb = new StringBuilder();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            sb.append(line);
        }
        String response = sb.toString();
        System.out.println("response=" + response);
    }
}