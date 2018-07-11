package com.garine.learn.myrpc.registry.zookeeper;

import com.garine.learn.myrpc.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 创建一个zk连接，单例处理
 * @author zhoujy
 */
@Slf4j
public class ZkConnectUtil {

    public static volatile CuratorFramework curatorFramework;

    public synchronized static CuratorFramework buildAZkConnection() throws Exception {
        if (ZkConnectUtil.curatorFramework != null){
            return ZkConnectUtil.curatorFramework;
        }
        String host = PropertiesUtil.getProperty("myrpc.zk.host");
        String port = PropertiesUtil.getProperty("myrpc.zk.port");
        String nameSp = PropertiesUtil.getProperty("myrpc.global.namespace", "myrpc");
        if (StringUtils.isEmpty(host)){
            throw new Exception("Zookeeper host为空");
        }
        if (StringUtils.isEmpty(port)){
            throw new Exception("Zookeeper port为空");
        }
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString(host+":"+port)
                .retryPolicy(new ExponentialBackoffRetry(4000,2))
                .namespace(nameSp).build();
        curatorFramework.start();
        log.info("----------连接zookeeper成功");
        ZkConnectUtil.curatorFramework = curatorFramework;
        return curatorFramework;
    }
}
