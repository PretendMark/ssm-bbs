package com.fly.web.service;

import com.fly.web.pojo.QuestionDO;
import com.fly.web.pojo.UserDO;

import java.util.List;

public interface LoginService {

    //使用用户名查询用户 password_slat  password_md5 password_sha1
    UserDO getPasswordForUserName(String username);

    /**
     * 获取所有的问题帖子
     * @return
     */
    List<QuestionDO> getAllQuestion(Object userEmail);

    /**
     * 获取该用户收藏的帖子
     * @param userEmail
     * @return
     */
    List<QuestionDO> getUserCollectQuestion(Object userEmail);

    /**
     * 通过用户邮件获取用户身份信息
     * @param userEmail
     * @return
     */
    UserDO getUserInfo(Object userEmail);
}
