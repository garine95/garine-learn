package com.garine.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication
public class SpringApplication1 {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringApplication1.class);
        Map<String, Object> properties = new LinkedHashMap<>();
        properties.put("server.port", "8081");
        //设置启动参数
        springApplication.setDefaultProperties(properties);
        //运行spring应用并且获得上下文
        ConfigurableApplicationContext applicationContext = springApplication.run(args);
        System.out.println(applicationContext.getClass());
        System.out.println(applicationContext.getBean(SpringApplication1.class).getClass());
    }
}
