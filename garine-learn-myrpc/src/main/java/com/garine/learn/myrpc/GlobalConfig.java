package com.garine.learn.myrpc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "myrpc.global")
@Data
public class GlobalConfig {
    /**
     * registry protocol
     */
    private String registryprotocol = "zookeeper";


    private String FRAMEWORK_SPACE = "myrpc";
}
