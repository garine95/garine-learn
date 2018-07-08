package com.garine.learn.myrpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * 为什么需要原生io操作配置？因为postProcessBeanDefinitionRegistry方法执行优先级高于很多注解，它执行时很多注解根本还没执行，所以里面的对象都不能依赖spring来获取
 * Desc:properties文件获取工具类
 */
public class PropertiesUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    private static Properties props;

    static {
        loadProps();
    }
    synchronized static private void loadProps() {
        props = new Properties();
        InputStream in = null;
        try {
            in = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties");
            props.load(in);
        } catch (FileNotFoundException e) {
            logger.error("application.properties文件未找到");
        } catch (IOException e) {
            logger.error("出现IOException");
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("application.properties文件流关闭出现异常");
            }
        }
        logger.info("加载properties文件内容完成...........");
        logger.info("properties文件内容：" + props);
    }

    public static String getProperty(String key) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }
}