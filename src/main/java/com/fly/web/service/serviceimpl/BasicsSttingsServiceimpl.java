package com.fly.web.service.serviceimpl;

import com.fly.web.dao.BasicsSttingsDao;
import com.fly.web.pojo.ProvincialAndCityDO;
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
}
