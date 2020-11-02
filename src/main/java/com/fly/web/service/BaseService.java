package com.fly.web.service;

import com.fly.web.pojo.UserDO;

/**
 * @author YuLF
 * @version 1.0
 * @date 2020/11/2 21:58
 */
public interface BaseService {

    /**
     * 通过id获得用户信息
     * @param uid
     * @return
     */
    UserDO getUserInfoByUid(String uid);

}
