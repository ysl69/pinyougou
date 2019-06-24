package com.pinyougou.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "tb_type_template")
public class TbTypeTemplate implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 模板名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 关联规格
     */
    @Column(name = "spec_ids")
    private String specIds;

    /**
     * 关联品牌
     */
    @Column(name = "brand_ids")
    private String brandIds;

    /**
     * 自定义属性
     */
    @Column(name = "custom_attribute_items")
    private String customAttributeItems;

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
     * 获取模板名称
     *
     * @return name - 模板名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置模板名称
     *
     * @param name 模板名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取关联规格
     *
     * @return spec_ids - 关联规格
     */
    public String getSpecIds() {
        return specIds;
    }

    /**
     * 设置关联规格
     *
     * @param specIds 关联规格
     */
    public void setSpecIds(String specIds) {
        this.specIds = specIds;
    }

    /**
     * 获取关联品牌
     *
     * @return brand_ids - 关联品牌
     */
    public String getBrandIds() {
        return brandIds;
    }

    /**
     * 设置关联品牌
     *
     * @param brandIds 关联品牌
     */
    public void setBrandIds(String brandIds) {
        this.brandIds = brandIds;
    }

    /**
     * 获取自定义属性
     *
     * @return custom_attribute_items - 自定义属性
     */
    public String getCustomAttributeItems() {
        return customAttributeItems;
    }

    /**
     * 设置自定义属性
     *
     * @param customAttributeItems 自定义属性
     */
    public void setCustomAttributeItems(String customAttributeItems) {
        this.customAttributeItems = customAttributeItems;
    }
}