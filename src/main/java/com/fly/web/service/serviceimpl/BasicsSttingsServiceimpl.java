package com.fly.web.service.serviceimpl;

import com.fly.web.dao.BasicsSttingsDao;
import com.fly.web.pojo.ProvincialAndCityDO;
import com.fly.web.pojo.UserDO;
import com.fly.web.service.BasicsSttingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasicsSttingsServiceimpl implements BasicsSttingsService {


    @Autowired
    private BasicsSttingsDao basicsSttingsDao;


    public List<ProvincialAndCityDO> getProvincialAndCity() {
        return basicsSttingsDao.getProvincialAndCity();
    }

    @Override
    public Boolean isExistNickname(String newNickname) {
        return basicsSttingsDao.isExistNickname(newNickname) > 0 ? true : false;
    }

    @Override
    public boolean saveUserPicture(String userEmailName, String userPictureName) {
        return basicsSttingsDao.saveUserPicture(userEmailName,userPictureName) > 0 ? true : false;
    }

    @Override
    public Boolean updateUserInfo(UserDO user) {
        //省市转换存储
        user.setUserCity(user.getProvincial()+"-"+user.getCity());
        return basicsSttingsDao.updateUserInfo(user) > 0 ? true : false;
    }
}
