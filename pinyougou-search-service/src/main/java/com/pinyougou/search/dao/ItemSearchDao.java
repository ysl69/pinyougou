package com.pinyougou.search.dao;

import com.pinyougou.pojo.TbItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author ysl
 * @Date 2019/7/5 21:37
 * @Description:
 **/


public interface ItemSearchDao extends ElasticsearchRepository<TbItem,Long> {
}
