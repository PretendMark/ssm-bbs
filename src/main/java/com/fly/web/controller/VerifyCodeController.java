package com.fly.web.controller;

import com.fly.web.email.EmailUtil;
import com.fly.web.exception.EmailException;
import com.fly.web.pojo.VerifyCode;
import com.fly.web.service.serviceimpl.RegisterServiceimpl;
import com.fly.web.util.*;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.servlet.http.HttpServletRequest;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;


/**
 * 这个类为邮箱验证用户的有效性
 */
@Controller
@RequestMapping( "/verifyCode" )
public class VerifyCodeController {
    private static JedisPool jedisPool = null;

    @Autowired
    private RegisterServiceimpl service;

    @Autowired
    private EmailUtil emailUtil;

    private static final String EMAIL_CONFIG = "email-verify.properties";
    static {
        /* 在一台redis的情况下，类加载时从池中获取一次Pool实例 */
        jedisPool = JedisPoolUtil.getJedisPoolInstance();
    }


    /**
     * 发送邮件验证码和重发邮件验证码的判断
     * @param email
     * @return
     * @throws EmailException
     * @throws JedisConnectionException
     */
    @RequestMapping( value = "/getVerifyCode" )
    public @ResponseBody VerifyCode getVerifyCode( @RequestParam(value = "email", required = true) String email ) throws EmailException, JedisConnectionException
    {
        @Cleanup Jedis	jedis	= jedisPool.getResource();
        VerifyCode	vc	= new VerifyCode();
        /* 0.此邮件在数据库是否注册过 */
        if ( service.isExistUser( email ) )
        {
            vc.setState( 205 );
            vc.setMessage( "对不起，同一邮箱只能绑定一个账户!" );
            return(vc);
        }


        /**
         * 验证用户昵称重复？
         */


        /* 1.今天发送邮件是否超过3次; 203 */
        if ( JedisUtil.EmailSendExceedThreeTimes( jedis, email, CharacterUtil.parseInt( PropertiesUtil.getProperty( "verify.day.count", EMAIL_CONFIG ), 3 ) ) )
        {
            vc.setState( 203 );
            vc.setMessage( "Sorry, the number of times to send captcha today has reached the limit, please come back tomorrow" );
            return(vc);
        }
        /* 2.redis是否有验证冷却时间，如果有则返回202-验证时间还未完 */
        int verifyCodeCooling = (int) JedisUtil.getVerifyCooling( jedis, email );
        if ( verifyCodeCooling > 1 )
        {
            vc.setState( 202 );
            vc.setTime( verifyCodeCooling );
            vc.setMessage( "Sorry, Please send again in " + verifyCodeCooling + " seconds" );
            return(vc);
        }

        /* 4.上面的条件都不成立、发送邮件， */


        /**
         * 得保证这个邮件发送验证码完全没问题
         * 不然用户收不到
         */
        String verifyCode = CharacterUtil.getSpecifyRandomString( 4 ); /* 生成的验证码 */
        /* 发送邮件 */
        Boolean isSendSucces = emailUtil.sendMail( email,
                PropertiesUtil.getProperty( "mail.head.title", EMAIL_CONFIG ),
                PropertiesUtil.getProperty( "mail.body.content", EMAIL_CONFIG ),
                verifyCode );

        /* 发送邮件成功 */
        if ( isSendSucces )
        {
            /* 获取properties配置文件中的验证码重写获取间隔 冷却时间 */
            int verifyCodeCoolingTime = CharacterUtil.parseInt( PropertiesUtil.getProperty( "verify.cooling", EMAIL_CONFIG ), 60 );
            /* 验证码消失时间 默认360s */
            int verifyCodeTime = CharacterUtil.parseInt( PropertiesUtil.getProperty( "verify.code.time", EMAIL_CONFIG ), 360 );
            /* 把验证码消失时间 、验证码 和 验证码重新获取时间 存入redis */
            JedisUtil.setVerify( jedis, email, verifyCodeTime, verifyCode, verifyCodeCoolingTime );
            vc.setMessage( "Send verification code to specified email successfully!" );
            vc.setTime( verifyCodeCoolingTime );
            vc.setState( 200 );
            return(vc);
        } else {
            /* 发送邮件出现异常 */
            JedisUtil.RollbackVerifyCodeDayCount( jedis, email );
            throw new EmailException( "发送邮件验证失败!.请联系管理员查看邮箱配置和日志!" );
        }
    }


    @RequestMapping( "/emailverify" )
    @ResponseBody
    public String EmailVerify()
    {
        String enabled = PropertiesUtil.getProperty( "verify.enabled", "email-verify.properties" );
        if ( CharacterUtil.parseBoolean( enabled ) )
        {
            return("{\"emailverify\":\"true\"}");
        }
        return("{\"emailverify\":\"false\"}");
    }


    /**
     * 注册验证
     * 验证验证码是否正确(邮件验证码或普通验证码)
     * @param ClientValidateCode
     * @param userEmail
     * @param req
     * @return
     */
    @RequestMapping( "/regVerify" )
    @ResponseBody
    public VerifyCode regVerify( @RequestParam("v") String ClientValidateCode, @RequestParam("e") String userEmail, HttpServletRequest req )
    {
        VerifyCode vc = new VerifyCode();
        if ( service.isExistUser( userEmail ) )
        {
            vc.setState( 205 );
            vc.setMessage( "对不起，同一邮箱只能绑定一个账户!" );
            return(vc);
        }
        @Cleanup Jedis jedis = jedisPool.getResource();
        /* 验证码是正确的吗 */
        Boolean isCorrect = false;
        /* 如果邮件验证码开启的就先验证邮件验证码是否正确 */
        String emailVerifyIsEnabled = EmailVerify();
        if ( emailVerifyIsEnabled.contains( "true" ) )
        {
            /* 如果邮件验证码正确 */
            if ( jedis.get( userEmail + ":verifycode" ).equalsIgnoreCase( ClientValidateCode ) )
            {
                /* 成功后删出redis的邮件验证码,避免第二次注册验证码不发送也能成功 */
                jedis.del( userEmail + ":verifycode" );
                isCorrect = true;
            }
        } else {
            /* 邮箱验证没有被开启，直接验证普通的验证码 */
            isCorrect = isCorrectVerifyCode( ClientValidateCode, req );
        }
        if ( isCorrect )
        {
            /* 验证码一致 */
            vc.setState( 300 );
            vc.setMessage( "验证码一致!" );
            /* 获取一个16位的随机token */
            String token = CharacterUtil.getSpecifyRandomString( 16 );
            vc.setToken( token );
            /* 存入redis并设置60秒有效时间 */
            jedis.set( userEmail + ":token", token );
            jedis.expire( userEmail + ":token", 60 );
        } else {
            vc.setState( 301 );
            vc.setMessage( "验证码不一致，验证失败" );
        }
        return(vc);
    }


    @RequestMapping( "/loginVerify" )
    @ResponseBody
    public VerifyCode loginVerify( @RequestParam("v") String ClientValidateCode, @RequestParam("e") String userEmail, HttpServletRequest req ) throws NoSuchAlgorithmException
    {
        VerifyCode vc = new VerifyCode();

        /* 验证码不一致 */
        if ( !isCorrectVerifyCode( ClientValidateCode, req ) )
        {
            vc.setMessage( "验证码不一致!验证失败" );
            vc.setState( 301 );
            return(vc);
        }
        /* 用户不存在 */
        if ( !service.isExistUser( userEmail ) )
        {
            vc.setState( 304 );
            vc.setMessage( "此用户不存在!" );
            return(vc);
        }
        /* 生成RSA公钥与私钥、公钥给前端密码加密 */
        Map<Integer, String> rsa = Encryption.getRSA();
        vc.setState( 302 );
        vc.setMessage( "验证码正确!核实密码中..." );
        vc.setToken( rsa.get( 0 ) );
        /* 把私钥给redis存起来60秒，60秒之内用户没登录，此次密码无效（可能出现第三方登录的情况） */
        @Cleanup Jedis jedis = jedisPool.getResource();
        jedis.set( userEmail + ":" + ClientValidateCode + ":privateKey", rsa.get( 1 ) );
        jedis.expire( userEmail + ":" + ClientValidateCode + ":privateKey", 60 );
        return(vc);
    }


    public Boolean isCorrectVerifyCode( String ClientValidateCode, HttpServletRequest req )
    {
        /* 邮箱验证没有被开启，直接验证普通的验证码 */
        String SessionValidateCode = (String) req.getSession().getAttribute( KAPTCHA_SESSION_KEY ); /* 获取服务端验证码 */
        if ( ClientValidateCode.equalsIgnoreCase( SessionValidateCode ) )
        {
            return(true);
        }
        return(false);
    }
}
