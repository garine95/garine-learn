package com.garine.common;

import com.garine.component.TestBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(basePackages = "com.garine.component")
public class MySpringApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册一个 总配置Class = MySpringApplication.class
        context.register(MySpringApplication.class);
        // 上下文启动,注册bean
        context.refresh();
        System.out.println(context.getBean(TestBean.class));
    }
}
