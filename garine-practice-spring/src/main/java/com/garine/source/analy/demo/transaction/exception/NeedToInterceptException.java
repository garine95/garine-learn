package com.garine.source.analy.demo.transaction.exception;

/**
 * @author zhoujy
 * @date 2018年12月06日
 **/
public class NeedToInterceptException extends RuntimeException {
    public NeedToInterceptException(String msg){
        super(msg);
    }
}
