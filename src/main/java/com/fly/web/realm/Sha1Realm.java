package com.fly.web.realm;
import com.fly.web.pojo.UserDO;
import com.fly.web.service.serviceimpl.LoginServiceimpl;
import com.fly.web.util.ApplicationContextHelper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;


/**
 * Sha1验证
 */
public class Sha1Realm extends AuthorizingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token ) throws AuthenticationException
    {
        System.out.println( "[Sha1验证] doGetAuthenticationInfo" );
        /* 1. 把 AuthenticationToken 转换为 UsernamePasswordToken */
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        /* 2. 从 UsernamePasswordToken 中来获取 userEmail */
        String userEmail = upToken.getUsername();
        /* 3. 调用数据库的方法, 从数据库中查询 userEmail 对应的用户记录 */
        LoginServiceimpl	loginServiceimpl	= (LoginServiceimpl) ApplicationContextHelper.getBean( "loginServiceimpl" );
        UserDO user			= loginServiceimpl.getPasswordForUserName( userEmail );
        /* 4. 若用户不存在, 则可以抛出 UnknownAccountException 异常 前面的方法已经验证过 这里可以省略 */
        if ( null == user )
            throw  new UnknownAccountException();
        /*
         * 5. 根据用户信息的情况, 决定是否需要抛出其他的 AuthenticationException 异常.
         * 6. 根据用户的情况, 来构建 AuthenticationInfo 对象并返回. 通常使用的实现类为: SimpleAuthenticationInfo
         */
        ByteSource bytes = ByteSource.Util.bytes( user.getUserEmail() );


        /**
         * Do Something
         */


        /**
         * 参数1：当前Subject中的用户
         * 参数2：当前算法加密后的字符串
         * 参数3：指定的Salt值，我是从数据库取出来的
         * 参数4：当前Realm名称
         */
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo( user.getUserEmail(), user.getUserPasswordSha1(), bytes, getName() );
        return(info);
    }


    /**
     * 如果最后一种验证通过，进行授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals )
    {
        System.out.println( "[Sha1验证] doGetAuthorizationInfo。。" );
        /* 1.获取身份信息 */
        String userEmail = (String) principals.getPrimaryPrincipal();
        /* 2.根据用户名 查询用户权限并设置角色权限 */
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole( "admin" );
        /* 3.返回 */
        return(info);
    }
}
