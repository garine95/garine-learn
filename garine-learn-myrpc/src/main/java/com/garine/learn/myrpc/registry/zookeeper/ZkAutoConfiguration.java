package com.garine.learn.myrpc.registry.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置类，参考spring boot自动配置的实现
 */
//@Bean注解方法的会执行创建bean
@Configuration
//当以下类在路径上才执行创建bean，当前rpc已经引入
@ConditionalOnClass({CuratorFrameworkFactory.class, CuratorFramework.class})
//使配置bean生效
@EnableConfigurationProperties(value = {ZkProperties.class})
@Slf4j
public class ZkAutoConfiguration {

    private final ZkProperties properties;


    public ZkAutoConfiguration(ZkProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnProperty(value = {"myrpc.zk.on"}, matchIfMissing = false)
    public CuratorFramework createCurator() throws Exception {
        return ZkConnectUtil.buildAZkConnection();
    }

    @Bean
    @ConditionalOnBean(CuratorFramework.class)
    public ZkRegisterCenter createRegister(){
        log.info("------创建服务注册组件");
        return new ZkRegisterCenter();
    }

    @Bean
    @ConditionalOnBean(CuratorFramework.class)
    public ZkDiscoverCenter createDiscover(){
        log.info("------创建服务发现组件");
        return new ZkDiscoverCenter();
    }
}
