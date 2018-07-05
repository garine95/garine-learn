package com.example.gupaolearn.zookeeper;

import com.example.gupaolearn.Util.CommonUtil;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZookeeperApiDemo {
    public static void main(String[] a) throws IOException, InterruptedException, KeeperException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper("192.168.0.15:2181", 20000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                //连接回调事件
                if (Event.KeeperState.SyncConnected == watchedEvent.getState()){
                    CommonUtil.println("连接成功回调执行");
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();

        //添加节点
        zooKeeper.create("/testNode", "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        Thread.sleep(1000);
        //存放版本
        Stat stat = new Stat();
        byte[] bytes = zooKeeper.getData("/testNode", null, stat);
        CommonUtil.println("获得数据："+new String(bytes));
        //修改节点
        zooKeeper.setData("/testNode", "2".getBytes(), stat.getVersion());
        bytes = zooKeeper.getData("/testNode", null, stat);
        CommonUtil.println("获得数据："+new String(bytes));

        zooKeeper.delete("/testNode", stat.getVersion());
    }
}
