package com.pinyougou.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_seckill_goods")
public class TbSeckillGoods implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * spu ID
     */
    @Column(name = "goods_id")
    private Long goodsId;

    /**
     * sku ID
     */
    @Column(name = "item_id")
    private Long itemId;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 商品图片
     */
    @Column(name = "small_pic")
    private String smallPic;

    /**
     * 原价格
     */
    @Column(name = "price")
    private BigDecimal price;

    /**
     * 秒杀价格
     */
    @Column(name = "cost_price")
    private BigDecimal costPrice;

    /**
     * 商家ID
     */
    @Column(name = "seller_id")
    private String sellerId;

    /**
     * 添加日期
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 审核日期
     */
    @Column(name = "check_time")
    private Date checkTime;

    /**
     * 审核状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 秒杀商品库存数量 
     */
    @Column(name = "num")
    private Integer num;

    /**
     * 剩余库存数
     */
    @Column(name = "stock_count")
    private Integer stockCount;

    /**
     * 描述
     */
    @Column(name = "introduction")
    private String introduction;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取spu ID
     *
     * @return goods_id - spu ID
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * 设置spu ID
     *
     * @param goodsId spu ID
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取sku ID
     *
     * @return item_id - sku ID
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * 设置sku ID
     *
     * @param itemId sku ID
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取商品图片
     *
     * @return small_pic - 商品图片
     */
    public String getSmallPic() {
        return smallPic;
    }

    /**
     * 设置商品图片
     *
     * @param smallPic 商品图片
     */
    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }

    /**
     * 获取原价格
     *
     * @return price - 原价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置原价格
     *
     * @param price 原价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取秒杀价格
     *
     * @return cost_price - 秒杀价格
     */
    public BigDecimal getCostPrice() {
        return costPrice;
    }

    /**
     * 设置秒杀价格
     *
     * @param costPrice 秒杀价格
     */
    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
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

    /**
     * 获取添加日期
     *
     * @return create_time - 添加日期
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置添加日期
     *
     * @param createTime 添加日期
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取审核日期
     *
     * @return check_time - 审核日期
     */
    public Date getCheckTime() {
        return checkTime;
    }

    /**
     * 设置审核日期
     *
     * @param checkTime 审核日期
     */
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    /**
     * 获取审核状态
     *
     * @return status - 审核状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置审核状态
     *
     * @param status 审核状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取开始时间
     *
     * @return start_time - 开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置开始时间
     *
     * @param startTime 开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取结束时间
     *
     * @return end_time - 结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置结束时间
     *
     * @param endTime 结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取秒杀商品库存数量 
     *
     * @return num - 秒杀商品库存数量 
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置秒杀商品库存数量 
     *
     * @param num 秒杀商品库存数量 
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取剩余库存数
     *
     * @return stock_count - 剩余库存数
     */
    public Integer getStockCount() {
        return stockCount;
    }

    /**
     * 设置剩余库存数
     *
     * @param stockCount 剩余库存数
     */
    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    /**
     * 获取描述
     *
     * @return introduction - 描述
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * 设置描述
     *
     * @param introduction 描述
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}