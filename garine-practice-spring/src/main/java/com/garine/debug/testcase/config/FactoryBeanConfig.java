package com.garine.debug.testcase.config;

import com.garine.debug.testcase.model.GaFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhoujy
 * @date 2018年11月27日
 **/
@Configuration
public class FactoryBeanConfig {
    @Bean
    public GaFactoryBean gaFactoryBean(){
        return new GaFactoryBean();
    }
}
