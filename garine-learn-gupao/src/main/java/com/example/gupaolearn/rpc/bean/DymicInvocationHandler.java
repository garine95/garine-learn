package com.example.gupaolearn.rpc.bean;

import com.example.gupaolearn.Util.CommonUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author zhoujy
 * @date 2018年07月10日
 **/
public class DymicInvocationHandler implements InvocationHandler{
    private String params;

    public DymicInvocationHandler(String params){
        this.params = params;
    }

    /**
     * 可扩展处理点invoke
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (args.length > 0){
            CommonUtil.println("代理对象\n->方法"+method.getName()+
                    "\n->方法调用参数："+args[0].toString()+
                    "\n->bean注册时读取到参数："+params);
        }
        return "invocation return";
    }
}
