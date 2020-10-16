package com.fly.web.dao;

import com.fly.web.pojo.ProvincialAndCityDO;

import java.util.List;

public interface BasicsSttingsDao {


    List<ProvincialAndCityDO> getProvincialAndCity();

    int isExistNickname(String newNickname);
}
