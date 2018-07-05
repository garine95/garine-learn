package com.garine.learn.myrpc.registry;

import lombok.Data;

/**
 * 服务信息类
 */
@Data
public class ServiceInfo {
    private String serviceName;

    private String serviceVersion;

    private String hostName;

    private String port;

    public String getFullHost(){
        return hostName+":"+port;
    }

    public String getFullService(){
        return serviceName+serviceVersion;
    }

    public ServiceInfo(String hostName, String port){
        this.hostName = hostName;
        this.port = port;
    }
    public ServiceInfo(){

    }

}
