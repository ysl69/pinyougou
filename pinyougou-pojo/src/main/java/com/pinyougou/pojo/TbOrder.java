package com.pinyougou.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_order")
public class TbOrder implements Serializable {
    /**
     * 订单id
     */
    @Id
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
     */
    @Column(name = "payment")
    private BigDecimal payment;

    /**
     * 支付类型，1、在线支付，2、货到付款
     */
    @Column(name = "payment_type")
    private String paymentType;

    /**
     * 邮费。精确到2位小数;单位:元。如:200.07，表示:200元7分
     */
    @Column(name = "post_fee")
    private String postFee;

    /**
     * 状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭,7、待评价
     */
    @Column(name = "status")
    private String status;

    /**
     * 订单创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 订单更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 付款时间
     */
    @Column(name = "payment_time")
    private Date paymentTime;

    /**
     * 发货时间
     */
    @Column(name = "consign_time")
    private Date consignTime;

    /**
     * 交易完成时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 交易关闭时间
     */
    @Column(name = "close_time")
    private Date closeTime;

    /**
     * 物流名称
     */
    @Column(name = "shipping_name")
    private String shippingName;

    /**
     * 物流单号
     */
    @Column(name = "shipping_code")
    private String shippingCode;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 买家留言
     */
    @Column(name = "buyer_message")
    private String buyerMessage;

    /**
     * 买家昵称
     */
    @Column(name = "buyer_nick")
    private String buyerNick;

    /**
     * 买家是否已经评价
     */
    @Column(name = "buyer_rate")
    private String buyerRate;

    /**
     * 收货人地区名称(省，市，县)街道
     */
    @Column(name = "receiver_area_name")
    private String receiverAreaName;

    /**
     * 收货人手机
     */
    @Column(name = "receiver_mobile")
    private String receiverMobile;

    /**
     * 收货人邮编
     */
    @Column(name = "receiver_zip_code")
    private String receiverZipCode;

    /**
     * 收货人
     */
    @Column(name = "receiver")
    private String receiver;

    /**
     * 过期时间，定期清理
     */
    @Column(name = "expire")
    private Date expire;

    /**
     * 发票类型(普通发票，电子发票，增值税发票)
     */
    @Column(name = "invoice_type")
    private String invoiceType;

    /**
     * 订单来源：1:app端，2：pc端，3：M端，4：微信端，5：手机qq端
     */
    @Column(name = "source_type")
    private String sourceType;

    /**
     * 商家ID
     */
    @Column(name = "seller_id")
    private String sellerId;

    private static final long serialVersionUID = 1L;

    /**
     * 获取订单id
     *
     * @return order_id - 订单id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置订单id
     *
     * @param orderId 订单id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
     *
     * @return payment - 实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
     */
    public BigDecimal getPayment() {
        return payment;
    }

    /**
     * 设置实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
     *
     * @param payment 实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
     */
    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    /**
     * 获取支付类型，1、在线支付，2、货到付款
     *
     * @return payment_type - 支付类型，1、在线支付，2、货到付款
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * 设置支付类型，1、在线支付，2、货到付款
     *
     * @param paymentType 支付类型，1、在线支付，2、货到付款
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * 获取邮费。精确到2位小数;单位:元。如:200.07，表示:200元7分
     *
     * @return post_fee - 邮费。精确到2位小数;单位:元。如:200.07，表示:200元7分
     */
    public String getPostFee() {
        return postFee;
    }

    /**
     * 设置邮费。精确到2位小数;单位:元。如:200.07，表示:200元7分
     *
     * @param postFee 邮费。精确到2位小数;单位:元。如:200.07，表示:200元7分
     */
    public void setPostFee(String postFee) {
        this.postFee = postFee;
    }

    /**
     * 获取状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭,7、待评价
     *
     * @return status - 状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭,7、待评价
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭,7、待评价
     *
     * @param status 状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭,7、待评价
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取订单创建时间
     *
     * @return create_time - 订单创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置订单创建时间
     *
     * @param createTime 订单创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取订单更新时间
     *
     * @return update_time - 订单更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置订单更新时间
     *
     * @param updateTime 订单更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取付款时间
     *
     * @return payment_time - 付款时间
     */
    public Date getPaymentTime() {
        return paymentTime;
    }

    /**
     * 设置付款时间
     *
     * @param paymentTime 付款时间
     */
    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    /**
     * 获取发货时间
     *
     * @return consign_time - 发货时间
     */
    public Date getConsignTime() {
        return consignTime;
    }

    /**
     * 设置发货时间
     *
     * @param consignTime 发货时间
     */
    public void setConsignTime(Date consignTime) {
        this.consignTime = consignTime;
    }

    /**
     * 获取交易完成时间
     *
     * @return end_time - 交易完成时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置交易完成时间
     *
     * @param endTime 交易完成时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取交易关闭时间
     *
     * @return close_time - 交易关闭时间
     */
    public Date getCloseTime() {
        return closeTime;
    }

    /**
     * 设置交易关闭时间
     *
     * @param closeTime 交易关闭时间
     */
    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    /**
     * 获取物流名称
     *
     * @return shipping_name - 物流名称
     */
    public String getShippingName() {
        return shippingName;
    }

    /**
     * 设置物流名称
     *
     * @param shippingName 物流名称
     */
    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    /**
     * 获取物流单号
     *
     * @return shipping_code - 物流单号
     */
    public String getShippingCode() {
        return shippingCode;
    }

    /**
     * 设置物流单号
     *
     * @param shippingCode 物流单号
     */
    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取买家留言
     *
     * @return buyer_message - 买家留言
     */
    public String getBuyerMessage() {
        return buyerMessage;
    }

    /**
     * 设置买家留言
     *
     * @param buyerMessage 买家留言
     */
    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    /**
     * 获取买家昵称
     *
     * @return buyer_nick - 买家昵称
     */
    public String getBuyerNick() {
        return buyerNick;
    }

    /**
     * 设置买家昵称
     *
     * @param buyerNick 买家昵称
     */
    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    /**
     * 获取买家是否已经评价
     *
     * @return buyer_rate - 买家是否已经评价
     */
    public String getBuyerRate() {
        return buyerRate;
    }

    /**
     * 设置买家是否已经评价
     *
     * @param buyerRate 买家是否已经评价
     */
    public void setBuyerRate(String buyerRate) {
        this.buyerRate = buyerRate;
    }

    /**
     * 获取收货人地区名称(省，市，县)街道
     *
     * @return receiver_area_name - 收货人地区名称(省，市，县)街道
     */
    public String getReceiverAreaName() {
        return receiverAreaName;
    }

    /**
     * 设置收货人地区名称(省，市，县)街道
     *
     * @param receiverAreaName 收货人地区名称(省，市，县)街道
     */
    public void setReceiverAreaName(String receiverAreaName) {
        this.receiverAreaName = receiverAreaName;
    }

    /**
     * 获取收货人手机
     *
     * @return receiver_mobile - 收货人手机
     */
    public String getReceiverMobile() {
        return receiverMobile;
    }

    /**
     * 设置收货人手机
     *
     * @param receiverMobile 收货人手机
     */
    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    /**
     * 获取收货人邮编
     *
     * @return receiver_zip_code - 收货人邮编
     */
    public String getReceiverZipCode() {
        return receiverZipCode;
    }

    /**
     * 设置收货人邮编
     *
     * @param receiverZipCode 收货人邮编
     */
    public void setReceiverZipCode(String receiverZipCode) {
        this.receiverZipCode = receiverZipCode;
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
     * 获取过期时间，定期清理
     *
     * @return expire - 过期时间，定期清理
     */
    public Date getExpire() {
        return expire;
    }

    /**
     * 设置过期时间，定期清理
     *
     * @param expire 过期时间，定期清理
     */
    public void setExpire(Date expire) {
        this.expire = expire;
    }

    /**
     * 获取发票类型(普通发票，电子发票，增值税发票)
     *
     * @return invoice_type - 发票类型(普通发票，电子发票，增值税发票)
     */
    public String getInvoiceType() {
        return invoiceType;
    }

    /**
     * 设置发票类型(普通发票，电子发票，增值税发票)
     *
     * @param invoiceType 发票类型(普通发票，电子发票，增值税发票)
     */
    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * 获取订单来源：1:app端，2：pc端，3：M端，4：微信端，5：手机qq端
     *
     * @return source_type - 订单来源：1:app端，2：pc端，3：M端，4：微信端，5：手机qq端
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * 设置订单来源：1:app端，2：pc端，3：M端，4：微信端，5：手机qq端
     *
     * @param sourceType 订单来源：1:app端，2：pc端，3：M端，4：微信端，5：手机qq端
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * 获取商家ID
     *
     * @return seller_id - 商家ID
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     * 设置商家ID
     *
     * @param sellerId 商家ID
     */
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}