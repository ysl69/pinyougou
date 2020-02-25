package com.itheima.dubbo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.dubbo.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ysl
 * @Date 2019/6/21 11:23
 * @Description: TODO
 **/

@RestController   // @RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用。
public class TestController {

    @Reference
    private TestService testService;


    @RequestMapping("/sayHello")
    public String showHelo(){
        return testService.sayhello();
    }
}
