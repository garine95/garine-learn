package com.garine.debug.testcase.model;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author zhoujy
 * @date 2018年11月27日
 **/
public class GaFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return new GaObject();
    }

    @Override
    public Class<?> getObjectType() {
        return GaObject.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
