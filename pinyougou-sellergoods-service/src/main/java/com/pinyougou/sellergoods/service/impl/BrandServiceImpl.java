package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author ysl
 * @Date 2019/6/24 10:27
 * @Description: TODO
 **/

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private TbBrandMapper brandMapper;

    /**
     * 查询所有的品牌列表
     * @return
     */
    @Override
    public List<TbBrand> findAll() {
        return brandMapper.selectAll();
    }
}
