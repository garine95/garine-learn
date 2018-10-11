package com.example.gupaolearn.concurrentest;

public class SafeThreadDemo {
    public static void main(String[] args) {
        WrapObject wrapObject = new WrapObject();
        wrapObject.a = 30;
        final int tag = 30;
        System.out.println(tag + "--main-->" + wrapObject.a);
        new Thread(() ->{
            System.out.println(tag +"--join-->"+wrapObject.a);
        }).start();
    }

    static class WrapObject{
        public volatile int a = 10;
    }
}