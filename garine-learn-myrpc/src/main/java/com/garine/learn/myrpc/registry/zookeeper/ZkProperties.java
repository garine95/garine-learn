package com.garine.learn.myrpc.registry.zookeeper;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "myrpc.zk")
@Data
public class ZkProperties {
    /**
     * zookeeper host.
     */
    private String host = "localhost";

    /**
     * zookeeper port.
     */
    private int port = 2181;
}
