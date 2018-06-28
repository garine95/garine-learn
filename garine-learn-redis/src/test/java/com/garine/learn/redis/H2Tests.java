package com.garine.learn.redis;

import com.garine.learn.redis.provider.dao.UserMapper;
import com.garine.learn.redis.provider.model.entity.User;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LearnRedisApplication.class)
@ActiveProfiles("utest")
@ContextConfiguration(locations = "classpath:dataSource.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
public class H2Tests {

	@Resource
	private UserMapper userMapper;

	@Test
	@DatabaseSetup(value = "classpath:/com/learn/data/base/h2test/h2_test.xml")
	public void testLock() {
        User cri = new User();
        cri.setSex("M");

        List<User> userList = userMapper.select(cri);
		Assert.assertTrue(!CollectionUtils.isEmpty(userList));
	}

}
