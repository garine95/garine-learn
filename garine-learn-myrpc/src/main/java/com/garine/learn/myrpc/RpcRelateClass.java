package com.garine.learn.myrpc;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RpcRelateClass {
    private Set<Class<?>> rpcClientClasses = new HashSet<>();

    private Set<Class<?>> rpcServerClasses = new HashSet<>();
}
