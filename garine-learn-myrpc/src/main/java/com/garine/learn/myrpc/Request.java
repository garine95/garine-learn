package com.garine.learn.myrpc;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhoujy
 * @date 2018年07月06日
 **/
@Data
public class Request implements Serializable{

    private String serviceName;

    private String serviceVersion;

    private String methodName;

    private Object[] args;

    private String hostName;

    private Integer port;

}
