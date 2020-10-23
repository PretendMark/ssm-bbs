package com.fly.web.listener;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 接收请求得控制类
 */
@Controller
@RequestMapping("/test")
public class UserValidityDetection {


    //当用户进入某个页面时 存入用户得某些Session 值
    @RequestMapping("/")
    public String pageSkip(HttpServletRequest request){
        request.getSession().setAttribute("UserKey","this is test key");
        //当用户进入页面时，需要保存一次当前活跃时间，避免用户进入后马上退出页面，无法监听用户是否在页面得活跃状态
        checkUser(request);
        //跳转页面时存入测试 session
        return "test";
    }

    @RequestMapping("/returnSession")
    @ResponseBody
    public HttpSession returnSession(HttpServletRequest request){
        return request.getSession();
    }

    /**
     * 查看用户session是否存在
     * @param request
     * @return
     */
    @RequestMapping("/getUserKey")
    @ResponseBody
    public String getUserKey(HttpServletRequest request){
        String UserKey = (String) request.getSession().getAttribute("UserKey");
        //跳转页面时存入测试 session
        return "{\"UserKey\":"+UserKey+"}";
    }

    /**
     * 页面js每间隔多少秒请求一次，存入当前时间戳，标识用户打开浏览器中打开了当前页面
     * @param request
     */
    @RequestMapping("/checkUserActive")
    @ResponseBody
    public String checkUser( HttpServletRequest request){
        //存入当前Session用户得活跃时间
        request.getSession().setAttribute("userActiveTime",System.currentTimeMillis());
        return "{\"code\":200,\"message\":\"The page remains active and successful!\"}";
    }


}
