package com.bolingcavalry.jdk8mavenspringboot.service;

import com.bolingcavalry.jdk8mavenspringboot.dao.UserInfoMapper;
import com.bolingcavalry.jdk8mavenspringboot.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Simple
 * @date 2021/4/1 11:32
 */
@Service
public class UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    public UserInfo getUserInfo(String username){
        return userInfoMapper.getUserInfoByUsername(username);
    }

}
