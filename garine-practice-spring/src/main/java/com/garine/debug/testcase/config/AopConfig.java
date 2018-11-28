package com.garine.debug.testcase.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AopConfig {

    @Pointcut("execution(* com.garine.debug.testcase.model.AopObject..*(..))")
    public void mypoint(){
        //切面定义
    }

    @Before("mypoint()")
    public void doAround() throws Throwable {
        System.out.println("before logic");
    }
}
