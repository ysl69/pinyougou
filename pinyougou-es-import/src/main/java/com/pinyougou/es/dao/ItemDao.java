package com.pinyougou.es.dao;

import com.pinyougou.pojo.TbItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author ysl
 * @Date 2019/7/2 21:50
 * @Description:
 **/


public interface ItemDao extends ElasticsearchRepository<TbItem,Long> {
}
