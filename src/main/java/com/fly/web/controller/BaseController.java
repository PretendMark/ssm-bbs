package com.fly.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * @author YuLF
 * @version 1.0
 * @date 2020/10/16 14:13
 * BBS所有类得基类
 */
@Component
public class BaseController {

    @Autowired
    @Qualifier(value = "systemConstant")
    public Properties systemConstant;

}
