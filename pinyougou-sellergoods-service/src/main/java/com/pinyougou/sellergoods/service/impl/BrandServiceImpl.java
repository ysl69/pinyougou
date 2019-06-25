package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.core.service.CoreServiceImpl;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;

/**
 * @Author ysl
 * @Date 2019/6/24 10:27
 * @Description: TODO
 **/

@Service
public class BrandServiceImpl extends CoreServiceImpl<TbBrand> implements BrandService {


    private TbBrandMapper brandMapper;

   /* @Autowired
    public BrandServiceImpl(Mapper<TbBrand> baseMapper, Class<TbBrand> clazz) {
        super(baseMapper, clazz);
    }*/
   @Autowired
   public BrandServiceImpl(TbBrandMapper brandMapper) {
       super(brandMapper, TbBrand.class);
       this.brandMapper=brandMapper;
   }



    /**
     * 品牌列表分页
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<TbBrand> findPage(Integer pageNo, Integer pageSize) {
       // 开始分页
        PageHelper.startPage(pageNo,pageSize);
        // 执行查询
        List<TbBrand> brands = brandMapper.selectAll();
        // 返回pageInfo对象
        PageInfo<TbBrand> pageInfo = new PageInfo<>(brands);

        // 先通过jSON序列化成string
        String str = JSON.toJSONString(pageInfo);
        // 字符串转换成对象
        PageInfo pageInfo1 = JSON.parseObject(str, PageInfo.class);

        return pageInfo1;
    }


    /**
     * 实现品牌条件查询功能，输入品牌名称、首字母后查询，并分页
     * @param pageNo
     * @param pageSize
     * @param brand
     * @return
     */
    @Override
    public PageInfo<TbBrand> findPage(Integer pageNo, Integer pageSize, TbBrand brand) {
        // 获取条件对象
        Example example = new Example(TbBrand.class);
        Example.Criteria criteria = example.createCriteria();

        // 判断组装条件
        if (brand != null){
            if (StringUtils.isNotBlank(brand.getName())){
                criteria.andLike("name","%"+brand.getName()+"%");
                // name like "%联想%"
            }

            if (StringUtils.isNotBlank(brand.getFirstChar())){
                criteria.andLike("firstChar","%"+brand.getFirstChar()+"%");
                // firstChar="%l%"
            }
        }

        // 开始分页
        PageHelper.startPage(pageNo,pageSize);
        // 执行查询
        List<TbBrand> brands = brandMapper.selectByExample(example);
        //返回pageInfo
        PageInfo<TbBrand> pageInfo = new PageInfo<>(brands);

        // 序列化再反序列化
        String str = JSON.toJSONString(pageInfo);
        PageInfo pageInfo1 = JSON.parseObject(str, PageInfo.class);
        return pageInfo1;
    }
}
