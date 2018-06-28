package com.garine.learn.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.garine.learn"})
public class LearnMqApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnMqApplication.class, args);
	}
}
