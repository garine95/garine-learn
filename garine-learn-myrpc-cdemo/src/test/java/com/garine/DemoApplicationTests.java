package com.garine;

import com.garine.learn.myrpc.api.HelloService;
import org.apache.curator.framework.CuratorFramework;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Resource
	CuratorFramework curatorFramework;

	@Resource
	private HelloService helloService;

	@Test
	public void contextLoads() throws Exception {
		String re = helloService.sayHi("this is from client");
		System.out.println("----------"+re);
	}

	@Test
	public void getChild() throws Exception {
		List<String> serhosts = curatorFramework.getChildren().forPath("/com.garine.learn.myrpc.client.service.HelloServiceRemote");
	}

}
