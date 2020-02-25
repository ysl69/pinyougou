package com.pinyougou.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_pay_log")
public class TbPayLog implements Serializable {
    /**
     * 支付订单号
     */
    @Id
    @Column(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 支付完成时间
     */
    @Column(name = "pay_time")
    private Date payTime;

    /**
     * 支付金额（分）
     */
    @Column(name = "total_fee")
    private Long totalFee;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 交易号码
     */
    @Column(name = "transaction_id")
    private String transactionId;

    /**
     * 交易状态 0  1:已支付
     */
    @Column(name = "trade_state")
    private String tradeState;

    /**
     * 订单编号列表
     */
    @Column(name = "order_list")
    private String orderList;

    /**
     * 支付类型
     */
    @Column(name = "pay_type")
    private String payType;

    private static final long serialVersionUID = 1L;

    /**
     * 获取支付订单号
     *
     * @return out_trade_no - 支付订单号
     */
    public String getOutTradeNo() {
        return outTradeNo;
    }

    /**
     * 设置支付订单号
     *
     * @param outTradeNo 支付订单号
     */
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    /**
     * 获取创建日期
     *
     * @return create_time - 创建日期
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建日期
     *
     * @param createTime 创建日期
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取支付完成时间
     *
     * @return pay_time - 支付完成时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * 设置支付完成时间
     *
     * @param payTime 支付完成时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 获取支付金额（分）
     *
     * @return total_fee - 支付金额（分）
     */
    public Long getTotalFee() {
        return totalFee;
    }

    /**
     * 设置支付金额（分）
     *
     * @param totalFee 支付金额（分）
     */
    public void setTotalFee(Long totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取交易号码
     *
     * @return transaction_id - 交易号码
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * 设置交易号码
     *
     * @param transactionId 交易号码
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * 获取交易状态 0  1:已支付
     *
     * @return trade_state - 交易状态 0  1:已支付
     */
    public String getTradeState() {
        return tradeState;
    }

    /**
     * 设置交易状态 0  1:已支付
     *
     * @param tradeState 交易状态 0  1:已支付
     */
    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    /**
     * 获取订单编号列表
     *
     * @return order_list - 订单编号列表
     */
    public String getOrderList() {
        return orderList;
    }

    /**
     * 设置订单编号列表
     *
     * @param orderList 订单编号列表
     */
    public void setOrderList(String orderList) {
        this.orderList = orderList;
    }

    /**
     * 获取支付类型
     *
     * @return pay_type - 支付类型
     */
    public String getPayType() {
        return payType;
    }

    /**
     * 设置支付类型
     *
     * @param payType 支付类型
     */
    public void setPayType(String payType) {
        this.payType = payType;
    }
}