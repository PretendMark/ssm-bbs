package com.fly.web.dao;


import com.fly.web.pojo.UserDO;

public interface BaseDao {


    UserDO getUserInfoByUid(String uid);
}
