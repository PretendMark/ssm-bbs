package com.fly.web.service.serviceimpl;

import com.fly.web.dao.RegisterDao;
import com.fly.web.pojo.User;
import com.fly.web.service.RegisterService;
import com.fly.web.util.Encryption;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RegisterServiceimpl implements RegisterService {
    @Autowired
    private RegisterDao registerDao;

    @Override
    public Boolean isExistUser( String email )
    {
        int count = registerDao.getCountFromEmail( email );
        return(count > 0 ? true : false);
    }


    @Override
    public int saveUser( User user )
    {
        /* Salt */
        Object salt = ByteSource.Util.bytes( user.getUserEmail() );

        /* 加密密码 */
        Object	md5	= Encryption.encryption( "MD5", salt, user.getUserPassword(), 1024 );
        Object	sha1	= Encryption.encryption( "SHA1", salt, user.getUserPassword(), 1024 );
        user.setPassword( salt + "", sha1 + "", md5 + "" );
        /* 注册时间 */
        user.setUserRegDate( System.currentTimeMillis() + "" );
        /* 保存至数据库 */
        return(registerDao.saveUser( user ) );
    }
}
