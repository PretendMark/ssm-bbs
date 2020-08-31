package com.fly.web.dao;


import com.fly.web.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface RegisterDao extends BaseDao {

    int saveUser(@Param("us") User user);

    int getCountFromEmail(String email);
}
