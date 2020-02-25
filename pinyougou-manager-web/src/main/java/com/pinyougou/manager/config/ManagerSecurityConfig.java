package com.pinyougou.manager.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author ysl
 * @Date 2019/6/26 11:32
 * @Description: TODO
 **/

@EnableWebSecurity  //开启自动的security的配置项
public class ManagerSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //1.拦截请求  admin/**
        http.authorizeRequests()
                //2.放行 css js login.html
                .antMatchers("/css/**", "/img/**", "/js/**", "/plugins/**", "/login.html").permitAll()
                //设置所有的其他请求都需要认证通过即可 也就是用户名和密码正确即可不需要其他的角色
                .anyRequest().authenticated();

        //3.配置自定义的表单的登录
        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/admin/index.html", true)
                .failureUrl("/login?error");
        //4.csrf禁用
        http.csrf().disable();//关闭CSRF

        //5.开启同源iframe 可以访问策略
        http.headers().frameOptions().sameOrigin();

        // 配置退出
        http.logout().logoutUrl("/logout").invalidateHttpSession(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //会自动添加ROLE_
        auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN");
    }
}
