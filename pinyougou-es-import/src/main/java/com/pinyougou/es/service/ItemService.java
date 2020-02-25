package com.pinyougou.es.service;

/**
 * @Author ysl
 * @Date 2019/7/2 21:47
 * @Description:
 **/


public interface ItemService {
    /**
     * 从数据库中获取数据 导入到ES的索引库
     */
    public void ImportDataToEs();
}
