package com.garine.learn.myrpc.registry.zookeeper;

import com.garine.learn.myrpc.GlobalConfig;
import com.garine.learn.myrpc.registry.RegistryCenter;
import com.garine.learn.myrpc.registry.ServiceInfo;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ZkRegisterCenter implements RegistryCenter{

    @Resource
    private CuratorFramework curatorFramework;

    @Resource
    private GlobalConfig globalConfig;

    @Override
    public boolean registry(ServiceInfo serviceInfo) {
        String serpath = "/"+globalConfig.getFRAMEWORK_SPACE()+"/"+serviceInfo.getFullService();
        try {
            if (curatorFramework.checkExists().forPath(serpath) == null){
                curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(serpath, "0".getBytes());
            }
            curatorFramework.create().forPath(serpath+"/"+serviceInfo.getFullHost(), "0".getBytes());
            System.out.print(serviceInfo.getFullService()+"服务注册成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
