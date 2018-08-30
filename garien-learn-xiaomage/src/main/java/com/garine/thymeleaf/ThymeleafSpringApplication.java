package com.garine.thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.garine.thymeleaf")
public class ThymeleafSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThymeleafSpringApplication.class, args);
    }
}
