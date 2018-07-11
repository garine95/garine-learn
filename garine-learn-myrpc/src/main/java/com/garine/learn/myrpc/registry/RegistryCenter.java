package com.garine.learn.myrpc.registry;

/**
 * 服务注册接口，可以多种方式实现，目前只有zk
 */
public interface RegistryCenter {

    boolean registry(ServiceInfo serviceInfo);
}
