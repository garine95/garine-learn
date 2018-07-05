package com.example.gupaolearn.zookeeper;

import com.example.gupaolearn.Util.CommonUtil;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZookeeperWatcherDemo {
    public static void main(String[] a) throws IOException, InterruptedException, KeeperException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper("192.168.0.15:2181", 40000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                CommonUtil.println("to->默认监听器");
                if (Event.KeeperState.SyncConnected==watchedEvent.getState()){
                    countDownLatch.countDown();
                    CommonUtil.println(watchedEvent.getType().toString());
                }
            }
        });

        countDownLatch.await();

        zooKeeper.create("/testNode", "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        Stat stat = new Stat();
        byte[] bytes = zooKeeper.getData("/testNode", null, stat);
        CommonUtil.println("获得数据->"+new String(bytes));
        //使用exists绑定事件
        zooKeeper.exists("/testNode", new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                CommonUtil.println("exists监听器");
                try {
                    zooKeeper.exists("/testNode", true);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        CommonUtil.println("设置数据2");
        zooKeeper.setData("/testNode", "2".getBytes(), stat.getVersion());


        bytes = zooKeeper.getData("/testNode", null, stat);
        CommonUtil.println("获得数据->"+new String(bytes));

        CommonUtil.println("设置数据3");
        zooKeeper.setData("/testNode", "3".getBytes(), stat.getVersion());









    }
}
