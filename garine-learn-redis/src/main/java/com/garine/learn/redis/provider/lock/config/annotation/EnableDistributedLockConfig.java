package com.garine.learn.redis.provider.lock.config.annotation;

import com.garine.learn.redis.provider.lock.config.DistributedLockConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用地址分布式锁配置
 *
 * @author zhoujy
 * @date 2018-03-29 20:09
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DistributedLockConfiguration.class)
@Documented
public @interface EnableDistributedLockConfig {
}
