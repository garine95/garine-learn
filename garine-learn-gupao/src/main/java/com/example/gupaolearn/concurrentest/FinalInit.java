package com.example.gupaolearn.concurrentest;

public class FinalInit {
    public final int a;
    public static FinalInit thisPoint;
    public FinalInit() throws InterruptedException {
        thisPoint = this;

        Thread.sleep(5000);
        a = 10;
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                new FinalInit();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(1000);
        System.out.println(FinalInit.thisPoint.a);
    }
}
