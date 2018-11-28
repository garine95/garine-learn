package com.garine.source.analy.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClassPathXmlContextTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:contextTest.xml");
        System.out.println(context.getBean("people"));
    }
}
