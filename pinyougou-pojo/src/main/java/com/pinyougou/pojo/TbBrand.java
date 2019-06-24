package com.pinyougou.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author ysl
 * @Date 2019/6/23 22:13
 * @Description: TODO
 **/


@Table(name = "tb_brand")
public class TbBrand implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 品牌名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 品牌首字母
     */
    @Column(name = "first_char")
    private String firstChar;

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
     * 获取品牌名称
     *
     * @return name - 品牌名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置品牌名称
     *
     * @param name 品牌名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取品牌首字母
     *
     * @return first_char - 品牌首字母
     */
    public String getFirstChar() {
        return firstChar;
    }

    /**
     * 设置品牌首字母
     *
     * @param firstChar 品牌首字母
     */
    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }
}

