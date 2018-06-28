package com.garine.learn.mq.consumer.config;


import com.garine.learn.common.mq.conf.AbstractMqConfig;
import com.garine.learn.common.mq.constant.QueueConstant;
import com.garine.learn.common.mq.util.ReflectUtil;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * rabbitMq配置
 *
 * @author wugx
 * @date 2017/7/21 10:09
 */
@Configuration
public class RabbitMqConfig extends AbstractMqConfig {

    @PostConstruct
    public void init() throws IllegalAccessException {
        super.init(ReflectUtil.getFieldValues(QueueConstant.class));
    }

}
