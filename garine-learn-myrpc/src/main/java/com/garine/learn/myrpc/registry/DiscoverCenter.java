package com.garine.learn.myrpc.registry;

public interface DiscoverCenter {

    ServiceInfo discover(ServiceInfo serviceInfo) throws Exception;
}
