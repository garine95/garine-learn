package com.garine.learn.myrpc.registry.zookeeper;

import com.garine.learn.myrpc.registry.RegistryCenter;
import com.garine.learn.myrpc.registry.ServiceInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * zk实现的服务注册中心
 * @author zhoujy
 */
@Slf4j
@Data
public class ZkRegisterCenter implements RegistryCenter{

    private CuratorFramework curatorFramework;

    public ZkRegisterCenter(){
        if (null == this.curatorFramework){
            try {
                this.curatorFramework = ZkConnectUtil.buildAZkConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean registry(ServiceInfo serviceInfo) {
        String serpath = "/"+serviceInfo.getFullService();
        try {
            if (curatorFramework.checkExists().forPath(serpath) == null){
                curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(serpath, "0".getBytes());
            }
            Stat stat = new Stat();
            if (curatorFramework.checkExists().forPath(serpath+"/"+serviceInfo.getFullHost()) != null){
                curatorFramework.getData().storingStatIn(stat).forPath(serpath+"/"+serviceInfo.getFullHost());
                curatorFramework.delete().forPath(serpath+"/"+serviceInfo.getFullHost());
            }
            curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(serpath+"/"+serviceInfo.getFullHost(), "0".getBytes());
            System.out.print(serviceInfo.getFullService()+"服务注册成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
