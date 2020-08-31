package com.fly.web.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors
public class ExceptionBody {

    /**
     *      510-NoSuchAlgorithmException
     */
    //异常提示信息
    private String message;
    //异常状态
    private int state;

}
