package com.itheima.es.dao;

import com.itheima.es.model.TbItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * @Author ysl
 * @Date 2019/7/22 14:16
 * @Description:
 **/


public interface ItemDao extends ElasticsearchCrudRepository<TbItem,Long> {
}
