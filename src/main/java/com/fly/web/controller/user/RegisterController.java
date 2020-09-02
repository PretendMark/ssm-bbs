package com.fly.web.controller.user;

import com.fly.web.pojo.User;
import com.fly.web.service.serviceimpl.RegisterServiceimpl;
import com.fly.web.util.JedisPoolUtil;
import com.fly.web.util.RegisterUtil;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * 注册
 */
@Controller
@RequestMapping( "/ureg" )
public class RegisterController {
    @Autowired
    private RegisterServiceimpl service;


    /**
     *
     * @param user      用户昵称、用户明文密码、用户验证码、用户邮件用户名
     * @param binding   验证结果
     * @return
     */
    @RequestMapping( "/register" )
    public String register( @Valid User user, BindingResult binding, Model model, HttpServletRequest req )
    {
        /*
         * 后端数据效验步骤 > 实体类注解效验 > 验证效验结果 > 页面回显效验错误数据
         * 如果验证结果有错
         * 如果任一字段效验不通过
         */
        if ( binding.hasErrors() )
        {
            return("redirect:/asset/user/public/reg.jsp");
            /* 初步服务端数据效验通过 */
        }else{
            /* 判断token是否一致，一致则进入Shiro密码加密存数据库阶段 */
            JedisPool	jedisPool	= JedisPoolUtil.getJedisPoolInstance();
            @Cleanup Jedis	jedis		= jedisPool.getResource();
            /* 如果redis里面的token一致 */
            if (jedis.get( user.getUserEmail() + ":token" )!=null && jedis.get( user.getUserEmail() + ":token" ).equals( user.getUserToken() ) )
            {
                /* 把用户IP地址获得 */
                user.setUserCity( RegisterUtil.getIpAddress( req ) );
                /* 保存至数据库 */
                int i = service.saveUser( user );
                return("redirect:/asset/user/public/login.jsp");
            }
            return("redirect:/asset/user/public/reg.jsp");
        }
    }
}
