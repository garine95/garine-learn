package com.garine.learn.redis.provider.lock;

/**
 * 分布式锁的服务提供者。此类是线程安全的。
 * 在使用前比如先调用 <code>setJedisCluster</code>
 * 进行初始化。
 * 在使用过程中可以随时调用 <code>setEnabled</code>方法来启用或者禁用，如果设置不启用，那么该方法将不调用
 * redis 的锁。
 *
 * @author liuhc
 * @data 2017年08月03日
 **/

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Random;

public class DistributedLockManager {

    /**
     * 如果是已经取消锁，那么返回此值
     */
    private static final String LOCK_DISABLED_VALUE = "lock_disabled";
    private final Log logger = LogFactory.getLog(getClass());
    /**
     * 所有缓存的 key 加上此前缀
     */
    private final String KEY_PREFIX = "ful:wms:lock.";
    /**
     * 是否启用
     */
    private volatile boolean enabled = true;
    /**
     * 默认超时时间，单位为秒
     */
    private volatile long timeout = 5;
    /**
     * 注入RedisTemplate实例
     */
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 尝试获取指定锁，并设置锁超时时间。
     * 此方法立即返回无论是否能否成功获取锁。
     *
     * @param lockKey     锁的键
     * @param lockTimeout 锁的超时时间，单位为秒
     * @return 如果成功返回一个随机的字符串，解锁的时候需要传入此值来解锁，获取锁失败时立即返回并返回 null
     */
    public String tryLock(final String lockKey, final long lockTimeout) {
        checkLockKey(lockKey);
        checkTimeout(lockTimeout);
        final String key = KEY_PREFIX + lockKey;
        if (enabled && redisTemplate != null) {
            final String lockValue = randomString();
            return redisTemplate.execute((RedisCallback<String>) redisConnection -> {
                Boolean success = redisConnection.setNX(key.getBytes(), lockValue.getBytes());
                if(success){
                    redisConnection.expire(key.getBytes(), lockTimeout);
                    return lockValue;
                }
                return null;
            });
        }
        return LOCK_DISABLED_VALUE;
    }

    /**
     * 进行解锁。
     *
     * @param lockKey   锁定时设定的键
     * @param lockValue 获取锁时取得的锁值
     * @return 返回true表示成功解锁，返回false表示锁已失效或不存在此锁。
     */
    public boolean unlock(String lockKey, final String lockValue) {
        checkLockKey(lockKey);
        if (enabled && redisTemplate != null) {
            final String key = KEY_PREFIX + lockKey;
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    byte[] valueStr = redisConnection.get(key.getBytes());
                    return valueStr != null && (new String(valueStr).equals(lockValue)) && redisConnection.del(key
                            .getBytes()) > 0;
                }
            });
        }
        return false;
    }

    /**
     * 尝试获取指定锁，并设置锁超时时间。
     * 此方法立即返回无论是否能否成功获取锁。
     *
     * @param lockKey 锁的键
     * @return 如果成功返回一个随机的字符串，解锁的时候需要传入此值来解锁，获取锁失败时立即返回并返回 null
     */
    public String tryLock(String lockKey) {
        return tryLock(lockKey, timeout);
    }


    private String randomString() {
        Random random = new Random();
        return System.currentTimeMillis() + "" + random.nextInt(100000);
    }

    private void checkLockKey(String key) {
        if (key == null || key.isEmpty()) {
            throw new RuntimeException("锁的键不能为空");
        }
    }

    private void checkTimeout(long timeout) {
        if (timeout < 0) {
            throw new RuntimeException("锁的超时时间必须不小于0");
        }
    }
}
