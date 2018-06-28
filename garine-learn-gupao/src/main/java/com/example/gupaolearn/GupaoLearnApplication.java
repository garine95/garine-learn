package com.example.gupaolearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.example.gupaolearn.*")
public class GupaoLearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(GupaoLearnApplication.class, args);
	}
}
