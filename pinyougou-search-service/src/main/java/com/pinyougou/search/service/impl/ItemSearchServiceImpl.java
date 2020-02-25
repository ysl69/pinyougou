package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;

import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.dao.ItemSearchDao;
import com.pinyougou.search.service.ItemSearchService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ysl
 * @Date 2019/7/3 9:07
 * @Description:
 **/

@Service
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ItemSearchDao dao;

    @Override
    public Map<String, Object> search(Map<String, Object> searchMap) {
        Map<String, Object> resultMap = new HashMap<>();

        // 1.获取关键字
        String keywords = (String) searchMap.get("keywords");

        // 2.创建搜索对象 的构建对象
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();

        //3.创建并添加查询条件 匹配查询
        //ativeSearchQueryBuilder.withIndices("pinyougou") 指定索引 默认查询所有的索引
        //nativeSearchQueryBuilder.withTypes("item") 指定类型 默认 查询所有的类型
        //searchQueryBuilder.withQuery(QueryBuilders.matchQuery("keyword",keywords));
        searchQueryBuilder.withQuery(QueryBuilders.multiMatchQuery(keywords, "seller", "category", "brand", "title"));

        searchQueryBuilder.addAggregation(AggregationBuilders.terms("category_group").field("category").size(50));

        //3.1 设置高亮显示的域（字段） 设置 前缀 和后缀
        searchQueryBuilder
                .withHighlightFields(new HighlightBuilder.Field("title"))
                .withHighlightBuilder(new HighlightBuilder().preTags("<em style=\"color:red\">").postTags("</em>"));

        //3.2 过滤查询  ----商品分类的过滤查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        String category = (String) searchMap.get("category");
        if (StringUtils.isNotBlank(category)) {
            // 不为空才设置过滤
            //searchQueryBuilder.withFilter(QueryBuilders.termQuery("category",category));
            boolQueryBuilder.filter(QueryBuilders.termQuery("category", category));
        }

        //3.3 过滤查询 ----商品的品牌的过滤查询
        String brand = (String) searchMap.get("brand");
        if (StringUtils.isNotBlank(brand)) {
            //不为空才设置过滤
            // builder.withFilter(QueryBuilders.termQuery("brand",brand));
            boolQueryBuilder.filter(QueryBuilders.termQuery("brand", brand));
        }

        //3.4 过滤查询 ----规格的过滤查询 获取到规格的名称 和规格的值  执行过滤查询
        Map<String, String> spec = (Map<String, String>) searchMap.get("spec");//{"网络":"移动4G","机身内存":"16G"}
        if (spec != null) {
            for (String key : spec.keySet()) {
                //该路径上去查询
                TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("specMap." + key + ".keyword",
                        spec.get(key));
                boolQueryBuilder.filter(termQueryBuilder);
            }
        }


        //3.5过滤查询  价格区间过滤
        String price = (String) searchMap.get("price");
        if (StringUtils.isNotBlank(price)) {
            String[] split = price.split("-");
            if ("*".equals(split[1])) {
                // 价格大于
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(split[0]));
            }else {
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").from(split[0], true).to(split[1], true));
            }
        }

        searchQueryBuilder.withFilter(boolQueryBuilder);

        //4.构建查询对象
        NativeSearchQuery searchQuery = searchQueryBuilder.build();


        // 设置分页条件
        Integer pageNo = (Integer) searchMap.get("pageNo");
        Integer pageSize = (Integer) searchMap.get("pageSize");
        if (pageNo==null){
            pageNo=1;
        }
        if (pageSize==null){
            pageSize=40;
        }
        //参数1 为当前页码 值为0 ：表示第一页
        //参数2 为每页显示的行
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);
        searchQuery.setPageable(pageable);



        //排序条件 价格排序
        String sortFiled = (String) searchMap.get("sortFiled");//排序的字段
        String sortType = (String) searchMap.get("sortType");//排序的类型 DESC  ASC
        if (StringUtils.isNotBlank(sortFiled) && StringUtils.isNotBlank(sortType)){
            if (sortType.equals("ASC")){
                Sort sort = new Sort(Sort.Direction.ASC, sortFiled);
                searchQuery.addSort(sort);
            }else if (sortType.equals("DESC")){
                Sort sort = new Sort(Sort.Direction.DESC, sortFiled);
                searchQuery.addSort(sort);
            }else {
                System.out.println("不排序");
            }
        }


        //5.执行查询
        AggregatedPage<TbItem> tbItems = elasticsearchTemplate.queryForPage(searchQuery, TbItem.class, new SearchResultMapper() {
            //自定义 进行结果集的映射   //获取高亮
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                //1.创建当前的页的集合
                List<T> content = new ArrayList<>();

                //2.获取查询的结果 获取总记录数
                SearchHits hits = searchResponse.getHits();

                //3.判断是否有记录 如果没有 返回
                if (hits==null || hits.getHits().length<=0){
                    return new AggregatedPageImpl(content);
                }
                //4.有记录  获取高亮的数据
                for (SearchHit hit : hits) {
                    // 有高亮
                    String sourceAsString = hit.getSourceAsString();//就是每一个文档对应的json数据
                    //btitem的数据JSON格式
                    TbItem tbItem = JSON.parseObject(sourceAsString, TbItem.class);

                    //获取高亮
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    //获取高亮的域为title的高亮对象
                    HighlightField highlightField = highlightFields.get("title");
                    //获取高亮的字段的数据对象
                    if (highlightField!=null){

                        StringBuffer sb = new StringBuffer();//高亮的数据
                        //获取高亮的碎片
                        Text[] fragments = highlightField.getFragments();

                        if (fragments != null){
                            for (Text fragment : fragments) {
                                sb.append(fragment.string());//获取到的高亮碎片的值<em styple="colore:red">
                            }
                        }

                        //不为空的时候 存储值
                        if(StringUtils.isNotBlank(sb.toString())){
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

        // 获取分组结果
        Aggregation category_group = tbItems.getAggregation("category_group");
        StringTerms stringTerms =(StringTerms)category_group;

        // 商品分类分组结果
        ArrayList<String> categoryList = new ArrayList<>();
        if (stringTerms != null){
            List<StringTerms.Bucket> buckets = stringTerms.getBuckets();
            for (StringTerms.Bucket bucket : buckets) {
                String keyAsString = bucket.getKeyAsString(); // 获取到的解释分类的名称
                categoryList.add(keyAsString);
            }
        }


        //6.获取结果集 返回

        // 获取第一个分类下的所有品牌和规格列表
        //判断 商品分类是否为空 如果不为空 根据点击到的商品分类查询 该分类下的所有的品牌和规格的列表
        if (StringUtils.isNotBlank(category)){
            Map map = searchBrandAndSpecList(categoryList.get(0));
            resultMap.putAll(map);
        }else {
            //否则 查询默认的商品分类下的品牌和规格的列表
            if (categoryList!=null && categoryList.size()>0){
                Map map = searchBrandAndSpecList(categoryList.get(0));
                resultMap.putAll(map);
            }else {
                resultMap.put("specList", new HashMap<>());
                resultMap.put("brandList",new HashMap<>());
            }
        }


        //7.设置结果集到map 返回(总页数 总记录数 当前页的集合 ......)
        resultMap.put("rows",tbItems.getContent()); //当前页的集合
        resultMap.put("total",tbItems.getTotalElements());//总记录数
        resultMap.put("totalPages",tbItems.getTotalPages());//总页数
        resultMap.put("categoryList",categoryList);

        return resultMap;
    }


    /**
     * 更新数据到索引库中
     * @param itemList 就是数据
     */
    @Override
    public void updateIndex(List<TbItem> itemList) {
        //先设置map 再一次性插入
        for (TbItem tbItem : itemList) {
            String spec = tbItem.getSpec();
            Map map = JSON.parseObject(spec,Map.class);
            tbItem.setSpecMap(map);
        }
        dao.saveAll(itemList);
    }


    /**
     * 根据SPU的IDs数组 进行删除
     * @param ids
     */
    @Override
    public void deleteByIds(Long[] ids) {
        //ids  里面是goods_id的值
        //delte from tb_item where goods_id in (1,2,) 从ES 删除
        DeleteQuery query = new DeleteQuery();
        //删除多个goodsid
        query.setQuery(QueryBuilders.termsQuery("goodsId",ids));
        //根据删除条件 索引名 和 类型
        elasticsearchTemplate.delete(query,TbItem.class);
    }


    /**
     * 查询品牌和规格列表
     * @param category 分类名称
     * @return
     */
    private Map searchBrandAndSpecList(String category){
        Map map = new HashMap();
        //获取模板ID
        Long typeId = (Long) redisTemplate.boundHashOps("itemCat").get(category);
        if (typeId != null){
            // 根据模板id查询品牌列表
            List brandList = (List) redisTemplate.boundHashOps("brandList").get(typeId);
            //返回值添加品牌列表
            map.put("brandList",brandList);

            // 根据模板id查询规格列表
            List specList = (List) redisTemplate.boundHashOps("specList").get(typeId);
            // 返回值添加规格列表
            map.put("specList",specList);
        }
        return map;
    }
}
