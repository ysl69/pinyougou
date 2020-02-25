package com.itheima.es.dao;

import com.itheima.model.TbItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author ysl
 * @Date 2019/7/2 11:16
 * @Description:
 **/
public interface ItemDao extends ElasticsearchRepository<TbItem,Long> {
}
