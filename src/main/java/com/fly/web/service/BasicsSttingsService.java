package com.fly.web.service;

import com.fly.web.pojo.ProvincialAndCity;

import java.util.List;

public interface BasicsSttingsService {

    /**
     * 获取所有省市
     * @return
     */
    List<ProvincialAndCity> getProvincialAndCity();

}
