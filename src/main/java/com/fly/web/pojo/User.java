package com.fly.web.pojo;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class User {
    private Integer uid;
    @Email(message = "您的邮箱不正确!")
    private String userEmail;
    @NotEmpty
    @Length(min = 1, max = 10, message = "您的名字长度不正确!")
    private String userName;
    @NotEmpty
    @Length(min = 6, max = 16, message = "您的密码长度不正确!")
    private String userPassword;
    @Length(min = 4, max = 5, message = "您的验证码长度不正确!")
    private String userVerifyCode;
    @Length(min = 16, max = 16, message = "您的Token长度不正确!")
    private String userToken;
    private String userPasswordSalt;
    private String userPasswordSha1;
    private String userPasswordMd5;
    private Integer roleid;
    private Integer userGold;
    private String userRegDate;
    private String userCity;
    private String userGender;
    private String userPicture;
    private String userSign;
    private String userAuth;

    public User() {
    }

    public void setPassword(String userPasswordSalt, String userPasswordSha1, String userPasswordMd5) {
        this.userPasswordSalt = userPasswordSalt;
        this.userPasswordSha1 = userPasswordSha1;
        this.userPasswordMd5 = userPasswordMd5;
    }

    public String getUserToken() {
        return userToken;
    }
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
    public String getUserVerifyCode() {
        return userVerifyCode;
    }

    public void setUserVerifyCode(String userVerifyCode) {
        this.userVerifyCode = userVerifyCode;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPasswordSalt() {
        return userPasswordSalt;
    }

    public void setUserPasswordSalt(String userPasswordSalt) {
        this.userPasswordSalt = userPasswordSalt == null ? null : userPasswordSalt.trim();
    }

    public String getUserPasswordSha1() {
        return userPasswordSha1;
    }

    public void setUserPasswordSha1(String userPasswordSha1) {
        this.userPasswordSha1 = userPasswordSha1 == null ? null : userPasswordSha1.trim();
    }

    public String getUserPasswordMd5() {
        return userPasswordMd5;
    }

    public void setUserPasswordMd5(String userPasswordMd5) {
        this.userPasswordMd5 = userPasswordMd5 == null ? null : userPasswordMd5.trim();
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public Integer getUserGold() {
        return userGold;
    }

    public void setUserGold(Integer userGold) {
        this.userGold = userGold;
    }

    public String getUserRegDate() {
        return userRegDate;
    }

    public void setUserRegDate(String userRegDate) {
        this.userRegDate = userRegDate == null ? null : userRegDate.trim();
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity == null ? null : userCity.trim();
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender == null ? null : userGender.trim();
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture == null ? null : userPicture.trim();
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign == null ? null : userSign.trim();
    }

    public String getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(String userAuth) {
        this.userAuth = userAuth == null ? null : userAuth.trim();
    }

    @Override
    public String toString() {
        return "User{" +
                "userEmail='" + userEmail + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userVerifyCode='" + userVerifyCode + '\'' +
                ", userToken='" + userToken + '\'' +
                ", userPasswordSalt='" + userPasswordSalt + '\'' +
                ", userPasswordSha1='" + userPasswordSha1 + '\'' +
                ", userPasswordMd5='" + userPasswordMd5 + '\'' +
                ", userCity='" + userCity + '\'' +
                '}';
    }
}