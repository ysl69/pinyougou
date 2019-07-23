package com.pinyougou.search.service;

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
}
