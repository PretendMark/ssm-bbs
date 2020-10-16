package com.fly.web.pojo;

import com.fly.web.constant.State;
import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors
public class ResultDTO {

    private int time;//返回验证码冷却

    private int state;//返回状态

    private String message;//返回信息

    private String token;//普通验证码的验证token


    public static ResultDTO getInstance() {
        return new ResultDTO();
    }
}
