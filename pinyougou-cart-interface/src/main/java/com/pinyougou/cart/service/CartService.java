package com.pinyougou.cart.service;

import com.pinyougou.pojo.Cart;

import java.util.List;

/**
 * @Author ysl
 * @Date 2020/2/23 15:53
 * @Description:
 **/


public interface CartService {


    /**
     * 向已有的购物车添加商品
     * @param cartList 已有的购物车
     * @param itemId 商品的ID
     * @param num 要购买的数量
     * @return
     */
    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num);

}
