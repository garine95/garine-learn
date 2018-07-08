package com.garine.learn.redis.provider.model.entity;

import com.garine.learn.redis.provider.MyRpcClient;
import lombok.Data;

import java.io.Serializable;

@Data
@MyRpcClient
public class User implements Serializable{
    private String userName;

    private String sex;
}
