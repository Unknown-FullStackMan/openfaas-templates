package com.bolingcavalry.jdk8mavenspringboot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {


    /**
     * 登录
     * @return
     */
//    //login与它默认的login重名,导致登录成功找不到重定向页面
//    @RequestMapping("/login2")
//    public String Login(){
//        System.out.println("执行登登录方法");
//        return "redirect:login.html";
//    }

   /* @RequestMapping("/")
    public String test(){
        System.out.println("执行登登录方法");
        return "login.html";
    }*/


    //@Secured("ROLE_abc")
    //PreAuthorize可以ROLE_开头 也可以不用ROLE_开头,配置类不允许ROLE_开头
    //@PreAuthorize("hasRole('abc')")
    @RequestMapping("/toMain")

    public String toMain(){
        return "redirect:main.html";
    }

    @RequestMapping("/toError")
    public String toError(){
        return "redirect:error.html";
    }

    @RequestMapping("/demo")
    public String demo(){
        return "demo";
    }


    //页面跳转
    @RequestMapping("/showLogin")
    public String showLogin(){
        return "login";
    }

}
