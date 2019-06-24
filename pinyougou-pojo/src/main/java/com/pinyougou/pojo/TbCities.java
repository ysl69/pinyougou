package com.pinyougou.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "tb_cities")
public class TbCities implements Serializable {
    /**
     * 唯一ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 城市ID
     */
    @Column(name = "cityid")
    private String cityid;

    /**
     * 城市名称
     */
    @Column(name = "city")
    private String city;

    /**
     * 省份ID
     */
    @Column(name = "provinceid")
    private String provinceid;

    private static final long serialVersionUID = 1L;

    /**
     * 获取唯一ID
     *
     * @return id - 唯一ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置唯一ID
     *
     * @param id 唯一ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取城市ID
     *
     * @return cityid - 城市ID
     */
    public String getCityid() {
        return cityid;
    }

    /**
     * 设置城市ID
     *
     * @param cityid 城市ID
     */
    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    /**
     * 获取城市名称
     *
     * @return city - 城市名称
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置城市名称
     *
     * @param city 城市名称
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取省份ID
     *
     * @return provinceid - 省份ID
     */
    public String getProvinceid() {
        return provinceid;
    }

    /**
     * 设置省份ID
     *
     * @param provinceid 省份ID
     */
    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }
}