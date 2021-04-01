package com.bolingcavalry.jdk8mavenspringboot.dao;

import com.bolingcavalry.jdk8mavenspringboot.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author Simple
 * @date 2021/4/1 11:21
 */

@Mapper
@Repository
public interface UserInfoMapper {
    @Select("select * from user where username = #{username}")
    UserInfo getUserInfoByUsername(String username);
}
