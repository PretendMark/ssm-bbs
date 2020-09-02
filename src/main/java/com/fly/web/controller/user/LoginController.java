package com.fly.web.controller.user;

import com.fly.web.pojo.UserLogin;
import com.fly.web.util.Encryption;
import com.fly.web.util.JedisPoolUtil;
import lombok.Cleanup;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * 登录
 */
@RequestMapping( "/ulogin" )
@Controller
public class LoginController {
    private static final String LOGIN_FAILED = "redirect:/asset/user/public/login.jsp";


    /**
     * 登录验证
     * @param user      用户登录提交的信息
     * @param result    用户登录信息效验结果
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping( "/login" )
    public String login( @Valid UserLogin user, BindingResult result, HttpServletRequest req ) throws Exception
    {
        Subject currentUser = SecurityUtils.getSubject();
        /* 如果当前用户还没有验证 */
        if ( !currentUser.isAuthenticated() )
        {
            /* 连接池获取一个jedis连接 */
            JedisPool	jedisPool	= JedisPoolUtil.getJedisPoolInstance();
            @Cleanup Jedis	jedis		= jedisPool.getResource();
            /* 从redis取出私钥 */
            String privateKey = jedis.get( user.getUserEmail() + ":" + user.getUserVerifyCode() + ":privateKey" );
            /* 如果后端提交数据验证有错 或者 redis已经不存在这个邮箱的解密私钥了 */
            if ( result.hasErrors() || privateKey == null )
            {
                /* 如果有错 把错误信息存到Session 重定向登录页面显示 */
                req.getSession().setAttribute( "loginError", "抱歉!数据效验失败。" );
                return(LOGIN_FAILED);
            }
            String password = Encryption.decrypt( user.getUserPassword(), privateKey );
            /* 使用私钥解密 并把解密之后的密码交给Shiro验证 */
            UsernamePasswordToken token = new UsernamePasswordToken( user.getUserEmail(), password );
            token.setRememberMe( true );
            try {
                currentUser.login( token );
            } catch ( UnknownAccountException uae ) {
                req.getSession().setAttribute( "loginError", "抱歉!没有这个账户" );
                return(LOGIN_FAILED);
            } catch ( IncorrectCredentialsException ice ) {
                req.getSession().setAttribute( "loginError", "抱歉!账户或密码错误" );
                return(LOGIN_FAILED);
            } catch ( LockedAccountException lae ) {
                req.getSession().setAttribute( "loginError", "抱歉!账户已被锁定" );
                return(LOGIN_FAILED);
            } catch ( AuthenticationException ae ) {
                /* unexpected condition?  error? */
                ae.printStackTrace();
                req.getSession().setAttribute( "loginError", "抱歉：" + ae.getMessage() );
                return(LOGIN_FAILED);
            }
        }
        /* 重定向到数据分页功能 */
        return("redirect:/user/getquestion");
    }


    /**
     * 清空 登录失败重定向登录页面 设置到Session里面的错误信息
     * @param request
     */
    @RequestMapping( "/removeLoginError" )
    public void removeLoginError( HttpServletRequest request )
    {
        request.getSession().removeAttribute( "loginError" );
    }


    /**
     * 退出
     * @param request
     */
    @RequestMapping( "/logout" )
    public void logout( HttpServletRequest request )
    {
        SecurityUtils.getSubject().logout();
    }
}
