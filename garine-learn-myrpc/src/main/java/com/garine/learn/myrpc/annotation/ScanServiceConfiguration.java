package com.garine.learn.myrpc.annotation;

import com.garine.learn.myrpc.RpcRelateClass;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import javax.annotation.Resource;
import java.util.Set;

//@Bean注解方法的会执行创建bean
@Configuration
@EnableConfigurationProperties(value = {AnnotationProperties.class})
@ConditionalOnClass(value = {Reflections.class})
@Slf4j
public class ScanServiceConfiguration {
    @Resource
    private AnnotationProperties annotationProperties;

    @Bean
    @ConditionalOnClass(Reflections.class)
    public RpcRelateClass createRpcClassDef(){
        Reflections reflections = new Reflections(annotationProperties.getBasepackage());
        Set<Class<?>> rpcClientClasses = reflections.getTypesAnnotatedWith(MyRpcClient.class);
        Set<Class<?>> rpcServerClasses = reflections.getTypesAnnotatedWith(MyRpcServer.class);

        boolean useDefaultFilters = false;
        String basePackage = "com.garine.learn";
        ClassPathScanningCandidateComponentProvider beanScanner = new ClassPathScanningCandidateComponentProvider(useDefaultFilters);
        TypeFilter myClientFilter = new AnnotationTypeFilter(MyRpcClient.class);
        TypeFilter myServerFilter = new AnnotationTypeFilter(MyRpcClient.class);
        beanScanner.addIncludeFilter(myClientFilter);
        beanScanner.addIncludeFilter(myServerFilter);
        Set<BeanDefinition> beanDefinitions = beanScanner.findCandidateComponents(basePackage);//在这里娶不到类型信息

        RpcRelateClass relateClass = new RpcRelateClass();
        relateClass.setRpcClientClasses(rpcClientClasses);
        relateClass.setRpcServerClasses(rpcServerClasses);
        log.info("------预存rpc class配置");
        return relateClass;
    }
}
