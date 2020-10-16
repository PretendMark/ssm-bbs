package com.fly.web.controller;

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

    @Resource(name = "systemConstant")
    public Properties systemConstant;

}
