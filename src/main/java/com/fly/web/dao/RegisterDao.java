package com.fly.web.dao;


import com.fly.web.pojo.UserDO;
import org.apache.ibatis.annotations.Param;

public interface RegisterDao extends BaseDao {

    int saveUser(@Param("us") UserDO user);

    int getCountFromEmail(String email);
}
