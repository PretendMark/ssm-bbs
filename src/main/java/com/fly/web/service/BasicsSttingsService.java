package com.fly.web.service;

import com.fly.web.pojo.ProvincialAndCityDO;
import com.fly.web.pojo.UserDO;

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

    /**
     * 更新用户头像图片名称到数据库
     * @param userName
     * @param userPictureName
     * @return
     */
    boolean saveUserPicture(String userName, String userPictureName);

    /**
     * 更新用户基本资料
     * @param user
     * @return
     */
    Boolean updateUserInfo(UserDO user);
}
