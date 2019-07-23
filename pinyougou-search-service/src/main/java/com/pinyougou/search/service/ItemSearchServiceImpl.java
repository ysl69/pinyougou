package com.pinyougou.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ysl
 * @Date 2019/7/23 10:12
 * @Description:
 **/

@Service//dubbo的注解
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;



    @Override
    public Map<String, Object> search(Map<String, Object> searchMap) {

        HashMap<String, Object> resultMap = new HashMap<>();

        //1.获取关键字
        String keywords = (String) searchMap.get("keywords");

        //2.创建搜索查询对象 的构建对象
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();

        //3.创建并添加查询条件 匹配查询
        searchQueryBuilder.withQuery(QueryBuilders.matchQuery("keyword",keywords));

        //4.构建查询对象pinyougou-search-web
        NativeSearchQuery searchQuery = searchQueryBuilder.build();

        //5.执行查询
        AggregatedPage<TbItem> tbItems = elasticsearchTemplate.queryForPage(searchQuery, TbItem.class);

        //6.获取结果集 返回
        List<TbItem> itemList = tbItems.getContent();
        long totalElements = tbItems.getTotalElements();
        int totalPages = tbItems.getTotalPages();
        resultMap.put("rows",itemList);
        resultMap.put("total",totalElements);
        resultMap.put("totalPages",totalPages);

        return resultMap;
    }
}
