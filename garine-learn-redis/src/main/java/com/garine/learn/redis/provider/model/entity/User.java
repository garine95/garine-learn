package com.garine.learn.redis.provider.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable{
    private String userName;

    private String sex;
}
