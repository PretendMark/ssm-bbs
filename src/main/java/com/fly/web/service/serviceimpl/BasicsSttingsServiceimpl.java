package com.fly.web.service.serviceimpl;

import com.fly.web.dao.BasicsSttingsDao;
import com.fly.web.pojo.ProvincialAndCity;
import com.fly.web.service.BasicsSttingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasicsSttingsServiceimpl implements BasicsSttingsService {


    @Autowired
    private BasicsSttingsDao basicsSttingsDao;


    public List<ProvincialAndCity> getProvincialAndCity() {
        return basicsSttingsDao.getProvincialAndCity();
    }
}
