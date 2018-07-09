package com.garine;

import com.garine.dubbo.api.HelloService;
import com.garine.dubbo.api.HessianService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Resource
	private HelloService helloService;

	@Resource
	private HessianService hessianService;

	@Test
	public void contextLoads() {
		helloService.sayhi("我是客户端");
	}

	@Test
	public void testHessian() {
		helloService.sayhi("我是客户端");
	}

}
