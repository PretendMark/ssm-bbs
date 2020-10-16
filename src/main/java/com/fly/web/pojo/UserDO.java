package com.fly.web.pojo;


import com.fly.web.util.DateConverter;
import com.fly.web.util.PathHandler;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * 注册
 */
public class UserDO implements Serializable {
    private Integer uid;
    @Email( message = "您的邮箱不正确!" )
    private String userEmail;       /* 用户邮箱 */
    @NotEmpty
    @Length( min = 1, max = 10, message = "您的名字长度不正确!" )
    private String userName;        /* 用户名 */


    @NotEmpty
    @Length( min = 6, max = 16, message = "您的密码长度不正确!" )
    private String userPassword;    /* 用户密码 */


    @Length( min = 4, max = 5, message = "您的验证码长度不正确!" )
    private String userVerifyCode;  /* 用户提交的验证码 */
    @Length( min = 16, max = 16, message = "您的Token长度不正确!" )
    private String userToken;       /* 用户密钥 */
    private String	userPasswordSalt;
    private String	userPasswordSha1;
    private String	userPasswordMd5;
    private Integer roleid;
    private String	roleEnglishName;
    private String	roleChineseName;
    private Integer userGold;
    private String	userRegDate;
    private String	userCity;       /* 用户省市 */
    private String	provincial;     /* 用户省 */
    private String	city;           /* 用户市 */
    private String	userGender;
    private String	userPicture;
    private String	userSign;
    private String	userAuth;

    public UserDO()
    {
    }


    public void setPassword( String userPasswordSalt, String userPasswordSha1, String userPasswordMd5 )
    {
        this.userPasswordSalt	= userPasswordSalt;
        this.userPasswordSha1	= userPasswordSha1;
        this.userPasswordMd5	= userPasswordMd5;
    }


    public String getProvincial()
    {
        return(provincial);
    }


    public String getCity()
    {
        return(city);
    }


    public void setProvincial( String provincial )
    {
        this.provincial = provincial;
    }


    public void setCity( String city )
    {
        this.city = city;
    }


    public String getRoleEnglishName()
    {
        return(roleEnglishName);
    }


    public String getRoleChineseName()
    {
        return(roleChineseName);
    }


    public void setRoleEnglishName( String roleEnglishName )
    {
        this.roleEnglishName = roleEnglishName;
    }


    public void setRoleChineseName( String roleChineseName )
    {
        this.roleChineseName = roleChineseName;
    }


    public String getUserToken()
    {
        return(userToken);
    }


    public void setUserToken( String userToken )
    {
        this.userToken = userToken;
    }


    public String getUserVerifyCode()
    {
        return(userVerifyCode);
    }


    public void setUserVerifyCode( String userVerifyCode )
    {
        this.userVerifyCode = userVerifyCode;
    }


    public String getUserPassword()
    {
        return(userPassword);
    }


    public void setUserPassword( String userPassword )
    {
        this.userPassword = userPassword;
    }


    public Integer getUid()
    {
        return(uid);
    }


    public void setUid( Integer uid )
    {
        this.uid = uid;
    }


    public String getUserEmail()
    {
        return(userEmail);
    }


    public void setUserEmail( String userEmail )
    {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }


    public String getUserName()
    {
        return(userName);
    }


    public void setUserName( String userName )
    {
        this.userName = userName == null ? null : userName.trim();
    }


    public String getUserPasswordSalt()
    {
        return(userPasswordSalt);
    }


    public void setUserPasswordSalt( String userPasswordSalt )
    {
        this.userPasswordSalt = userPasswordSalt == null ? null : userPasswordSalt.trim();
    }


    public String getUserPasswordSha1()
    {
        return(userPasswordSha1);
    }


    public void setUserPasswordSha1( String userPasswordSha1 )
    {
        this.userPasswordSha1 = userPasswordSha1 == null ? null : userPasswordSha1.trim();
    }


    public String getUserPasswordMd5()
    {
        return(userPasswordMd5);
    }


    public void setUserPasswordMd5( String userPasswordMd5 )
    {
        this.userPasswordMd5 = userPasswordMd5 == null ? null : userPasswordMd5.trim();
    }


    public Integer getRoleid()
    {
        return(roleid);
    }


    public void setRoleid( Integer roleid )
    {
        this.roleid = roleid;
    }


    public Integer getUserGold()
    {
        return(userGold);
    }


    public void setUserGold( Integer userGold )
    {
        this.userGold = userGold;
    }


    public String getUserRegDate()
    {
        return(userRegDate);
    }


    public void setUserRegDate( String userRegDate )
    {
        //不等于空就是注册 注册存入的是没有转换的时间戳
        if(this.userPasswordSha1 == null){
            this.userRegDate = DateConverter.fromToday( new Date( Long.parseLong( String.valueOf( userRegDate ) ) ) );
        } else {
            this.userRegDate = userRegDate;
        }
    }


    public String getUserCity()
    {
        return(userCity);
    }


    public void setUserCity( String userCity )
    {
        //赋值省市
        if ( userCity != null && userCity.contains( "-" ) )
        {
            String[] split	= userCity.split( "-" );
            this.provincial = split[0];
            this.city	= split[1];
        }
            this.userCity = userCity == null ? null : userCity.trim();

    }


    public String getUserGender()
    {
        return(userGender);
    }


    public void setUserGender( String userGender )
    {
        if ( userGender != null && userGender.equals( "1" ) )
        {
            this.userGender = "女";
        } else { this.userGender = "男"; }
        /* 如果用户默认没有上传头像 */
        if ( this.userPicture == null )
        {
            this.userPicture = PathHandler.getDefaultPicture( this.userGender );
        }
    }


    public String getUserPicture()
    {
        return(userPicture);
    }


    public void setUserPicture( String userPicture )
    {
        this.userPicture = userPicture;
    }


    public String getUserSign()
    {
        return(userSign);
    }


    public void setUserSign( String userSign )
    {
        this.userSign = userSign == null ? null : userSign.trim();
    }


    public String getUserAuth()
    {
        return(userAuth);
    }


    public void setUserAuth( String userAuth )
    {
        this.userAuth = userAuth == null ? null : userAuth.trim();
    }


    @Override
    public String toString()
    {
        return("User{" +
                "uid=" + uid +
                ", userEmail='" + userEmail + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userVerifyCode='" + userVerifyCode + '\'' +
                ", userToken='" + userToken + '\'' +
                ", userPasswordSalt='" + userPasswordSalt + '\'' +
                ", userPasswordSha1='" + userPasswordSha1 + '\'' +
                ", userPasswordMd5='" + userPasswordMd5 + '\'' +
                ", roleid=" + roleid +
                ", roleEnglishName='" + roleEnglishName + '\'' +
                ", roleChineseName='" + roleChineseName + '\'' +
                ", userGold=" + userGold +
                ", userRegDate='" + userRegDate + '\'' +
                ", userCity='" + userCity + '\'' +
                ", provincial='" + provincial + '\'' +
                ", city='" + city + '\'' +
                ", userGender='" + userGender + '\'' +
                ", userPicture='" + userPicture + '\'' +
                ", userSign='" + userSign + '\'' +
                ", userAuth='" + userAuth + '\'' +
                '}');
    }
}