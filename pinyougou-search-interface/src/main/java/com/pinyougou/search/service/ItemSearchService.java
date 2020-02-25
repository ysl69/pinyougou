package com.pinyougou.search.service;

import com.pinyougou.pojo.TbItem;

import java.util.List;
import java.util.Map;

/**
 * @Author ysl
 * @Date 2019/7/3 9:06
 * @Description:
 **/
public interface ItemSearchService {


    /**
     * 根据搜索条件搜索内容展示数据返回
     * @param searchMap  页面传递过来的条件 执行查询
     * @return  返回一个Map 包括所有的数据（集合，规格数据 总页数 总个记录数。。。。。）
     */
    Map<String,Object> search(Map<String,Object> searchMap);


    /**
     * 更新数据到索引库中
     * @param itemList 就是数据
     */
    public void updateIndex(List<TbItem> itemList);


    /**
     * 删除同步索引数据
     * @param ids
     */
    public void deleteByIds(Long[] ids);
}
