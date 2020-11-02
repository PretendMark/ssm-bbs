package com.fly.web.pojo;


import com.fly.web.file.LocalSaveFileAdapter;
import com.fly.web.file.UserFileUploadAccessor;
import com.fly.web.util.DateConverter;
import com.fly.web.util.PathHandler;
import com.mysql.jdbc.StringUtils;
import com.sun.istack.internal.NotNull;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户数据效验类
 */
public class UserDO implements Serializable {
    @NotBlank( groups = {updatePersonalDataGroup.class}, message = "您的用户id不能为空!" )
    @Length( groups = {updatePersonalDataGroup.class}, min = 1, max = 9, message = "用户id错误!")
    private String uid;
    @NotBlank( groups = {registerGroup.class}, message = "您的邮箱不能为空!" )
    @Email( groups = {registerGroup.class}, message = "您的邮箱不正确!" )
    private String userEmail;       /* 用户邮箱 */
    @NotBlank( groups = {registerGroup.class, updatePersonalDataGroup.class}, message = "您的名称不能为空!" )
    @Length( groups = {registerGroup.class, updatePersonalDataGroup.class}, min = 1, max = 10, message = "您的名字长度不正确!" )
    private String userName;        /* 用户名 */

    @NotBlank( groups = {registerGroup.class} , message = "您的密码不能为空!")
    @Length( groups = {registerGroup.class},  min = 6, max = 16, message = "您的密码长度不正确!" )
    private String userPassword;    /* 用户密码 */

    @NotBlank( groups = {registerGroup.class} )
    @Length( groups = {registerGroup.class},  min = 4, max = 5, message = "您的验证码长度不正确!" )
    private String userVerifyCode;  /* 用户提交的验证码 */
    @NotBlank( groups = {registerGroup.class}, message = "您的Token不能为空!" )
    @Length( groups = {registerGroup.class},  min = 15, max = 16, message = "您的Token长度不正确!" )
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
    @NotEmpty( groups = {updatePersonalDataGroup.class} ,message = "您的省份效验错误，不能为空")
    @Length( groups = {updatePersonalDataGroup.class}, min = 3, max = 10, message = "您的省份效验错误，不能为空，最小3字符，最大10个字符")
    private String	provincial;     /* 用户省 */
    @NotBlank( groups = {updatePersonalDataGroup.class}, message = "您所在的市效验失败，不能为空")
    @Length( groups = {updatePersonalDataGroup.class}, min = 2, max = 10, message = "您所在的市效验失败，最小2字符，最大10字符")
    private String	city;           /* 用户市 */
    @NotBlank( groups = {updatePersonalDataGroup.class}, message = "性别不能为空")
    @Length( groups = {updatePersonalDataGroup.class}, max = 1, message = "您的性别长度不对")
    private String	userGender;

    private String	userPicture;

    @Length( groups = {updatePersonalDataGroup.class}, max = 255, message = "您的签名超出最大长度255")
    private String	userSign;

    private String	userAuth;

    public UserDO()
    {
    }

    /**
     * 定义接口，用于指明在什么情况下，使用对应的验证规则
     */
    //验证修改个人资料 分组接口
    public interface updatePersonalDataGroup {}
    //验证注册资料接口
    public interface registerGroup{}

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


    public String getUid()
    {
        return(uid);
    }


    public void setUid( String uid )
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
        this.userGender = userGender;
        /* 用户注册时设置用户默认头像：如果用户默认没有上传头像因为Mybatis 如果数据字段为空，不会执行该字段的set方法，但是这个set性别是一定会执行的 */
        if (StringUtils.isNullOrEmpty(this.userPicture) )
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
        //数据库图片名称不等于null 则获取项目访问全路径
        if(!StringUtils.isNullOrEmpty(userPicture)){
            UserFileUploadAccessor userFileUploadAccessor = new LocalSaveFileAdapter();
            this.userPicture = userFileUploadAccessor.getUserPictureAccessPath(userPicture);
            return;
        }
        //如果头像默认为空，根据性别获取默认头像
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