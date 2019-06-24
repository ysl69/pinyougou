package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;

import java.util.List;

/**
 * @Author ysl
 * @Date 2019/6/24 10:26
 * @Description: TODO  服务层接口
 **/
public interface BrandService {

    /**
     * 查询所有的品牌列表
     * @return
     */
    List<TbBrand> findAll();
}

