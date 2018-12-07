package com.garine.debug.testcase.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author zhoujy
 * @date 2018年11月27日
 **/
@Component
public class GaComponent {
    @Value("${garine}")
    private String name;

    @Autowired
    private GaObject gaObject;
}
