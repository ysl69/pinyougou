package com.itheima.es.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author ysl
 * @Date 2019/7/22 11:35
 * @Description:
 **/


@Document(indexName = "pinyougou",type="item")

public class TbItem implements Serializable {
    /**
     * 商品id，同时也是商品编号
     */
    @Id//文档唯一的ID  标识该字段为文档的唯一标识
    @Field(type = FieldType.Long) //标识 该数据也要作为字段进行展示
    private Long id;

    /**
     * 商品标题
     */
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart",type = FieldType.Text,copyTo = "keyword")
    private String title;


    @Field(type = FieldType.Long)
    private Long goodsId;

    /**
     * 冗余字段 存放三级分类名称  关键字 只能按照确切的词来搜索
     */
    @Field(type = FieldType.Keyword,copyTo = "keyword")
    private String category;

    /**
     * 冗余字段 存放品牌名称
     */
    @Field (type = FieldType.Keyword,copyTo = "keyword")
    private String brand;

    /**
     * 冗余字段，用于存放商家的店铺名称
     */
    @Field(type = FieldType.Keyword,copyTo = "keyword")
    private String seller;

    //要索引 对象类型
    @Field(index = true,type=FieldType.Object)
    private Map<String,String> specMap;

    //getter和setter


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Map<String, String> getSpecMap() {
        return specMap;
    }

    public void setSpecMap(Map<String, String> specMap) {
        this.specMap = specMap;
    }
}
