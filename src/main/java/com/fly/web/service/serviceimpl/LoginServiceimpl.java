package com.fly.web.service.serviceimpl;

import com.fly.web.dao.LoginDao;
import com.fly.web.pojo.Question;
import com.fly.web.pojo.User;
import com.fly.web.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LoginServiceimpl implements LoginService {

    @Autowired
    private LoginDao loginDao;


    @Override
    public User getPasswordForUserName(String username) {
        return loginDao.getPasswordForUserName(username);
    }

    @Override
    public List<Question> getAllQuestion(Object userEmail) {
        return loginDao.getAllQuestion(userEmail);
    }

    @Override
    public List<Question> getUserCollectQuestion(Object userEmail) {
        return loginDao.getUserCollectQuestion(userEmail);
    }
}
