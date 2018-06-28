package com.garine.learn.redis.provider.config;

import com.github.pagehelper.PageHelper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * @author huanggh
 * @since 2016年8月10日
 * MyBatis扫描接口，使用的tk.mybatis.spring.mapper.MapperScannerConfigurer，
 * 如果你不使用通用Mapper，可以改为org.xxx...
 */
@Configuration
public class MyBatisMapperScannerConfig {
    /**
     * 分页插件
     */
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties pageProperties = new Properties();
        pageProperties.setProperty("reasonable", "true");
        pageProperties.setProperty("supportMethodsArguments", "true");
        pageProperties.setProperty("returnPageInfo", "check");
        pageProperties.setProperty("params", "count=countSql");
        pageHelper.setProperties(pageProperties);

        return pageHelper;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.garine.learn.redis.provider.dao");

        Properties properties = new Properties();
        properties.setProperty("mappers", "com.garine.learn.common.provider.dao.BaseMapper");
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY", "MYSQL");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }
}