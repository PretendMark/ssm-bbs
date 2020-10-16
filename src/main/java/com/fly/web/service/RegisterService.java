package com.fly.web.service;


import com.fly.web.pojo.UserDO;

public interface RegisterService {

    /**
     * 是否存在这个用户
     * @param email
     * @return
     */
    Boolean isExistUser(String email);

    /**
     * 保存用户
     * @param user
     * @return
     */
    int saveUser(UserDO user);


}
