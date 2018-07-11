package com.garine.learn.myrpc;

import com.garine.learn.myrpc.annotation.MyRpcClient;
import com.garine.learn.myrpc.annotation.MyRpcServer;
import com.garine.learn.myrpc.client.RpcClientProxyFactory;
import com.garine.learn.myrpc.registry.ServiceInfo;
import com.garine.learn.myrpc.registry.zookeeper.ZkDiscoverCenter;
import com.garine.learn.myrpc.registry.zookeeper.ZkRegisterCenter;
import com.garine.learn.myrpc.server.RpcServerPublisher;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.Set;

/**
 * com.example.gupaolearn.rpc.BeanUtil中有优化实现
 * @author zhoujy
 */
@Component
public class BeanRegistryUtil implements ApplicationContextAware,BeanDefinitionRegistryPostProcessor {
    private static ApplicationContext ctx;

    private ZkDiscoverCenter zkDiscoverCenter = new ZkDiscoverCenter();

    private ZkRegisterCenter zkRegisterCenter = new ZkRegisterCenter();


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    /**
     * 将动态生成的客户端访问代理对象加入spring容器，对象封装了网络请求操作
     * 先于@Configuration @Bean @Value等执行，里面涉及的对象都只能用原生方法操作
     * @param beanDefinitionRegistry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        Reflections reflections = new Reflections(PropertiesUtil.getProperty("myrpc.an.basepackage"));
        Set<Class<?>> rpcClientClasses = reflections.getTypesAnnotatedWith(MyRpcClient.class);
        Set<Class<?>> rpcServerClasses = reflections.getTypesAnnotatedWith(MyRpcServer.class);
        registryClientProxyBean(beanDefinitionRegistry, rpcClientClasses, reflections);
        registryServiceToCenter(rpcServerClasses, reflections);
    }

    private void registryServiceToCenter(Set<Class<?>> rpcServerClasses, Reflections reflections) {
        Iterator<Class<?>> it = rpcServerClasses.iterator();
        while (it.hasNext()){
            Class<?> cls = it.next();
            if (!cls.isInterface()){
                continue;
            }
            Set sub = reflections.getSubTypesOf(cls);
            if (CollectionUtils.isEmpty(sub)){
                //没有实现类则不发布服务
                continue;
            }
            //全类名
            String serviceName = cls.getName();
            //当前服务器ip
            String host = "127.0.0.1";
            //服务发布端口
            String port = "8888";
            ServiceInfo serviceInfo = new ServiceInfo();
            serviceInfo.setServiceName(serviceName);
            serviceInfo.setPort(port);
            serviceInfo.setHostName(host);
            zkRegisterCenter.registry(serviceInfo);
            RpcServerPublisher.handlerMap.put(serviceInfo.getFullService(), serviceInfo);
        }
    }

    private void registryClientProxyBean(BeanDefinitionRegistry beanDefinitionRegistry, Set<Class<?>> rpcClientClasses, Reflections reflections) {
        Iterator<Class<?>> it = rpcClientClasses.iterator();
        while (it.hasNext()){
            Class cls = it.next();
            if (!cls.isInterface()){
                continue;
            }
            Set sub = reflections.getSubTypesOf(cls);
            if (!CollectionUtils.isEmpty(sub)){
                //已经有实现类则不创建代理
                continue;
            }
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(cls);
            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
            definition.getPropertyValues().add("interfaceClass", definition.getBeanClassName());
            definition.getPropertyValues().add("serviceInfo", zkDiscoverCenter.discover(ServiceInfo.build(cls)));
            definition.setBeanClass(RpcClientProxyFactory.class);
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            beanDefinitionRegistry.registerBeanDefinition(getSerName(cls.getSimpleName()), definition);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext ctxx) throws BeansException {
        ctx = ctxx;
    }

    /**
     * 根据class信息，在spring容器中获取相应的bean，例如我们的服务实现类
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz){
        return ctx.getBean(clazz);
    }

    private String getSerName(String str){
        String first = str.substring(0,1).toLowerCase();
        if (str.length() > 1){
            return first + str.substring(1);
        }else {
            return first;
        }
    }
}

