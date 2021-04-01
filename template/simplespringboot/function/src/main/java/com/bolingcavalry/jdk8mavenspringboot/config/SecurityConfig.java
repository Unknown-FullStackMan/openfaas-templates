package com.bolingcavalry.jdk8mavenspringboot.config;



import com.bolingcavalry.jdk8mavenspringboot.handler.MyAccessDenieHandler;
import com.bolingcavalry.jdk8mavenspringboot.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAccessDenieHandler myAccessDenieHandler;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //表单提交
        http.formLogin()
                //自定义登录页面
                .loginPage("/login.html")
                //自定义csrf登录页面，通常使用这个，开启csrf，避免攻击
                //.loginPage("/showLogin")
                //当发现 /login认为是登录，必须和表单提交的地址一样，去执行UserDetailsServiceImpl
                .loginProcessingUrl("/login")
                //登录成功后的页面必须是post请求
                .successForwardUrl("/toMain")
                //自定义登录成功处理器 实际开发使用最多，不能和successForwardUrl共存不然报错
                //.successHandler(new MyAuthenticationSuccessHandler("http://www.baidu.com"))
                .failureForwardUrl("/toError");
                //自定义登陆失败处理器 实际开发使用最多
                //.failureHandler(new MyAuthenticationFailureHandler("/error.html"));


        //授权认证
        http.authorizeRequests()
                //login.html不需要被认证
                //.antMatchers("/showLogin").permitAll()
                .antMatchers("/error.html").permitAll()
                .antMatchers("/login.html").permitAll()
                .antMatchers("/login.css").permitAll()
                //放行自定义csrf登录页面,通常使用这个，开启csrf，避免攻击
                //.antMatchers("/showLogin").permitAll()
                //基于access
                //.antMatchers("/login.html").access("permitAll")
                //放行静态资源
                .antMatchers("/js/**","/css/**","/images/**").permitAll()
                //放行静态资源以png结尾的文件
                //.antMatchers("/**/*.png").permitAll()
                // 正则表达式
                //.regexMatchers(".+[.]png").permitAll()
                //.regexMatchers("/demo").permitAll()
                //mvc匹配
                //.mvcMatchers("/demo").servletPath("/xxx").permitAll()


                //权限判断,严格区分大小写
                .antMatchers("/main1.html").hasAnyAuthority("admin")
                //设置多个权限控制
                //.antMatchers("/main1.html").hasAnyAuthority("admin","adMIN")

                //角色判断
                .antMatchers("/main1.html").hasRole("admin")
                //.antMatchers("/main1.html").hasAnyRole("ABC,abc")
                //基于access
                //.antMatchers("/main1.html").access("hasRole('abc')")

                //IP地址判断
                //.antMatchers("/main1.html").hasIpAddress("127.0.0.1")
                //.antMatchers("/**/*.png").permitAll()

                //所有请求都必须被认证，必须登录后被访问
                .anyRequest().authenticated();
                //自定义access方法
                //.anyRequest().access("@myServiceImpl.hasPermission(request,authentication)");

        //异常处理
        http.exceptionHandling()
                .accessDeniedPage("/error2.html");
                //.accessDeniedHandler(myAccessDenieHandler);

        //记住我
        http.rememberMe()
                //失效时间单位秒
                .tokenValiditySeconds(6)
                //.rememberMeParameter()
                //自定义登录逻辑
                .userDetailsService(userDetailsService)
                //持久层对象
                .tokenRepository(persistentTokenRepository);

        //退出
        http.logout()
                //自定义退出名,和login.html里面的退出名一致,一般不改
                ////.logoutUrl("/user/logout")
                //退出成功后跳转的页面
                .logoutSuccessUrl("/login.html");

        //关闭csrf防护。 如果使用了csrf定义的页面，就打开csr防护。
        http.csrf().disable();

    }


    @Bean
    public PasswordEncoder getPw(){
        return new BCryptPasswordEncoder();
    }


    //存储用户信息
    @Bean
    public PersistentTokenRepository getPersistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository=new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
//        //自动建表,第一次启动需要，第二次启动注释掉
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }
}
