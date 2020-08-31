package com.fly.web.dao;

import com.fly.web.pojo.Question;
import com.fly.web.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoginDao {

    User getPasswordForUserName(@Param("userEmail") String userEmail);

    List<Question> getAllQuestion(@Param("userEmail")Object userEmail);

    List<Question> getUserCollectQuestion(@Param("userEmail")Object userEmail);
}
