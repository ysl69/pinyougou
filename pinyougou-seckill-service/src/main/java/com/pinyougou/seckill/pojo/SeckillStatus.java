package com.pinyougou.seckill.pojo;

import java.io.Serializable;

/**
 * @Author ysl
 * @Date 2019/7/20 11:35
 * @Description:存储相关秒杀状态
 **/


public class SeckillStatus implements Serializable {
    public static final Integer SECKILL_queuing=1;//排队中
    public static final Integer SECKILL_to_be_paid=2;//待支付
    public static final Integer SECKILL_be_paid_timeout=3;//支付超时
    public static final Integer SECKILL_fail=4;//支付失败
    public static final Integer SECKILL_paid=5;//已支付

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private String userId;//用户的ID
    private Long goodsId;//秒杀商品的ID
    private Integer status;//秒杀的状态 1.排队中 2.待支付 3 支付超时 4 秒杀失败 5 已支付

    public SeckillStatus() {
    }

    public SeckillStatus(String userId, Long goodsId) {
        this.userId = userId;
        this.goodsId = goodsId;
    }

    public SeckillStatus(String userId, Long goodsId, Integer status) {
        this.userId = userId;
        this.goodsId = goodsId;
        this.status = status;
    }
}
