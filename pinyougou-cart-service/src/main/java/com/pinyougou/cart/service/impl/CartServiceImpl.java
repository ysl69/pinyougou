package com.pinyougou.cart.service.impl;

import com.pinyougou.cart.service.CartService;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.Cart;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author ysl
 * @Date 2020/2/23 15:57
 * @Description:
 **/


public class CartServiceImpl implements CartService {

    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num) {
        //1.根据商品SKU ID查询SKU商品信息
        TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);

        //2.获取商家ID
        String sellerId = tbItem.getSellerId();
        Cart cart = findCartBySellerId(sellerId,cartList);

        //3.3.判断要添加的商品的商家的ID 是否在已有的购物车列表中存在
        // 如果没有存在  直接添加商品
        if (cart == null){
            cart = new Cart();
            cart.setSellerId(sellerId);
            cart.setSellerName(tbItem.getSeller());//店铺名

            List<TbOrderItem> orderitemlist = new ArrayList<>();//明细列表

            TbOrderItem orderItemnew = new TbOrderItem();
            //补充属性
            //设置它的属性
            orderItemnew.setItemId(itemId);
            orderItemnew.setGoodsId(tbItem.getGoodsId());
            orderItemnew.setTitle(tbItem.getTitle());
            orderItemnew.setPrice(tbItem.getPrice());
            orderItemnew.setNum(num); //传递过来的购买数量

            double v = num * tbItem.getPrice().doubleValue();
            orderItemnew.setTotalFee(new BigDecimal(v));//金额

            orderItemnew.setPicPath(tbItem.getImage());

            orderitemlist.add(orderItemnew);

            cartList.add(cart);
        }else {
            List<TbOrderItem> orderItemList = cart.getOrderItemList();//明细列表
            TbOrderItem orderItem = findOrderItemByItemId(itemId, orderItemList);

            //4.如果要添加的商品的商家的ID 在已有的购物车列表中存在  此时表示有商家了

            if (orderItem != null){
                //4.1 判断要添加的商品 是否在已有的商家的明细列表中存在
                // 如果 有  说明要添加的商品存在于购物车中   数量向加
                orderItem.setNum(orderItem.getNum()+num); //数量相加
                //金额重新计算  数量*单价
                double v = orderItem.getNum() * orderItem.getPrice().doubleValue();
                orderItem.setTotalFee(new BigDecimal(v));

                //判断如果商品的购买数量为0  表示不买了，直接删除商品
                if (orderItem.getNum() == 0){
                    orderItemList.remove(orderItem);
                }

                //如果是长度为空 用户没有购买该商品 删除对象
                if (orderItemList.size() == 0){ //[]
                    cartList.remove(cart); //商家也删除了
                }
            }else {
                // 4.2要添加的商品 如果没有存在商家的明细列表中
                //  说明没有商品 直接添加该商品到明细中
                TbOrderItem orderItemnew = new TbOrderItem();
                //设置他的属性
                orderItemnew.setItemId(itemId);
                orderItemnew.setGoodsId(tbItem.getGoodsId());
                orderItemnew.setTitle(tbItem.getTitle());
                orderItemnew.setPrice(tbItem.getPrice());
                orderItemnew.setNum(num);//传递过来的购买的数量
                double v = num * tbItem.getPrice().doubleValue();
                orderItemnew.setTotalFee(new BigDecimal(v));//金额
                orderItemnew.setPicPath(tbItem.getImage());//商品的图片路径
                orderItemList.add(orderItemnew);
            }
        }

        return null;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Cart> findCartListFormRedis(String username) {
        return (List<Cart>) redisTemplate.boundHashOps("cartList").get(username);
    }

    @Override
    public void saveCartListToRedis(String username, List<Cart> cartList) {
        redisTemplate.boundHashOps("cartList").put(username,cartList);
    }

    @Override
    public List<Cart> mergeCartList(List<Cart> cookieList, List<Cart> redisList) {
        for(Cart cart: cookieList){
            for(TbOrderItem orderItem:cart.getOrderItemList()){
                redisList= addGoodsToCartList(redisList,orderItem.getItemId(),orderItem.getNum());
            }
        }
        return redisList;
    }


    /**
     * 判断是否属于同一商品
     * @param itemId
     * @param orderItemList
     * @return
     */
    private TbOrderItem findOrderItemByItemId(Long itemId, List<TbOrderItem> orderItemList) {
        for (TbOrderItem orderItem : orderItemList) {
           if (orderItem.getItemId().longValue() == itemId){
               return orderItem;
           }
        }
        return null;
    }


    /**
     * 判断是否属于同一商家
     * @param sellerId
     * @param cartList
     * @return
     */
    private Cart findCartBySellerId(String sellerId, List<Cart> cartList) {
        for (Cart cart : cartList) {
            if (cart.getSellerId().equals(sellerId)){
                return cart;
            }
        }
        return null;
    }
}
