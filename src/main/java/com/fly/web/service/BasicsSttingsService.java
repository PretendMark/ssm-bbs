package com.fly.web.service;

import com.fly.web.pojo.ProvincialAndCityDO;

import java.util.List;

public interface BasicsSttingsService {

    /**
     * 获取所有省市
     * @return
     */
    List<ProvincialAndCityDO> getProvincialAndCity();

    /**
     * 是否存在用户昵称
     * @param newNickname
     * @return
     */
    Boolean isExistNickname(String newNickname);
}
