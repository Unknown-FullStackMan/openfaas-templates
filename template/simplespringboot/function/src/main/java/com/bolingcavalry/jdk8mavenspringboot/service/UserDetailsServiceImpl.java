package com.bolingcavalry.jdk8mavenspringboot.service;

import com.bolingcavalry.jdk8mavenspringboot.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserDetailsServiceImpl  implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.查询数据库 判断用户名是否存在，如果不存在就抛出UsernameNotFoundException异常

        UserInfo userInfo = userInfoService.getUserInfo(username);
        if(userInfo==null){
            throw new UsernameNotFoundException("用户名不存在！！");
        }
        //2.把查询出来的密码（注册时已经加密过）进行解析，或者直接把密码放入构造方法

        // 得到用户角色
        String role = userInfo.getRole();
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 角色必须以`ROLE_`开头，数据库中没有，则在这里加
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        String password = passwordEncoder.encode(userInfo.getPassword());
        return new User(userInfo.getUsername(),password, authorities);
    }
}
