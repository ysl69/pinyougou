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


    /**
     * 从redis中查询购物车
     * @param username
     * @return
     */
    public List<Cart> findCartListFormRedis(String username);


    /**
     * 将购物车保存到redis
     * @param username
     * @param cartList
     */
    public void saveCartListToRedis(String username,List<Cart> cartList);
}
