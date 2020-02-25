package com.pinyougou.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbOrderItem;
import entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author ysl
 * @Date 2019/7/12 9:20
 * @Description:
 **/

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private TbItemMapper itemMapper;


    /**
     * 购物车
     * @param cartList 已有的购物车
     * @param itemId 商品的ID
     * @param num 要购买的数量
     * @return
     */
    @Override
    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num) {
        //1.根据商品ID 查询商品的数据
        TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);

        //2.获取商品数据中的商家ID sellerId
        String sellerId = tbItem.getSellerId();

        //3.判断要添加的商品的商家的ID 是否在已有的购物车列表中存在
        Cart cart = findCartBySellerId(sellerId,cartList);
        if (cart == null){
            // 如果没有存在  直接添加商品
            cart = new Cart();

            cart.setSellerId(sellerId);//商家ID
            cart.setSellerName(tbItem.getSeller());// 店铺名

            List<TbOrderItem> orderItemList = new ArrayList<>();// 明细列表

            TbOrderItem orderItem = new TbOrderItem();

            //补充属性
            //设置他的属性
            orderItem.setItemId(itemId); //商品id
            orderItem.setGoodsId(tbItem.getGoodsId()); //SPU_ID
            orderItem.setTitle(tbItem.getTitle()); //商品标题
            orderItem.setPrice(tbItem.getPrice()); //商品单价
            orderItem.setNum(num); //传递过来的购买的数量

            double v = num * tbItem.getPrice().doubleValue();
            orderItem.setTotalFee(new BigDecimal(v)); //小计
            orderItem.setPicPath(tbItem.getImage()); //商品图片地址
            orderItem.setSellerId(sellerId);

            orderItemList.add(orderItem); //添加到明细列表
            cart.setOrderItemList(orderItemList);
            cartList.add(cart); // 添加到购物车

        }else {
            //4.如果要添加的商品的商家的ID 在已有的购物车列表中存在  此时表示有商家了
            List<TbOrderItem> orderItemList = cart.getOrderItemList();
            TbOrderItem orderItem = findOrderItemByItemId(itemId,orderItemList);


            if (orderItem != null){
                //4.1 判断 要添加的商品 是否在 商家下的明细列表中是否存在  如果 存在    数量相加
                orderItem.setNum(orderItem.getNum()+num);//数量相加

                //金额重新计算  数量* 单价
                double v = orderItem.getNum() * orderItem.getPrice().doubleValue();
                orderItem.setTotalFee(new BigDecimal(v));//重新设置

                //判断如果商品的购买数量为0 表示不买了，就要删除商品
                if (orderItem.getNum() == 0){
                    orderItemList.remove(orderItem);
                }

                //如果是长度为空说明 用户没购买该商家的商品就直接删除对象
                if (orderItemList.size() == 0){
                    cartList.remove(cart); //商家也删除了
                }

            }else {

            // 要添加的商品 如果 没有存在商家的明细列表中  说明 没有商品  直接添加该商品到明细中
                //要添加的商品的数据所封装的POJO
                orderItem = new TbOrderItem();

                orderItem.setItemId(itemId); //商品id
                orderItem.setGoodsId(tbItem.getGoodsId());//SPU_ID
                orderItem.setTitle(tbItem.getTitle()); //商品标题
                orderItem.setPrice(tbItem.getPrice()); // 商品单价
                orderItem.setNum(num); //传递过来的购买的数量

                double v = num * tbItem.getPrice().doubleValue();
                orderItem.setTotalFee(new BigDecimal(v)); //小计
                orderItem.setPicPath(tbItem.getImage()); // 商品图片地址
                orderItem.setSellerId(sellerId);

                orderItemList.add(orderItem);
            }
        }
        return cartList;
    }


    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 从redis中获取已有的购物车列表数据
     * @param name
     * @return
     */
    @Override
    public List<Cart> getCartListFromRedis(String name) {
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("CART_REDIS_KEY").get(name);
        if (cartList == null){
            cartList = new ArrayList<>();
        }
        return cartList;
    }


    /**
     * 将最新的购物车数据 存储回redis中
     * @param name
     * @param newestList
     */
    @Override
    public void saveToRedis(String name, List<Cart> newestList) {
        redisTemplate.boundHashOps("CART_REDIS_KEY").put(name,newestList);
    }


    /**
     * 合并cookie和redis的数据
     * @param cookieCartList cookie中的购物车数据
     * @param redisList redis中的购物车数据
     * @return
     */
    @Override
    public List<Cart> mergeCartList(List<Cart> cookieCartList, List<Cart> redisList) {
        //向已有的购物车中添加商品
        for (Cart cart : cookieCartList) {
            for (TbOrderItem orderItem : cart.getOrderItemList()) {
                redisList = addGoodsToCartList(redisList,orderItem.getItemId(),orderItem.getNum());
            }
        }
        return redisList;
    }


    /**
     * 在明细列表中查询 要添加的商品是否存在
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
     *从已有的购物车中查询 商家的ID 是否存在
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


