package com.example.gupaolearn.rpc.bean;

import lombok.Data;
import org.springframework.beans.factory.FactoryBean;

/**
 * 类bean工厂，只支持T传入非接口class类创建bean，对于我们动态创建的代理类没办法支持创建bean
 * @author zhoujy
 * @date 2018年07月10日
 **/
@Data
public class ActualFactoryBean<T> implements FactoryBean<T> {
    private Class<T> myClass;

    /**
     * 新建bean
     * @return
     * @throws Exception
     */
    @Override
    public T getObject() throws Exception {
        //利用反射具体的bean新建实现，不支持T为接口。
        return (T) myClass.newInstance();
    }

    /**
     * 获取bean
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return myClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
