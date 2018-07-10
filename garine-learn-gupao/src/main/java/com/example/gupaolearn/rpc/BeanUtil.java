package com.example.gupaolearn.rpc;

import com.example.gupaolearn.rpc.bean.InterfaceFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * spring 手动创建、获取bean工具
 * @author zhoujy
 * @date 2018年07月10日
 **/
@Component
public class BeanUtil implements ApplicationContextAware,BeanDefinitionRegistryPostProcessor {
    private static ApplicationContext applicationContext;

    /**
     * 实例化时自动执行,通常用反射包获取到需要动态创建的接口类，容器初始化时，此方法执行，创建bean
     * 执行过程与registryBeanWithDymicEdit基本一致
     * @param beanDefinitionRegistry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        Class<?> beanClazz = null;//反射获取接口clazz
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClazz);
        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
        definition.getPropertyValues().add("interfaceClass", beanClazz);
        definition.getPropertyValues().add("params", "注册传入参数，一般是properties配置的信息");
        definition.setBeanClass(InterfaceFactoryBean.class);
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        beanDefinitionRegistry.registerBeanDefinition(beanClazz.getSimpleName(), definition);
    }

    /**
     * 实例化时自动执行
     * @param configurableListableBeanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    /**
     * BeanUtil实例化时自动注入applicationContext
     * @param applicationContextz
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContextz) throws BeansException {
        applicationContext = applicationContextz;
    }

    public static Object getBean(Class<?> clazz){
        return applicationContext.getBean(clazz);
    }

    public static Object getBean(String className){
        return applicationContext.getBean(className);
    }
    /**
     * 直接创建bean，不设置属性
     * @param beanId
     * @param clazz
     * @return
     */
    public static boolean registryBean(String beanId, Class<?> clazz){
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        BeanDefinition definition = builder.getBeanDefinition();
        getRegistry().registerBeanDefinition(beanId, definition);
        return true;
    }


    /**
     * 为已知的class创建bean，可以设置bean的属性，可以用作动态代理对象的bean扩展
     * @param beanId
     * @param
     * @return
     */
    public static boolean registryBeanWithEdit(String beanId, Class<?> factoryClazz, Class<?> beanClazz){
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClazz);
        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
        definition.getPropertyValues().add("myClass", beanClazz);
        definition.setBeanClass(factoryClazz);
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        getRegistry().registerBeanDefinition(beanId, definition);
        return true;
    }

    /**
     * 为已知的class创建bean，可以设置bean的属性，可以用作动态代理对象的bean扩展
     * @param beanId
     * @param
     * @return
     */
    public static boolean registryBeanWithDymicEdit(String beanId, Class<?> factoryClazz, Class<?> beanClazz, String params){
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClazz);
        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
        definition.getPropertyValues().add("interfaceClass", beanClazz);
        definition.getPropertyValues().add("params", params);
        definition.setBeanClass(factoryClazz);
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        getRegistry().registerBeanDefinition(beanId, definition);
        return true;
    }

    /**
     * 获取注册者
     * context->beanfactory->registry
     * @return
     */
    public static BeanDefinitionRegistry getRegistry(){
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
        return (DefaultListableBeanFactory)configurableApplicationContext.getBeanFactory();
    }
}
