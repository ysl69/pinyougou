package com.pinyougou.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "tb_content_category")
public class TbContentCategory implements Serializable {
    /**
     * 类目ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 分类名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 分组
     */
    @Column(name = "content_group")
    private String contentGroup;

    /**
     * 分类key
     */
    @Column(name = "content_key")
    private String contentKey;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;

    private static final long serialVersionUID = 1L;

    /**
     * 获取类目ID
     *
     * @return id - 类目ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置类目ID
     *
     * @param id 类目ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取分类名称
     *
     * @return name - 分类名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置分类名称
     *
     * @param name 分类名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取分组
     *
     * @return content_group - 分组
     */
    public String getContentGroup() {
        return contentGroup;
    }

    /**
     * 设置分组
     *
     * @param contentGroup 分组
     */
    public void setContentGroup(String contentGroup) {
        this.contentGroup = contentGroup;
    }

    /**
     * 获取分类key
     *
     * @return content_key - 分类key
     */
    public String getContentKey() {
        return contentKey;
    }

    /**
     * 设置分类key
     *
     * @param contentKey 分类key
     */
    public void setContentKey(String contentKey) {
        this.contentKey = contentKey;
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
}