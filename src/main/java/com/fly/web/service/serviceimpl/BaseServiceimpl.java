package com.fly.web.service.serviceimpl;

import com.fly.web.dao.BaseDao;
import com.fly.web.pojo.UserDO;
import com.fly.web.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author YuLF
 * @version 1.0
 * @date 2020/11/2 21:58
 */
@Service
public class BaseServiceimpl implements BaseService {

    @Autowired
    private BaseDao baseDao;

    @Override
    public UserDO getUserInfoByUid(String uid) {
        return baseDao.getUserInfoByUid(uid);
    }
}
