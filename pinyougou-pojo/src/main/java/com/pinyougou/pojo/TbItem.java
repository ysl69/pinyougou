package com.pinyougou.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_item")
public class TbItem implements Serializable {
    /**
     * 商品id，同时也是商品编号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 商品卖点
     */
    @Column(name = "sell_point")
    private String sellPoint;

    /**
     * 商品价格，单位为：元
     */
    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "stock_count")
    private Integer stockCount;

    /**
     * 库存数量
     */
    @Column(name = "num")
    private Integer num;

    /**
     * 商品条形码
     */
    @Column(name = "barcode")
    private String barcode;

    /**
     * 商品图片
     */
    @Column(name = "image")
    private String image;

    /**
     * 所属类目，叶子类目 分类的ID
     */
    @Column(name = "categoryId")
    private Long categoryid;

    /**
     * 商品状态，1-正常，2-下架，3-删除
     */
    @Column(name = "status")
    private String status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "item_sn")
    private String itemSn;

    @Column(name = "cost_pirce")
    private BigDecimal costPirce;

    @Column(name = "market_price")
    private BigDecimal marketPrice;

    @Column(name = "is_default")
    private String isDefault;

    @Column(name = "goods_id")
    private Long goodsId;

    @Column(name = "seller_id")
    private String sellerId;

    @Column(name = "cart_thumbnail")
    private String cartThumbnail;

    /**
     * 冗余字段 存放三级分类名称
     */
    @Column(name = "category")
    private String category;

    /**
     * 冗余字段 存放品牌名称
     */
    @Column(name = "brand")
    private String brand;

    @Column(name = "spec")
    private String spec;

    /**
     * 冗余字段，用于存放商家的店铺名称
     */
    @Column(name = "seller")
    private String seller;

    private static final long serialVersionUID = 1L;

    /**
     * 获取商品id，同时也是商品编号
     *
     * @return id - 商品id，同时也是商品编号
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置商品id，同时也是商品编号
     *
     * @param id 商品id，同时也是商品编号
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取商品标题
     *
     * @return title - 商品标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置商品标题
     *
     * @param title 商品标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取商品卖点
     *
     * @return sell_point - 商品卖点
     */
    public String getSellPoint() {
        return sellPoint;
    }

    /**
     * 设置商品卖点
     *
     * @param sellPoint 商品卖点
     */
    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    /**
     * 获取商品价格，单位为：元
     *
     * @return price - 商品价格，单位为：元
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置商品价格，单位为：元
     *
     * @param price 商品价格，单位为：元
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return stock_count
     */
    public Integer getStockCount() {
        return stockCount;
    }

    /**
     * @param stockCount
     */
    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    /**
     * 获取库存数量
     *
     * @return num - 库存数量
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置库存数量
     *
     * @param num 库存数量
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取商品条形码
     *
     * @return barcode - 商品条形码
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * 设置商品条形码
     *
     * @param barcode 商品条形码
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * 获取商品图片
     *
     * @return image - 商品图片
     */
    public String getImage() {
        return image;
    }

    /**
     * 设置商品图片
     *
     * @param image 商品图片
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 获取所属类目，叶子类目 分类的ID
     *
     * @return categoryId - 所属类目，叶子类目 分类的ID
     */
    public Long getCategoryid() {
        return categoryid;
    }

    /**
     * 设置所属类目，叶子类目 分类的ID
     *
     * @param categoryid 所属类目，叶子类目 分类的ID
     */
    public void setCategoryid(Long categoryid) {
        this.categoryid = categoryid;
    }

    /**
     * 获取商品状态，1-正常，2-下架，3-删除
     *
     * @return status - 商品状态，1-正常，2-下架，3-删除
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置商品状态，1-正常，2-下架，3-删除
     *
     * @param status 商品状态，1-正常，2-下架，3-删除
     */
    public void setStatus(String status) {
        this.status = status;
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
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return item_sn
     */
    public String getItemSn() {
        return itemSn;
    }

    /**
     * @param itemSn
     */
    public void setItemSn(String itemSn) {
        this.itemSn = itemSn;
    }

    /**
     * @return cost_pirce
     */
    public BigDecimal getCostPirce() {
        return costPirce;
    }

    /**
     * @param costPirce
     */
    public void setCostPirce(BigDecimal costPirce) {
        this.costPirce = costPirce;
    }

    /**
     * @return market_price
     */
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    /**
     * @param marketPrice
     */
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * @return is_default
     */
    public String getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault
     */
    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * @return goods_id
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * @param goodsId
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * @return seller_id
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     * @param sellerId
     */
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * @return cart_thumbnail
     */
    public String getCartThumbnail() {
        return cartThumbnail;
    }

    /**
     * @param cartThumbnail
     */
    public void setCartThumbnail(String cartThumbnail) {
        this.cartThumbnail = cartThumbnail;
    }

    /**
     * 获取冗余字段 存放三级分类名称
     *
     * @return category - 冗余字段 存放三级分类名称
     */
    public String getCategory() {
        return category;
    }

    /**
     * 设置冗余字段 存放三级分类名称
     *
     * @param category 冗余字段 存放三级分类名称
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 获取冗余字段 存放品牌名称
     *
     * @return brand - 冗余字段 存放品牌名称
     */
    public String getBrand() {
        return brand;
    }

    /**
     * 设置冗余字段 存放品牌名称
     *
     * @param brand 冗余字段 存放品牌名称
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return spec
     */
    public String getSpec() {
        return spec;
    }

    /**
     * @param spec
     */
    public void setSpec(String spec) {
        this.spec = spec;
    }

    /**
     * 获取冗余字段，用于存放商家的店铺名称
     *
     * @return seller - 冗余字段，用于存放商家的店铺名称
     */
    public String getSeller() {
        return seller;
    }

    /**
     * 设置冗余字段，用于存放商家的店铺名称
     *
     * @param seller 冗余字段，用于存放商家的店铺名称
     */
    public void setSeller(String seller) {
        this.seller = seller;
    }
}