package com.fly.web.controller;

import com.fly.web.exception.EmailException;
import com.fly.web.pojo.ExceptionBody;
import com.fly.web.pojo.VerifyCode;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.mail.AuthenticationFailedException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.NoSuchAlgorithmException;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 文件读取异常
     * @param e
     * @return
     */
    @ExceptionHandler( IOException.class )
    public ModelAndView isIOException( IOException e )
    {
        ModelAndView model = new ModelAndView();


        /**
         * Do something
         */
        model.addObject( "exceptionMsg", e.getMessage() );
        model.setViewName( "other/exception" );
        return(model);
    }


    /**
     * RSA加密解密读取异常
     * @param e
     * @return
     */
    @ExceptionHandler( NoSuchAlgorithmException.class )
    @ResponseBody
    public ExceptionBody noSuchAlgorithm( NoSuchAlgorithmException e )
    {
        ExceptionBody model = new ExceptionBody();


        /**
         * Do something
         */
        model.setMessage( "加密算法找不到了。请联系管理员" + e.getMessage() );
        model.setState( 510 );
        return(model);
    }


    /**
     * 邮件发送失败 异常处理
     * @param e
     * @return
     */
    @ExceptionHandler( EmailException.class )
    @ResponseBody
    public VerifyCode isIOException( EmailException e )
    {
        VerifyCode vc = new VerifyCode();


        /**
         * Do something
         */
        vc.setMessage( e.getMessage() );
        vc.setState( 206 );
        return(vc);
    }


    /**
     * Jedis 连接redis超时失败异常
     */
    @ExceptionHandler( JedisConnectionException.class )
    @ResponseBody
    public VerifyCode isConnectTimeout( JedisConnectionException e )
    {
        VerifyCode vc = new VerifyCode();


        /**
         * Do something
         */
        vc.setMessage( "可能是管理员没有开启Redis，请联系管理员配置好Redis：" + e.getMessage() );
        vc.setState( 204 );
        return(vc);
    }


    /**
     * 其他所有异常
     */
    @ExceptionHandler( Exception.class )
    @ResponseBody
    public ExceptionBody exception( Exception e )
    {
        e.printStackTrace();
        ExceptionBody eb = new ExceptionBody();


        /**
         * Do something
         */
        eb.setMessage( "出了点情况：" + e.getMessage() );
        eb.setState( 500 );
        return(eb);
    }
}
