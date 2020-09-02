package com.fly.web.realm;

import com.fly.web.pojo.User;
import com.fly.web.service.serviceimpl.LoginServiceimpl;
import com.fly.web.util.ApplicationContextUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * MD5验证
 */
public class Md5Realm extends AuthenticatingRealm {
    /**
     * 用于认证 One
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token ) throws AuthenticationException
    {
        System.out.println( "[MD5验证] doGetAuthenticationInfo" );
        /* 1. 把 AuthenticationToken 转换为 UsernamePasswordToken */
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        /* 2. 从 UsernamePasswordToken 中来获取 userEmail(用用户邮件作为的用户名) */
        String userEmail = upToken.getUsername();
        /* 3. 调用数据库的方法, 从数据库中查询 userEmail 对应的用户记录 */
        LoginServiceimpl	loginServiceimpl	= (LoginServiceimpl) ApplicationContextUtils.getBean( "loginServiceimpl" );
        User			user			= loginServiceimpl.getPasswordForUserName( userEmail );
        /* 4. 若用户不存在, 则可以抛出 UnknownAccountException 异常 */
        if ( null == user )
            throw  new UnknownAccountException();
        /*
         * 5. 根据用户信息的情况, 决定是否需要抛出其他的 AuthenticationException 异常.
         * 6. 根据用户的情况, 来构建 AuthenticationInfo 对象并返回. 通常使用的实现类为: SimpleAuthenticationInfo
         * 7 realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可
         */
        ByteSource bytes = ByteSource.Util.bytes( user.getUserEmail() );


        /**
         * 参数1：当前Subject中的用户
         * 参数2：当前算法加密后的字符串
         * 参数3：指定的Salt值，我是从数据库取出来的
         * 参数4：当前Realm名称
         */
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo( user.getUserEmail(), user.getUserPasswordMd5(), bytes, getName() );
        return(info);
    }
}
