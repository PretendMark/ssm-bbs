package com.fly.web.constant;


/**
 * @author YuLF
 * @version 1.0
 * @date 2020/10/12 19:11
 */
public enum State {

    EMAIL_AUTHCODE_OK(200, "冷却成功，可以再次发送邮件验证码了"),
    EMAIL_AUTHCODE_COUNTDOWN(202, "再次发送邮件验证码还有冷却时间"),
    EMAIL_AUTHCODE_LIMIT(203, "抱歉-今天发送验证码已经达到上限"),
    REDIS_NOT_STARTED(204, "管理员没有开启Redis 连接redis失败"),
    USER_EXIST(205, "对不起，同一邮箱只能绑定一个账户!"),
    EMAIL_SERVICE_FAIL(206, "邮件服务：发送邮件失败了，请联系管理员检查配置和控制台日志"),
    AUTH_CODE_OK(300, "验证码效验正确!"),
    AUTH_CODE_FAIL(301, "验证码不一致，验证失败!"),
    AUTH_CODE_EMAIL_OK(302, "验证码效验正确，核实密码中..."),
    USER_NOT_EXIST(304, "用户不存在!"),
    USER_NICKNAME_EXIST(305, "*用户昵称已经被使用了!"),
    USER_NICKNAME_NOT_EXIST(306, "*用户昵称可用!"),
    INTERNAL_ERROR(500,"服务出现异常："),
    RSA_NOT_FOUND(510,"RSA加密算法未找到。请联系管理员："),
    NULL_ERROR(511,"请求参数为空!查询失败");

    private final int value;
    private final String reasonPhrase;

    State(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public static State valueOf(int statusCode) {
        State[] var1 = values();
        int var2 = var1.length;
        for(int var3 = 0; var3 < var2; ++var3) {
            State status = var1[var3];
            if (status.value == statusCode) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
    }

    public String toString() {
        return Integer.toString(this.value);
    }
    public String Tips() {
        return this.reasonPhrase;
    }
    public int value() {
        return this.value;
    }


}
