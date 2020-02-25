package com.pinyougou.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author ysl
 * @Date 2019/7/19 21:22
 * @Description:
 **/

@Controller
public class PageController {


    @RequestMapping("/page/login")
    public String showPage(String url){
        return "redirect:"+url;
    }
}
