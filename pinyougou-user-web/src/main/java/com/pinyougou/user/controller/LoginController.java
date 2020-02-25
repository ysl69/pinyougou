package com.pinyougou.user.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ysl
 * @Date 2019/7/11 10:27
 * @Description:
 **/

@RestController
@RequestMapping("/login")
public class LoginController {


    /**
     * 获取用户名
     * @return
     */
    @RequestMapping("/name")
    public String getName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
