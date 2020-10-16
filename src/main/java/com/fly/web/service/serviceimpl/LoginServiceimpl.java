package com.fly.web.service.serviceimpl;

import com.fly.web.dao.LoginDao;
import com.fly.web.pojo.QuestionDO;
import com.fly.web.pojo.UserDO;
import com.fly.web.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LoginServiceimpl implements LoginService {

    @Autowired
    private LoginDao loginDao;


    @Override
    public UserDO getPasswordForUserName(String username) {
        return loginDao.getPasswordForUserName(username);
    }

    @Override
    public List<QuestionDO> getAllQuestion(Object userEmail) {
        return loginDao.getAllQuestion(userEmail);
    }

    @Override
    public List<QuestionDO> getUserCollectQuestion(Object userEmail) {
        return loginDao.getUserCollectQuestion(userEmail);
    }

    @Override
    public UserDO getUserInfo(Object userEmail) {


        return loginDao.getUserInfo(userEmail);
    }
}
