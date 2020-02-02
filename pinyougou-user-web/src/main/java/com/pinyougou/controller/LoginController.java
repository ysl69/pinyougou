package com.pinyougou.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ysl
 * @Date 2020/2/1 20:14
 * @Description:
 **/

@RestController
@RequestMapping
public class LoginController {

    @RequestMapping("/name")
    public String getName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
