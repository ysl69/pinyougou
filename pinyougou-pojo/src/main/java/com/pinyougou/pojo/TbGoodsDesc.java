package com.pinyougou.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "tb_goods_desc")
public class TbGoodsDesc implements Serializable {
    /**
     * SPU_ID
     */
    @Id
    @Column(name = "goods_id")
    private Long goodsId;

    /**
     * 描述
     */
    @Column(name = "introduction")
    private String introduction;

    /**
     * 规格结果集，所有规格，包含isSelected
     */
    @Column(name = "specification_items")
    private String specificationItems;

    /**
     * 自定义属性（参数结果）
     */
    @Column(name = "custom_attribute_items")
    private String customAttributeItems;

    /**
     * 商品的图片信息和颜色信息存储
     */
    @Column(name = "item_images")
    private String itemImages;

    /**
     * 包装列表
     */
    @Column(name = "package_list")
    private String packageList;

    /**
     * 售后服务
     */
    @Column(name = "sale_service")
    private String saleService;

    private static final long serialVersionUID = 1L;

    /**
     * 获取SPU_ID
     *
     * @return goods_id - SPU_ID
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * 设置SPU_ID
     *
     * @param goodsId SPU_ID
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
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

    /**
     * 获取规格结果集，所有规格，包含isSelected
     *
     * @return specification_items - 规格结果集，所有规格，包含isSelected
     */
    public String getSpecificationItems() {
        return specificationItems;
    }

    /**
     * 设置规格结果集，所有规格，包含isSelected
     *
     * @param specificationItems 规格结果集，所有规格，包含isSelected
     */
    public void setSpecificationItems(String specificationItems) {
        this.specificationItems = specificationItems;
    }

    /**
     * 获取自定义属性（参数结果）
     *
     * @return custom_attribute_items - 自定义属性（参数结果）
     */
    public String getCustomAttributeItems() {
        return customAttributeItems;
    }

    /**
     * 设置自定义属性（参数结果）
     *
     * @param customAttributeItems 自定义属性（参数结果）
     */
    public void setCustomAttributeItems(String customAttributeItems) {
        this.customAttributeItems = customAttributeItems;
    }

    /**
     * 获取商品的图片信息和颜色信息存储
     *
     * @return item_images - 商品的图片信息和颜色信息存储
     */
    public String getItemImages() {
        return itemImages;
    }

    /**
     * 设置商品的图片信息和颜色信息存储
     *
     * @param itemImages 商品的图片信息和颜色信息存储
     */
    public void setItemImages(String itemImages) {
        this.itemImages = itemImages;
    }

    /**
     * 获取包装列表
     *
     * @return package_list - 包装列表
     */
    public String getPackageList() {
        return packageList;
    }

    /**
     * 设置包装列表
     *
     * @param packageList 包装列表
     */
    public void setPackageList(String packageList) {
        this.packageList = packageList;
    }

    /**
     * 获取售后服务
     *
     * @return sale_service - 售后服务
     */
    public String getSaleService() {
        return saleService;
    }

    /**
     * 设置售后服务
     *
     * @param saleService 售后服务
     */
    public void setSaleService(String saleService) {
        this.saleService = saleService;
    }
}