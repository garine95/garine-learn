package com.garine.learn.redis;

import com.garine.learn.redis.provider.lock.DistributedLockManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LearnRedisApplication.class)
public class RedisTests {

	@Resource
	private DistributedLockManager distributedLockManager;
	@Test
	public void testLock() {
		String lockCode = distributedLockManager.tryLock("testKey", 60);

		String lockCode2 =distributedLockManager.tryLock("testKey", 60);
		if (StringUtils.isEmpty(lockCode2) && !StringUtils.isEmpty(lockCode)){
			System.out.print("lock ok");
		}
	}

}
