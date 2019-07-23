package com.pinyougou.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.pinyougou.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
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
        //searchQueryBuilder.withQuery(QueryBuilders.matchQuery("keyword",keywords));
        //高亮时设置查询多个指定的域
        searchQueryBuilder.withQuery(QueryBuilders.multiMatchQuery(keywords,"seller","category","brand","title"));

        searchQueryBuilder.addAggregation(AggregationBuilders.terms("category_group").field("category").size(50));


        //3.1设置高亮显示的域（字段）  设置前缀和后缀
        searchQueryBuilder.withHighlightFields(new HighlightBuilder.Field("title"))
                .withHighlightBuilder(new HighlightBuilder()
                        .preTags("<em style=\"color:red\">")
                        .postTags("</em>"));


        //4.构建查询对象pinyougou-search-web
        NativeSearchQuery searchQuery = searchQueryBuilder.build();

        //5.执行查询  自定义数据映射封装
        AggregatedPage<TbItem> tbItems = elasticsearchTemplate.queryForPage(searchQuery, TbItem.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHits hits = searchResponse.getHits();
                List<T> content = new ArrayList<>();
                //如果没有搜索到记录
                if (hits==null || hits.getHits().length<=0){
                    return new AggregatedPageImpl<>(content);
                }
                for (SearchHit hit : hits) {
                    //就是每一个文档对应的json数据
                    String sourceAsString = hit.getSourceAsString();
                    TbItem tbItem = JSON.parseObject(sourceAsString, TbItem.class);

                    // 获取高亮
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    //获取高亮的域为title的高亮对象
                    HighlightField highlightField = highlightFields.get("title");

                    if (highlightField != null){
                        //获取高亮的碎片
                        Text[] fragments = highlightField.getFragments();
                        StringBuffer sb = new StringBuffer();

                        if (fragments != null){
                            for (Text fragment : fragments) {
                                //获取到的高亮碎片的值<em styple="colore:red">
                                sb.append(fragment.string());
                            }
                        }
                        //不为空的时候 存储值if
                        if (StringUtils.isNotBlank(sb.toString())){
                            tbItem.setTitle(sb.toString());
                        }
                    }
                    content.add((T)tbItem);
                }
                AggregatedPageImpl<T> aggregatedPage = new AggregatedPageImpl<>(content, pageable, hits.getTotalHits(),
                        searchResponse.getAggregations(),
                        searchResponse.getScrollId());

                return aggregatedPage;
            }
        });

        //获取分组结果
        Aggregation category_group = tbItems.getAggregation("category_group");
        StringTerms stringTerms = (StringTerms) category_group;

        //商品分类分组结果
        List<String> categoryList = new ArrayList<>();
        if (stringTerms != null){
            List<StringTerms.Bucket> buckets = stringTerms.getBuckets();
            for (StringTerms.Bucket bucket : buckets) {
                //获取到的就是分类的名
                String keyAsString = bucket.getKeyAsString();

                categoryList.add(keyAsString);
            }
        }


        //获取第一个分类下的所有的品牌和规格列表
        Map map = searchBrandAndSpecList(categoryList.get(0));
        resultMap.putAll(map);


        //6.获取结果集 返回
        List<TbItem> itemList = tbItems.getContent();
        long totalElements = tbItems.getTotalElements();
        int totalPages = tbItems.getTotalPages();
        resultMap.put("rows",itemList);
        resultMap.put("total",totalElements);
        resultMap.put("totalPages",totalPages);
        resultMap.put("categoryList",categoryList);
        return resultMap;
    }



    @Autowired
    private RedisTemplate redisTemplate;


    /**
     *  查询品牌和规格列表
     * @param category 分类名称
     * @return
     */
    public Map searchBrandAndSpecList(String category){
        Map map = new HashMap();
        //获取模板ID
        Long typeId = (Long) redisTemplate.boundHashOps("itemCat").get(category);

        if (typeId != null){
            //根据模板Id查询品牌列表
            List brandList = (List) redisTemplate.boundHashOps("brandList").get(typeId);
            //返回值添加品牌列表
            map.put("brandList",brandList);

            //根据模板Id查询规格列表
            List specList = (List) redisTemplate.boundHashOps("specList").get(typeId);
            map.put("specList",specList);
        }
        return map;
    }
}
