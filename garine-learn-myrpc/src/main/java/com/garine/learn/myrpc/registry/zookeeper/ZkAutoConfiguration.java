package com.garine.learn.myrpc.registry.zookeeper;

import com.garine.learn.myrpc.GlobalConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@ConditionalOnClass({CuratorFrameworkFactory.class, CuratorFramework.class})
@EnableConfigurationProperties(value = {ZkProperties.class, GlobalConfig.class})
public class ZkAutoConfiguration {

    private final ZkProperties properties;

    @Resource
    private GlobalConfig globalConfig;

    public ZkAutoConfiguration(ZkProperties properties) {
        this.properties = properties;
    }

    public CuratorFramework createCurator(){
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString(this.properties.getHost()+":"+this.properties.getPort())
                .retryPolicy(new ExponentialBackoffRetry(1000,2))
                .namespace(globalConfig.getFRAMEWORK_SPACE()).build();
        curatorFramework.start();
        return curatorFramework;
    }
}
