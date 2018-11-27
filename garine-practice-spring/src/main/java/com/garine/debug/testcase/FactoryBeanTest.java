package com.garine.debug.testcase;

import com.garine.debug.testcase.model.GaComponent;
import com.garine.debug.testcase.model.GaObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author zhoujy
 * @date 2018年11月27日
 **/
@SpringBootApplication(scanBasePackages = "com.garine.debug.testcase")
public class FactoryBeanTest {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FactoryBeanTest.class, args);
        System.out.println(context.getBean(GaObject.class));
        System.out.println(context.getBean("&gaFactoryBean"));
        System.out.println(context.getBean(GaComponent.class));
    }
}
