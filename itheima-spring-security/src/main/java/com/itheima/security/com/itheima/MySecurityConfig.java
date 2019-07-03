package com.itheima.security.com.itheima;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author ysl
 * @Date 2019/6/30 18:49
 * @Description:
 **/

@EnableWebSecurity  //开启springseuciryt的自动的配置项 ： 自动的配置
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    //认证 和 授权
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //定义了一个用户名 admin  密码 admin 并且拥有ADMIN角色的用户
        //ROLE_  默认会自动拼接  不需要写前缀
        auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //授权 登录和错误页面 不需要登录
        //其他的请求/admin/** 都需要拥有ADMIN的角色的人 才可以访问
        //其他的请求/user/** 都需要拥有USER的角色的人 才可以访问
        //其他的任意请求 都只要登录就可以访问了。
        http.authorizeRequests()
                .antMatchers("/login.html","/error.html").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated();


        //设置使用表单登录
        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/index.jsp",true)
                .failureUrl("/error.html");

        //禁用Csrf
        // 禁用跨站请求伪造
        http.csrf().disable();
    }
}
