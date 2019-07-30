package com.pinyougou.page.service;

/**
 * @Author ysl
 * @Date 2019/7/28 21:10
 * @Description:  商品详细页接口
 **/
public interface ItemPageService {

    /**
     * 生成商品详细页
     * @param goodsId
     */
    public void genItemHtml(Long goodsId);


    /**
     * 删除商品详细页
     * @param goodsId
     */
    void deleteById(Long[] goodsId);
}
