package com.fly.web.pojo;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors
public class VerifyCode {

    private int time;//返回验证码冷却
    /**
     *  200-邮件验证码：冷却成功，可以再次发送验证码了
     *  202-邮件验证码：再次发送还有冷却时间
     *  203-邮件验证码：抱歉-今天发送验证码已经超过3次
     *  204-没有开启Redis 连接redis失败
     *  205-用户已存在!
     *  206-邮件服务：发送邮件失败了，请检查配置和控制台日志
     *  300-普通验证码，效验正确，
     *  301-普通验证码：验证码不一致验证失败
     *  302-普通验证码正确并且这个邮件用户存在
     *  304-用户不存在
     */
    private int state;//返回状态

    private String message;//返回信息

    private String token;//普通验证码的验证token


}
