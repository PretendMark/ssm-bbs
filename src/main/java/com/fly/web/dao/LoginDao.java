package com.fly.web.dao;

import com.fly.web.pojo.QuestionDO;
import com.fly.web.pojo.UserDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoginDao {

    UserDO getPasswordForUserName(@Param("userEmail") String userEmail);

    List<QuestionDO> getAllQuestion(@Param("userEmail")Object userEmail);

    List<QuestionDO> getUserCollectQuestion(@Param("userEmail")Object userEmail);

    UserDO getUserInfo(@Param("userEmail")Object userEmail);
}
