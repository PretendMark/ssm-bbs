package com.fly.web.constant;

/**
 * @author YuLF
 * @version 1.0
 * @date 2020/10/14 17:34
 * 响应URL控制类
 */
public interface WebFinal {

    String LOGIN_FAILED = "redirect:/asset/user/public/login.jsp";
    String BASIC_SETTINGS_URL = "redirect:/asset/user/private/set.jsp#info";
    String REGISTER_PAGE_URL = "redirect:/asset/user/public/reg.jsp";
    String USER_LOGIN_SUCCESS = "redirect:/asset/user/private/index.jsp";


}
