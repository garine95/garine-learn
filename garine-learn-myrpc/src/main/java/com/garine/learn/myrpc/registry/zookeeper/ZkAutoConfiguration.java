package com.garine.learn.myrpc.registry.zookeeper;

import com.garine.learn.myrpc.GlobalConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

//@Bean注解方法的会执行创建bean
@Configuration
//当以下类在路径上才执行创建bean，当前rpc已经引入
@ConditionalOnClass({CuratorFrameworkFactory.class, CuratorFramework.class})
//使配置bean生效
@EnableConfigurationProperties(value = {ZkProperties.class, GlobalConfig.class})
public class ZkAutoConfiguration {

    private final ZkProperties properties;

    @Resource
    private GlobalConfig globalConfig;

    public ZkAutoConfiguration(ZkProperties properties) {
        this.properties = properties;
    }

    @Bean
    /**
     * 配置文件属性是否为true,此处决定所有zookeeper配置是否加载
     */
    @ConditionalOnProperty(value = {"myrpc.zk.on"}, matchIfMissing = false)
    public CuratorFramework createCurator(){
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString(this.properties.getHost()+":"+this.properties.getPort())
                .retryPolicy(new ExponentialBackoffRetry(1000,2))
                .namespace(globalConfig.getFRAMEWORK_SPACE()).build();
        curatorFramework.start();
        return curatorFramework;
    }

    @Bean
    @ConditionalOnBean(CuratorFramework.class)
    public ZkRegisterCenter createRegister(){
        return new ZkRegisterCenter();
    }

    @Bean
    @ConditionalOnBean(CuratorFramework.class)
    public ZkDiscoverCenter createDiscover(){
        return new ZkDiscoverCenter();
    }
}
