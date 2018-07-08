package com.garine.learn.redis;

import com.garine.learn.redis.provider.MyRpcClient;
import com.garine.learn.redis.provider.lock.DistributedLockManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reflections.Reflections;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Set;

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

	@Test
	public void terstss(){
		boolean useDefaultFilters = false;
		String basePackage = "com.garine.learn.redis.provider.model.entity";
		ClassPathScanningCandidateComponentProvider beanScanner = new ClassPathScanningCandidateComponentProvider(useDefaultFilters);
		TypeFilter myClientFilter = new AnnotationTypeFilter(MyRpcClient.class);
		beanScanner.addIncludeFilter(myClientFilter);
		Set<BeanDefinition> beanDefinitions = beanScanner.findCandidateComponents(basePackage);

		Reflections reflections = new Reflections(basePackage);
		Set<Class<?>> rpcClientClasses = reflections.getTypesAnnotatedWith(MyRpcClient.class);
	}

}
