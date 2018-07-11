package com.garine.learn.myrpc.registry.zookeeper;

import com.garine.learn.myrpc.registry.DiscoverCenter;
import com.garine.learn.myrpc.registry.ServiceInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 利用zookeeper实现的服务发现中心
 */
@Slf4j
@Data
public class ZkDiscoverCenter implements DiscoverCenter {

    private CuratorFramework curatorFramework;

    public ZkDiscoverCenter(){
        if (null == this.curatorFramework){
            try {
                this.curatorFramework = ZkConnectUtil.buildAZkConnection();
            } catch (Exception e) {
                log.error("创建zookeeper连接失败");
                e.printStackTrace();
            }
        }
    }


    @Override
    public ServiceInfo discover(ServiceInfo serviceInfo) {
        List<String> serhosts = null;
        try {
            serhosts = curatorFramework.getChildren().forPath("/"+ serviceInfo.getFullService());
            //TODO 负载均衡，选出可用服务地址
            if (CollectionUtils.isEmpty(serhosts)){
                throw new Exception("服务发现异常无可用服务");
            }
        } catch (Exception e) {
            log.error("服务发现异常");
            e.printStackTrace();
        }
        String loadedStr = serhosts.get(0);
        serviceInfo.setHostName(loadedStr.split(":")[0]);
        serviceInfo.setPort(loadedStr.split(":")[1]);
        return serviceInfo;
    }
}
