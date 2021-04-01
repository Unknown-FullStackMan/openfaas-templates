package com.bolingcavalry.jdk8mavenspringboot.pojo;

import lombok.Data;

/**
 * @author Simple
 * @date 2021/4/1 11:26
 */

@Data
public class UserInfo {
    private int id;
    private String username;
    private String password;
    private String role;
}
