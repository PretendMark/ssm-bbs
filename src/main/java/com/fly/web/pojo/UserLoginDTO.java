package com.fly.web.pojo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 登录
 */
public class UserLoginDTO {
    @Email
    private String userEmail;
    @NotNull
    @NotEmpty
    private String userPassword;
    @NotEmpty
    private String userVerifyCode;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserVerifyCode() {
        return userVerifyCode;
    }

    public void setUserVerifyCode(String userVerifyCode) {
        this.userVerifyCode = userVerifyCode;
    }

    @Override
    public String toString() {
        return "LoginVerify{" +
                "userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userVerifyCode='" + userVerifyCode + '\'' +
                '}';
    }
}
