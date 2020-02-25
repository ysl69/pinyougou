package com.pinyougou.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_seckill_order")
public class TbSeckillOrder implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 秒杀商品ID
     */
    @Column(name = "seckill_id")
    private Long seckillId;

    /**
     * 支付金额
     */
    @Column(name = "money")
    private BigDecimal money;

    /**
     * 用户
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 商家
     */
    @Column(name = "seller_id")
    private String sellerId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 支付时间
     */
    @Column(name = "pay_time")
    private Date payTime;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 收货人地址
     */
    @Column(name = "receiver_address")
    private String receiverAddress;

    /**
     * 收货人电话
     */
    @Column(name = "receiver_mobile")
    private String receiverMobile;

    /**
     * 收货人
     */
    @Column(name = "receiver")
    private String receiver;

    /**
     * 交易流水
     */
    @Column(name = "transaction_id")
    private String transactionId;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取秒杀商品ID
     *
     * @return seckill_id - 秒杀商品ID
     */
    public Long getSeckillId() {
        return seckillId;
    }

    /**
     * 设置秒杀商品ID
     *
     * @param seckillId 秒杀商品ID
     */
    public void setSeckillId(Long seckillId) {
        this.seckillId = seckillId;
    }

    /**
     * 获取支付金额
     *
     * @return money - 支付金额
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置支付金额
     *
     * @param money 支付金额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取用户
     *
     * @return user_id - 用户
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户
     *
     * @param userId 用户
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取商家
     *
     * @return seller_id - 商家
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     * 设置商家
     *
     * @param sellerId 商家
     */
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取支付时间
     *
     * @return pay_time - 支付时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * 设置支付时间
     *
     * @param payTime 支付时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取收货人地址
     *
     * @return receiver_address - 收货人地址
     */
    public String getReceiverAddress() {
        return receiverAddress;
    }

    /**
     * 设置收货人地址
     *
     * @param receiverAddress 收货人地址
     */
    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    /**
     * 获取收货人电话
     *
     * @return receiver_mobile - 收货人电话
     */
    public String getReceiverMobile() {
        return receiverMobile;
    }

    /**
     * 设置收货人电话
     *
     * @param receiverMobile 收货人电话
     */
    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    /**
     * 获取收货人
     *
     * @return receiver - 收货人
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * 设置收货人
     *
     * @param receiver 收货人
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * 获取交易流水
     *
     * @return transaction_id - 交易流水
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * 设置交易流水
     *
     * @param transactionId 交易流水
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}