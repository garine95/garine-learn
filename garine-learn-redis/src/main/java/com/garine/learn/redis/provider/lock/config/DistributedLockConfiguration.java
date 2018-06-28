package com.garine.learn.redis.provider.lock.config;

import com.garine.learn.redis.provider.lock.DistributedLockManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author zhoujy
 * @date 2018-04-20 20:06
 */
public class DistributedLockConfiguration {

    @Bean
    @ConditionalOnClass({RedisTemplate.class})
    public DistributedLockManager landmarkComponent() {
        return new DistributedLockManager();
    }

}
