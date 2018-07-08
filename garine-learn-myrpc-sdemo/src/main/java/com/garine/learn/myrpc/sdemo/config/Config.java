package com.garine.learn.myrpc.sdemo.config;

import com.garine.learn.myrpc.RpcRelateClass;
import com.garine.learn.myrpc.annotation.MyRpcClient;
import org.reflections.Reflections;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.util.Set;

//@Configuration
//@Primary
public class Config{

    @Bean
    @ConditionalOnClass(Reflections.class)
    public RpcRelateClass createRpcClassDef(){
        Reflections reflections = new Reflections("com.garine.learn");
        Set<Class<?>> rpcClientClasses = reflections.getTypesAnnotatedWith(MyRpcClient.class);//启动时扫描不到注解修饰的类

        boolean useDefaultFilters = false;
        String basePackage = "com.garine.learn";
        ClassPathScanningCandidateComponentProvider beanScanner = new ClassPathScanningCandidateComponentProvider(useDefaultFilters);
        TypeFilter myClientFilter = new AnnotationTypeFilter(MyRpcClient.class);
        TypeFilter myServerFilter = new AnnotationTypeFilter(MyRpcClient.class);
        beanScanner.addIncludeFilter(myClientFilter);
        beanScanner.addIncludeFilter(myServerFilter);
        Set<BeanDefinition> beanDefinitions = beanScanner.findCandidateComponents(basePackage);

        RpcRelateClass relateClass = new RpcRelateClass();
        return relateClass;
    }

}
