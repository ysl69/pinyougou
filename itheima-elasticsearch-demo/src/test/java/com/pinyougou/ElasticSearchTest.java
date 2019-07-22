package com.pinyougou;

import com.itheima.es.dao.ItemDao;
import com.itheima.es.model.TbItem;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ysl
 * @Date 2019/7/22 11:57
 * @Description:
 **/

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring-es.xml")
public class ElasticSearchTest {


    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Autowired
    private ItemDao itemDao;


    /**
     * 创建索引和映射
     */
    @Test
    public void testCreateIndexAndMapping(){
        //创建索引
        elasticsearchTemplate.createIndex(TbItem.class);

        //创建映射
        elasticsearchTemplate.putMapping(TbItem.class);
    }


    /**
     * 添加文档
     */
    @Test
    public void saveData(){
       /* TbItem tbItem = new TbItem();
        tbItem.setId(20000L);
        tbItem.setTitle("测试商品");
        itemDao.save(tbItem);*/


        for (int i = 0; i < 20; i++) {
            TbItem tbItem = new TbItem();
            tbItem.setId((long) i);
            tbItem.setTitle("测试商品"+i);
            tbItem.setCategory("商品分类"+i);
            tbItem.setBrand("三星"+i);
            tbItem.setSeller("三星旗舰店"+i);

            Map<String, String> map = new HashMap<>();
            map.put("网络制式","移动4G");
            map.put("机身内存","16G");
            tbItem.setSpecMap(map);

            itemDao.save(tbItem);
        }
    }


    /**
     * 删除文档
     */
    @Test
    public void deleteById(){
        itemDao.deleteById(20000L);
    }


    /**
     * 修改数据  修改数据和 保存数据一样。当有相同的ID 时直接就更新
     */
    @Test
    public void update(){
        TbItem tbItem = new TbItem();
        tbItem.setId(30000L);
        tbItem.setTitle("测试商品1111");
        tbItem.setCategory("商品分类111");
        tbItem.setBrand("三星");
        tbItem.setSeller("三星旗舰店");

        itemDao.save(tbItem);
    }


    /**
     * 查询  查询所有
     */
    @Test
    public void QueryAll(){
        Iterable<TbItem> all = itemDao.findAll();
        for (TbItem tbItem : all) {
            System.out.println(tbItem.getTitle());
        }
    }


    /**
     * 查询  根据ID 查询
     */
    @Test
    public void QueryById(){
        System.out.println(itemDao.findById(30000L).get().getTitle());
    }


    /**
     * 分页查询  所有数据
     */
    @Test
    public void queryByPageable(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<TbItem> all = itemDao.findAll(pageable);
        for (TbItem tbItem : all) {
            System.out.println(tbItem.getTitle());
        }
    }


    //通配符查询  索引的时候分词了，但是查询的时候不分词。
    //* 表示匹配所有
    //? 表示匹配一个字符 会占用一个字符空间。
    @Test
    public void queryByWialdQuery(){
        NativeSearchQuery query = new NativeSearchQuery(QueryBuilders.wildcardQuery("title", "商?"));
        AggregatedPage<TbItem> tbItems = elasticsearchTemplate.queryForPage(query, TbItem.class);

        long totalElements = tbItems.getTotalElements();
        System.out.println("总记录数"+totalElements);
        List<TbItem> content = tbItems.getContent();

        for (TbItem tbItem : content) {
            System.out.println(tbItem.getTitle());
        }
    }




    //分词  匹配 查询  通过boolean 查询 默认是OR 进行连接

    // 索引的时候分词了  查询的时候先分词  再进行查询匹配 并通过OR 进行连接 并集显示 所以有数据
    @Test
    public void queryByMatchQuery(){
        NativeSearchQuery query = new NativeSearchQuery(QueryBuilders.matchQuery("title", "商品111"));
        AggregatedPage<TbItem> tbItems = elasticsearchTemplate.queryForPage(query, TbItem.class);

        long totalElements = tbItems.getTotalElements();
        System.out.println("总记录数"+totalElements);
        List<TbItem> content = tbItems.getContent();

        for (TbItem tbItem : content) {
            System.out.println(tbItem.getTitle());
        }

    }


        //组合域查询
    @Test
    public void queryByCopyTo(){
        //select * from xx where keyword="三星"   <====> select * from xx where seller like '三星' OR category like '三星' OR ....

        NativeSearchQuery query = new NativeSearchQuery(QueryBuilders.matchQuery("keyword", "三星"));
        AggregatedPage<TbItem> tbItems = elasticsearchTemplate.queryForPage(query, TbItem.class);
        long totalElements = tbItems.getTotalElements();
        System.out.println("总记录数："+totalElements);
        List<TbItem> content = tbItems.getContent();

        for (TbItem tbItem : content) {
            System.out.println(tbItem.getTitle());
        }
    }


    /**
     * 对象域查询
     */
    @Test
    public void queryByObject(){
            //specMap.网络制式.keyword  ===> fieldName.属性名.keyword
            //specMap :指定的就是要查询的字段名 和POJO中的字段一致

            // 网络制式 :指定的就是 指定 网络制式 字段
            // keyword :固定的写法 表示 搜索的时候不分词。

            NativeSearchQuery query = new NativeSearchQuery(QueryBuilders.matchQuery("specMap.网络制式.keyword", "移动4G"));
            AggregatedPage<TbItem> tbItems = elasticsearchTemplate.queryForPage(query, TbItem.class);
            long totalElements = tbItems.getTotalElements();
            System.out.println("总记录数："+totalElements);

            List<TbItem> content = tbItems.getContent();
            for (TbItem tbItem : content) {
                System.out.println(tbItem.getTitle()+":"+tbItem.getSpecMap());
            }
        }


    /**
     * 过滤查询
     */
    @Test
    public void queryByFilter(){
        //1.创建查询对象的构建对象
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //2.创建 查询条件
        queryBuilder.withIndices("pinyougou");//设置从哪一个索引查询
        queryBuilder.withTypes("item");//设置从哪一个类型中查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("title","商品"));//从title 中查询内容为商品的数据

        //3.创建 过滤查询（规格的过滤查询 多个过滤使用bool查询 ）
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.filter(QueryBuilders.termQuery("specMap.网络制式.keyword","移动4G"));
        boolQueryBuilder.filter(QueryBuilders.termQuery("specMap.机身内存.keyword","16G"));

        queryBuilder.withFilter(boolQueryBuilder);

        //4.构建 查询条件
        NativeSearchQuery searchQuery = queryBuilder.build();

        //5.执行查询
        AggregatedPage<TbItem> tbItems = elasticsearchTemplate.queryForPage(searchQuery, TbItem.class);

        //6.获取结果集
        long totalElements = tbItems.getTotalElements();
        System.out.println("总记录数："+totalElements);

        List<TbItem> content = tbItems.getContent();
        for (TbItem tbItem : content) {
            System.out.println(tbItem.getTitle()+":>>>"+tbItem.getSpecMap());
        }
    }
}
