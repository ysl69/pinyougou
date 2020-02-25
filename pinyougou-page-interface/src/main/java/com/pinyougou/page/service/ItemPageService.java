package com.pinyougou.page.service;

/**
 * @Author ysl
 * @Date 2019/7/6 21:52
 * @Description: 商品详细页接口
 **/
public interface ItemPageService {


    /**
     * 生成商品详细页
     * @param goodsId
     */
    public void genItemHtml(Long goodsId);


    public void deleteById(Long[] goodsId);
}
