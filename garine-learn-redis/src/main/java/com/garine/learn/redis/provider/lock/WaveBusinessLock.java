package com.garine.learn.redis.provider.lock;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author zhoujy
 * @date 2018年04月20日
 **/
@Slf4j
@Component
public class WaveBusinessLock {
    private static final Map<String, Map<String, String>> WAVE_LOCK_MAP = Maps.newConcurrentMap();

    private static final String WAVE_LOCK_PREFIX = "wave.";

    @Resource
    private DistributedLockManager distributedLockManager;

    /**
     *
     * @param toLocKeys 要求同时成功，同时失败的keys
     * @return
     */
    public String toLock(List<String> toLocKeys){
        String key = UUID.randomUUID().toString();
        Map<String, String> presentLocks = Maps.newHashMap();
        WAVE_LOCK_MAP.put(key, presentLocks);
        for (String toLocKey : toLocKeys) {
            String lockKey = WAVE_LOCK_PREFIX + toLocKey;
            String lockCode = distributedLockManager.tryLock(lockKey, 10*60);
            if (StringUtils.isEmpty(lockCode)){
                for (int i = 1;i <= 3;i++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        log.warn("Inventory Match Interrupted!", e);
                        // Restore interrupted state...
                        Thread.currentThread().interrupt();
                    }
                    lockCode = distributedLockManager.tryLock(lockKey, 10*60);
                    if (!StringUtils.isEmpty(lockCode)){
                        break;
                    }
                }
                if (!this.validator(key, lockCode, lockKey)){
                    return null;
                }
            }
            presentLocks.put(lockKey, lockCode);
        }
        return key;
    }

    /**
     * 校验能否获取到锁
     */
    private boolean validator(String key, String lockCode, String lockKey) {
        if (StringUtils.isEmpty(lockCode)) {
            this.unLock(key);
            String message = String.format("GenPick DistributeLock Exception : lockKey %s",
                    lockKey);
            log.warn(message);
            return false;
        }
        return true;
    }

    public void unLock(String presentLockKey) {
        Map<String, String> locked = WAVE_LOCK_MAP.get(presentLockKey);
        if (Objects.isNull(locked)){
            return;
        }
        for (Map.Entry<String, String> entry : locked.entrySet()) {
            this.distributedLockManager.unlock(entry.getKey(), entry.getValue());
        }
        WAVE_LOCK_MAP.remove(presentLockKey);
    }
}
