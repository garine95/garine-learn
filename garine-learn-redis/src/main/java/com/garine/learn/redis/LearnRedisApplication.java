package com.garine.learn.redis;

import com.garine.learn.redis.provider.lock.config.annotation.EnableDistributedLockConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDistributedLockConfig
public class LearnRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnRedisApplication.class, args);
	}
}
