package com.garine;

import com.garine.source.analy.demo.transaction.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Resource
	TestService testService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void  testInsert(){
		testService.insertTest();
	}

}
