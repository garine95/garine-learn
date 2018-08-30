package com.garine.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 如何推断一个spring应用是web的？可以手动设置
 */
@SpringBootApplication
public class SpringApplication3 {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringApplication3.class);
        Map<String, Object> properties = new LinkedHashMap<>();
        properties.put("server.port", "8081");
        //设置启动参数
        springApplication.setDefaultProperties(properties);
        //springApplication.setWebApplicationType(WebApplicationType.NONE);
        //运行spring应用并且获得上下文
        ConfigurableApplicationContext applicationContext = springApplication.run(args);
        System.out.println(applicationContext.getClass());
    }
}
