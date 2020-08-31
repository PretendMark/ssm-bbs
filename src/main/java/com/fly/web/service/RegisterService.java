package com.fly.web.service;

import com.fly.web.pojo.User;

public interface RegisterService {

    //是否存在这个用户
    Boolean isExistUser(String email);

    //保存用户
    int saveUser(User user);


}
