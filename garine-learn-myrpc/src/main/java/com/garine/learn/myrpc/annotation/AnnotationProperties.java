package com.garine.learn.myrpc.annotation;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "myrpc.an")
@Data
public class AnnotationProperties {
    /**
     * 服务基本包路径
     */
    private String basepackage = "com";
}
