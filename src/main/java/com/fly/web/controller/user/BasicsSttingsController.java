package com.fly.web.controller.user;

import com.fly.web.constant.State;
import com.fly.web.constant.WebFinal;
import com.fly.web.controller.BaseController;
import com.fly.web.pojo.ResultDTO;
import com.fly.web.pojo.UserDO;
import com.fly.web.service.serviceimpl.BasicsSttingsServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * 基本设置菜单
 */
@Controller
@RequestMapping("/user")
public class BasicsSttingsController extends BaseController {
    @Autowired
    private BasicsSttingsServiceimpl basicsSttingsServiceimpl;

    @RequestMapping("/basicsettings")
    public String basicsettings() {
        return WebFinal.BASIC_SETTINGS_URL;
    }


    /**
     * 更新用户资料
     * @param user
     * @param bindingResult
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO updateUserInfo(@Validated(UserDO.updatePersonalDataGroup.class) UserDO user, BindingResult bindingResult, HttpServletRequest request) {
        ResultDTO resultDTO = ResultDTO.getInstance();
        if (bindingResult.hasErrors()) {
            FieldError fieldError1 = bindingResult.getFieldError();
            resultDTO.setMessage(fieldError1.getDefaultMessage());
            resultDTO.setState(State.DATE_VALIDATION_FAIL.value());
        } else {
            basicsSttingsServiceimpl.updateUserInfo(user);
            resultDTO.setMessage(State.USER_UPDATE_SUCCESS.Tips());
            resultDTO.setState(State.USER_UPDATE_SUCCESS.value());
            //更新资料后更新session信息
            UserDO userDO = this.baseServiceimpl.getUserInfoByUid(user.getUid());
            request.getSession().setAttribute("userInfo", userDO);
        }
        return resultDTO;
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
