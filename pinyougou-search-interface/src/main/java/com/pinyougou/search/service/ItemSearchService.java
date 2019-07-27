package com.pinyougou.search.service;

import com.pinyougou.pojo.TbItem;

import java.util.List;
import java.util.Map;

/**
 * @Author ysl
 * @Date 2019/7/23 10:08
 * @Description:
 **/
public interface ItemSearchService {


    /**
     *  根据搜索条件搜索内容展示数据返回
     * @param searchMap
     * @return
     */
    Map<String,Object> search(Map<String,Object> searchMap);


    /**
     * 更新数据到索引库中
     * @param items  数据
     */
    public void updateIndex(List<TbItem> items);


    /**
     * 商品删除同步索引数据
     * @param ids
     */
    void deleteByIds(Long[] ids);
}
