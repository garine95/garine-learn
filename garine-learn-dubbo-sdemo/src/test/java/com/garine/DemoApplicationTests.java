package com.garine;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.remoting.Transporter;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.assertj.core.internal.cglib.core.DebuggingClassWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() throws IllegalAccessException, InstantiationException, IOException, NotFoundException, CannotCompileException {
/*        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        //设置将cglib生成的代理类字节码生成到指定位置
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\Temp");*/
//        Transporter t = ExtensionLoader.getExtensionLoader(Transporter.class).getAdaptiveExtension();
//		Class clzzz = t.getClass();
//        CtClass ctClass = ClassPool.getDefault().get
//        FileOutputStream fo = new FileOutputStream(new File("D://ad.class"));
//        fo.write(ctClass.toBytecode());
//        fo.close();
    }



}
