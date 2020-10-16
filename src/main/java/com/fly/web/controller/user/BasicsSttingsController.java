package com.fly.web.controller.user;

import com.fly.web.constant.State;
import com.fly.web.constant.WebFinal;
import com.fly.web.pojo.ProvincialAndCityDO;
import com.fly.web.pojo.ResultDTO;
import com.fly.web.service.serviceimpl.BasicsSttingsServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * 基本设置菜单
 */
@Controller
@RequestMapping("/user")
public class BasicsSttingsController {
    @Autowired
    private BasicsSttingsServiceimpl basicsSttingsServiceimpl;


    @RequestMapping("/basicsettings")
    public String basicsettings() {
        return WebFinal.BASIC_SETTINGS_URL;
    }

    @RequestMapping("/updateinfo")
    public String updateinfo(@RequestParam("uid") String uid, HttpServletRequest request) {

        return WebFinal.BASIC_SETTINGS_URL;
    }

    @RequestMapping("/checkNickname")
    @ResponseBody
    public ResultDTO checkNickname(@RequestParam(value = "newNickname", required = false) String newNickname) {
        ResultDTO instance = ResultDTO.getInstance();
        if (null == newNickname) {
            instance.setState(State.NULL_ERROR.value());
            instance.setMessage(State.NULL_ERROR.Tips());
            return instance;
        }
        Boolean is = basicsSttingsServiceimpl.isExistNickname(newNickname);
        if (is) {
            instance.setState(State.USER_NICKNAME_EXIST.value());
            instance.setMessage(State.USER_NICKNAME_EXIST.Tips());
        } else {
            instance.setState(State.USER_NICKNAME_NOT_EXIST.value());
            instance.setMessage(State.USER_NICKNAME_NOT_EXIST.Tips());
        }
        return instance;
    }
}
