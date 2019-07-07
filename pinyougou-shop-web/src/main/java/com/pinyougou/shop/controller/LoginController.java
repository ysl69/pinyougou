package com.pinyougou.shop.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ysl
 * @Date 2019/7/7 21:32
 * @Description:
 **/


@RestController
@RequestMapping("/login")
public class LoginController {
    @RequestMapping("/getName")
    public String getLoginName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
