package com.pinyougou.core.service;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * 核心接口
 *
 * @author 三国的包子
 * @version 1.0
 * @package com.pinyougou.core.service *
 * @since 1.0
 */
public interface CoreService<T> {
    /**
     * 添加数据
     * @param record
     * @return
     */
     int insert(T record);

    /**
     *  添加数据
     * @param record
     * @return
     */
     void add(T record);

    /**
     * 忽略空添加数据
     * @param record
     * @return
     */
     int insertSelective(T record);


    /**
     * 根据实体对象作为条件来删除  条件为等号
     * @param record
     * @return
     */
    int delete(T record);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    void delete(Object[] ids);

    /**
     * 根据主键来删除
     * @param key
     * @return
     */
    int deleteByPrimaryKey(Object key);

    /**
     * 根据条件来删除
     * @param example
     * @return
     */
    int deleteByExample(Object example);


    /**
     * 根据条件 查询一条记录
     * @param record
     * @return
     */
    T selectOne(T record);


    /**
     * 根据主键来查询
     * @param id
     * @return
     */
    T findOne(Object id);



    /**
     * 根据条件查询列表记录  条件使用等号
     * @param record  等号条件
     * @return
     */
    List<T> select(T record);


    /**
     * 查询所有
     * @return
     */
    List<T> selectAll();

    List<T> findAll();




    /**
     * 根据主键来查询
     * @param key
     * @return
     */
    T selectByPrimaryKey(Object key);


    /**
     * 根据条件来查询
     * @param example  这个为任意的条件
     * @return
     */
    List<T> selectByExample(Object example);



    /**
     * 修改 等同于updateByPrimaryKey
     * @param record
     * @return
     */
    void update(T record);

    /**
     * 根据主键更新
     * @param record  要更新的数据  一定要有主键的值  null 也会更新进去
     * @return
     */
    int updateByPrimaryKey(T record);


    /**
     * 根据主键来更新  更新的条件为 非空不更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(T record);


    /**
     * 根据条件来更新  为空也要更新
     * @param record   要更新后的数据对象
     * @param example  查询条件
     * @return
     */
    int updateByExample(@Param("record") T record, @Param("example") Object example);


    /**
     * 根据条件来更新  为空不更新
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") T record, @Param("example") Object example);













}
