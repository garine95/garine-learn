package com.garine.learn.myrpc.registry.zookeeper;

import com.garine.learn.myrpc.GlobalConfig;
import com.garine.learn.myrpc.registry.DiscoverCenter;
import com.garine.learn.myrpc.registry.ServiceInfo;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 利用zookeeper实现的发现中心
 */
@Component
public class ZkDiscoverCenter implements DiscoverCenter {

    @Resource
    private CuratorFramework curatorFramework;

    @Resource
    private GlobalConfig globalConfig;

    @Override
    public ServiceInfo discover(ServiceInfo serviceInfo) throws Exception {
        List<String> serhosts = curatorFramework.getChildren().forPath("/"+globalConfig.getFRAMEWORK_SPACE() + "/"+ serviceInfo.getFullService());
        //TODO 负载均衡，选出可用服务地址
        if (CollectionUtils.isEmpty(serhosts)){
            throw  new Exception("无可用服务");
        }
        String loadedStr = serhosts.get(0);
        return new ServiceInfo(loadedStr.split(":")[0], loadedStr.split(":")[1]);
    }
}
