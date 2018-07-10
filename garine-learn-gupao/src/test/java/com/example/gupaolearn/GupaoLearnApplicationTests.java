package com.example.gupaolearn;

import com.example.gupaolearn.Util.CommonUtil;
import com.example.gupaolearn.rpc.BeanUtil;
import com.example.gupaolearn.rpc.bean.DymicInvocationHandler;
import com.example.gupaolearn.rpc.bean.HelloServiceImpl;
import com.example.gupaolearn.rpc.bean.ActualFactoryBean;
import com.example.gupaolearn.rpc.bean.InterfaceFactoryBean;
import com.example.gupaolearn.rpc.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GupaoLearnApplicationTests {

	@Test
	public void testSimpleCreateBean() {
		BeanUtil.registryBean("helloServiceImpl", HelloServiceImpl.class);
		HelloService bean = (HelloService) BeanUtil.getBean(HelloServiceImpl.class);
		bean.sayHello("my word");

		Class s = HelloService.class;
	}

    @Test
    public void testFactoryCreateBean() {
        BeanUtil.registryBeanWithEdit("helloService", ActualFactoryBean.class, HelloServiceImpl.class);
        HelloService bean = (HelloService) BeanUtil.getBean("helloService");
        bean.sayHello("my word");
    }


    @Test
    public void testFactoryCreateDymicBean() {
        BeanUtil.registryBeanWithDymicEdit("helloService", InterfaceFactoryBean.class, HelloService.class, "这是bean注册时参数");
        HelloService bean = (HelloService) BeanUtil.getBean("helloService");
        String re = bean.sayHello("my word");
        CommonUtil.println("re-->"+re);
    }
}
