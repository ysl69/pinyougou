package com.pinyougou.sellergoods.config;

import org.apache.http.impl.client.HttpClients;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.xml.ws.http.HTTPBinding;

/**
 * @Author ysl
 * @Date 2019/6/30 19:46
 * @Description:
 **/

@EnableWebSecurity   //开启springseuciryt的自动的配置项 ： 自动的配置
public class ManagerSecurityConfig extends WebSecurityConfigurerAdapter {

    //认证 和 授权
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //定义了一个用户名 admin  密码 admin 并且拥有ADMIN角色的用户
        auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //1.拦截请求  admin/**
        http.authorizeRequests()
                //2.放行 css js login.html
                .antMatchers("/css/**","/img/**","/js/**","/plugins/**","/login.html").permitAll()
                //设置所有的其他请求都需要认证通过即可 也就是用户名和密码正确即可不需要其他的角色
                .anyRequest().authenticated();

        //3.配置自定义的表单的登录
        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/admin/index.html",true)
                .failureUrl("/login?error");

        //4.csrf禁用
        http.csrf().disable();

        //5.配置 html设置为同源 访问策略
        http.headers().frameOptions().sameOrigin();

        //配置退出
        http.logout().logoutUrl("/logout").invalidateHttpSession(true);
    }
}
