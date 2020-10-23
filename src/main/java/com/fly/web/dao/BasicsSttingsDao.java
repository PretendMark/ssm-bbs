package com.fly.web.dao;

import com.fly.web.pojo.ProvincialAndCityDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BasicsSttingsDao {


    List<ProvincialAndCityDO> getProvincialAndCity();

    int isExistNickname(String newNickname);

    int saveUserPicture(@Param("userEmailName") String userEmailName, @Param("userPictureName") String userPictureName);
}
