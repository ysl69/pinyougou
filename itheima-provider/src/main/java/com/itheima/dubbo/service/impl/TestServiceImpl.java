package com.itheima.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dubbo.service.TestService;


/**
 * @Author ysl
 * @Date 2019/6/20 11:57
 * @Description: TODO
 **/

@Service
public class TestServiceImpl implements TestService {
    @Override
    public String sayhello() {
        return "hello world";
    }
}
