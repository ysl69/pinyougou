package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author ysl
 * @Date 2019/6/24 10:29
 * @Description: TODO
 **/

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;


    /**
     * 查询所有的品牌列表
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbBrand> findAll(){
        return brandService.findAll();
    }
}
