package com.example.gupaolearn.zookeeper;

import com.example.gupaolearn.Util.CommonUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * curator封装api使用
 */
public class CuratorDemo {
    public static void main(String[] ar) throws Exception {
        CuratorFramework curator = CuratorFrameworkFactory.builder()
                .connectString("192.168.0.15:2181")
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace("curator").build();
        curator.start();

        curator.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/testNode", "1".getBytes());
        Stat stat = new Stat();
        byte[] bytes = curator.getData().storingStatIn(stat).forPath("/testNode");
        CommonUtil.println(new String(bytes));

        curator.setData().withVersion(stat.getVersion()).forPath("/testNode", "2".getBytes());
        bytes = curator.getData().storingStatIn(stat).forPath("/testNode");
        CommonUtil.println(new String(bytes));

        /**
         * PathChildCache 监听一个节点下子节点的创建、删除、更新
         * NodeCache  监听一个节点的更新和创建事件
         * TreeCache  综合PatchChildCache和NodeCache的特性
         */
        //使用方法大致如此
        NodeCache treeCache = new NodeCache(curator,"/testNode");
        treeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                CommonUtil.println("curator监听");
            }
        });
       treeCache.start();


        curator.setData().withVersion(stat.getVersion()).forPath("/testNode", "3".getBytes());
        bytes = curator.getData().storingStatIn(stat).forPath("/testNode");
        CommonUtil.println(new String(bytes));

        Thread.sleep(1000);
        curator.close();







































    }
}
