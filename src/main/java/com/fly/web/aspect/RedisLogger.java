package com.fly.web.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Component
@Aspect
public class RedisLogger {

    private Logger log = Logger.getLogger(RedisLogger.class);

    @AfterReturning("execution(public void com.fly.web.util.JedisUtil.setVerify(..))")
    public void setVerifyLogger(JoinPoint joinPoint){
        Object[] objects = joinPoint.getArgs();
        System.out.println("切面参数："+ Arrays.asList(objects));
        log.info("在redis为： "+ objects[1] + "添加验证码："+objects[3]);
        log.info("在redis为： "+ objects[1] + "设置验证码过期时间："+objects[2]);
        log.info("在redis为： "+ objects[1] + "设置验证码重新获取时间："+objects[4]);
        log.info("在redis为： "+ objects[1] + "设置验证码重新获取时间的过期时间为："+objects[4]);
    }

}
