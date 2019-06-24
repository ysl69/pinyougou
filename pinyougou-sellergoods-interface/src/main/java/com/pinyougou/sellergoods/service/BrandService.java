package com.pinyougou.sellergoods.service;

import com.github.pagehelper.PageInfo;
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
     *
     * @return
     */
    List<TbBrand> findAll();


    /**
     * 品牌列表分页
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo<TbBrand> findPage(Integer pageNo,Integer pageSize);


    /**
     * 实现品牌条件查询功能，输入品牌名称、首字母后查询，并分页
     * @param pageNo
     * @param pageSize
     * @param brand
     * @return
     */
    PageInfo<TbBrand> findPage(Integer pageNo,Integer pageSize,TbBrand brand);


    /**
     * 添加品牌
     * @param brand
     */
    public void add(TbBrand brand);


    /**
     * 更新品牌
     * @param brand
     */
    public void update(TbBrand brand);


    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    public TbBrand findOne(Long id);


    /**
     * 删除
     * @param ids
     */
    public void  delete(Long[] ids);
}

