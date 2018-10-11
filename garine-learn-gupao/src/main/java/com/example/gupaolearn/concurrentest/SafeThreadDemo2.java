package com.example.gupaolearn.concurrentest;

public class SafeThreadDemo2 {
    public static void main(String[] args) {
        WrapObject wrapObject = new WrapObject();
        synchronized (wrapObject){
            wrapObject.a = 30;
        }
        final int tag = 30;
        System.out.println(tag + "--main-->" + wrapObject.a);
        new Thread(() ->{
            synchronized (wrapObject){
                System.out.println(tag +"--join-->"+wrapObject.a);
            }
        }).start();
    }

    static class WrapObject{
        public int a = 10;
    }
}