package com.pinyougou.sellergoods.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ysl
 * @Date 2019/6/30 20:16
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
