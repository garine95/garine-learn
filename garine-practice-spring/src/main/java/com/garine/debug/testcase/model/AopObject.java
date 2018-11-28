package com.garine.debug.testcase.model;

import org.springframework.stereotype.Component;

@Component
public class AopObject {

    public void aoped(){
        System.out.println("logic");
    }
}
