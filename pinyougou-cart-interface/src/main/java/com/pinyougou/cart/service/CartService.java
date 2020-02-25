package com.pinyougou.cart.service;

import entity.Cart;

import java.util.List;

/**
 * @Author ysl
 * @Date 2019/7/12 9:16
 * @Description:
 **/


public interface CartService {


    /**
     * 向已有的购物车添加商品  返回一个最新的购物车列表
     * @param cartList 已有的购物车
     * @param itemId 商品的ID
     * @param num 要购买的数量
     * @return
     */
    public List<Cart> addGoodsToCartList(List<Cart> cartList,Long itemId,Integer num);


    /**
     * 从redis中获取已有的购物车列表数据
     * @param name
     * @return
     */
    List<Cart> getCartListFromRedis(String name);

    /**
     * 将最新的购物车数据 存储回redis中
     * @param name
     * @param newestList
     */
    void saveToRedis(String name, List<Cart> newestList);


    /**
     *  返回一个最新的购物车数据
     * @param cookieCartList cookie中的购物车数据
     * @param redisList redis中的购物车数据
     * @return
     */
    List<Cart> mergeCartList(List<Cart> cookieCartList, List<Cart> redisList);

}
