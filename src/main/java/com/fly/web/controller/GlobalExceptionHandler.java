package com.fly.web.controller;

import com.fly.web.constant.State;
import com.fly.web.exception.EmailException;
import com.fly.web.pojo.ResultDTO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.IOException;
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
    public ResultDTO noSuchAlgorithm(NoSuchAlgorithmException e )
    {
        ResultDTO model = ResultDTO.getInstance();


        /**
         * Do something
         */
        model.setMessage( State.RSA_NOT_FOUND.Tips() + e.getMessage() );
        model.setState( State.RSA_NOT_FOUND.value() );
        return(model);
    }


    /**
     * 邮件发送失败 异常处理
     * @param e
     * @return
     */
    @ExceptionHandler( EmailException.class )
    @ResponseBody
    public ResultDTO isIOException(EmailException e )
    {
        ResultDTO vc = ResultDTO.getInstance();


        /**
         * Do something
         */
        vc.setMessage( e.getMessage() );
        vc.setState(State.EMAIL_SERVICE_FAIL.value() );
        return(vc);
    }


    /**
     * Jedis 连接redis超时失败异常
     */
    @ExceptionHandler( JedisConnectionException.class )
    @ResponseBody
    public ResultDTO isConnectTimeout(JedisConnectionException e )
    {
        ResultDTO vc = ResultDTO.getInstance();


        /**
         * Do something
         */
        vc.setMessage( State.REDIS_NOT_STARTED.Tips() + e.getMessage() );
        vc.setState( State.REDIS_NOT_STARTED.value() );
        return(vc);
    }


    /**
     * 其他所有异常
     */
    @ExceptionHandler( Exception.class )
    @ResponseBody
    public ResultDTO exception(Exception e )
    {
        e.printStackTrace();
        ResultDTO vc = ResultDTO.getInstance();

        /**
         * Do something
         */
        vc.setMessage( State.INTERNAL_ERROR.Tips() + e.getMessage() );
        vc.setState( State.INTERNAL_ERROR.value() );
        return(vc);
    }
}
