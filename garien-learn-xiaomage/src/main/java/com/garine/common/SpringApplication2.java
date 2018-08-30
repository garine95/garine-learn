package com.garine.common;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpringApplication2 {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringApplication2.class)
                .properties("server.port=0")//0表示随机获取端口
                .run(args);
        //fluent风格构建spring应用
    }
}
