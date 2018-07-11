package com.garine.learn.myrpc.registry;

/**
 * 服务发现接口，可以多种方式实现，目前只有zk
 */
public interface DiscoverCenter {

    ServiceInfo discover(ServiceInfo serviceInfo) throws Exception;
}
