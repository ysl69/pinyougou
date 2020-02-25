package com.pinyougou;

import com.itheima.es.dao.ItemDao;
import com.itheima.model.TbItem;
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
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author ysl
 * @Date 2019/7/2 11:05
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



    @Test
    public void saveData(){
        TbItem item = new TbItem();
        item.setId(3000L);
        item.setTitle("华为手机");
        item.setCategory("手机");
        item.setBrand("华为");
        item.setSeller("华为旗舰店");
        Map<String, String> map = new HashMap<>();
        map.put("网络制式","移动4G");
        map.put("机身内存","128G");
        item.setSpecMap(map);//规格的数据 规格的名称(key) 和规格的选项值(value)
        item.setGoodsId(1L);
        itemDao.save(item);
    }


    @Test
    public void createAllDocument(){
        for (long i = 0; i < 100; i++) {
            TbItem item = new TbItem();
            item.setId(3000+i);
            item.setTitle("华为手机"+i);
            item.setCategory("手机"+i);
            item.setBrand("华为"+i);
            item.setSeller("华为旗舰店"+i);
            item.setGoodsId(1L);
            itemDao.save(item);
        }
    }

    // 删除文档
    @Test
    public void deleteById(){
        itemDao.deleteById(20000L);
    }


    //修改数据  修改数据和 保存数据一样。当有相同的ID 时直接就更新
    @Test
    public void update(){
            TbItem tbItem = new TbItem();
            tbItem.setId(50L);
            tbItem.setGoodsId(50L);
            tbItem.setTitle("华为");
            tbItem.setCategory("华为手机");
            tbItem.setBrand("华为");
            tbItem.setSeller("华为旗舰店");
            itemDao.save(tbItem);
    }


    // 查询 查询所有
    @Test
    public void QueryAll(){
        Iterable<TbItem> all = itemDao.findAll();
        for (TbItem tbItem : all) {
            System.out.println(tbItem.getTitle());
        }
    }


    // 查询 根据id查询
    @Test
    public void QueryById(){
        Optional<TbItem> id = itemDao.findById(30000L);
        System.out.println(id.get().getTitle());
    }


    // 分页查询  所有数据
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
    //? 表示匹配一个字符 会占用一个字符空间
    @Test
    public void queryByWialdQuery(){
        //1.创建一个查询对象  //2.设置查询的条件
        NativeSearchQuery query = new NativeSearchQuery(QueryBuilders.wildcardQuery("title", "华?"));
        AggregatedPage<TbItem> tbItems = elasticsearchTemplate.queryForPage(query, TbItem.class);

        //3.执行查询
        long totalElements = tbItems.getTotalElements();
        System.out.println("总记录数："+totalElements);

        //4.获取到查询结果 处理结果
        List<TbItem> content = tbItems.getContent();
        for (TbItem tbItem : content) {
            System.out.println(tbItem.getTitle());
        }
    }


    //分词match  匹配 查询  通过boolean 查询 默认是OR 进行连接
    // 索引的时候分词了  查询的时候先分词  再进行查询匹配 并通过OR 进行连接 并集显示 所以有数据
    @Test
    public void queryByMatchQuery(){
        //1.创建一个查询对象 设置查询的条件
        NativeSearchQuery query = new NativeSearchQuery(QueryBuilders.matchQuery("title", "华为手机"));
        AggregatedPage<TbItem> tbItems = elasticsearchTemplate.queryForPage(query, TbItem.class);

        //2.执行查询
        long totalElements = tbItems.getTotalElements();
        System.out.println("总记录数："+totalElements);
        System.out.println("获取到页数："+tbItems.getTotalPages());

        //3.获取结果
        List<TbItem> content = tbItems.getContent();
        for (TbItem tbItem : content) {
            System.out.println(tbItem.getTitle());
        }
    }


    //组合域查询
    @Test
    public void queryByCoryTo(){
        //select * from xx where keyword="三星"   <====>
        // select * from xx where seller like '三星' OR category like '三星' OR ....

        //1.创建查询对象
        NativeSearchQuery query = new NativeSearchQuery(QueryBuilders.matchQuery("keyword", "华为"));
        //SearchQuery query = new NativeSearchQuery(QueryBuilders.matchQuery("分辨率","手机"));

        //2.设置查询的条件
        //3.执行查询
        AggregatedPage<TbItem> tbItems = elasticsearchTemplate.queryForPage(query, TbItem.class);

        //4.获取结果
        long totalElements = tbItems.getTotalElements();
        System.out.println("总记录数："+totalElements);
        System.out.println("获取到页数："+tbItems.getTotalPages());
        List<TbItem> content = tbItems.getContent();

        for (TbItem tbItem : content) {
            System.out.println(tbItem.getTitle());
        }
    }



    //查询  网络制式 为 移动4G 的手机
    @Test
    public void queryByObject(){
        //specMap.网络制式.keyword  ===> fieldName.属性名.keyword
        //specMap :指定的就是要查询的字段名 和POJO中的字段一致

        // 网络制式 :指定的就是 指定 网络制式 字段
        // keyword :固定的写法 表示 搜索的时候不分词。

        NativeSearchQuery query = new NativeSearchQuery(QueryBuilders.matchQuery("specMap.网络制式.keyword", "移动4G"));

        AggregatedPage<TbItem> tbItems = elasticsearchTemplate.queryForPage(query, TbItem.class);

        System.out.println("总记录数："+tbItems.getTotalElements());
        System.out.println("获取到页数："+tbItems.getTotalPages());

        List<TbItem> content = tbItems.getContent();
        for (TbItem tbItem : content) {
            System.out.println(tbItem.getTitle()+":"+tbItem.getSpecMap());
        }
    }



    // 多个条件组合查询
    //  查询 title 为华为 的 数据
    //  查询 网络制式 为 移动4G的数据
    @Test
    public void queryByFilter(){
        //1.创建查询对象的构建对象
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        // 2.创建查询条件
        queryBuilder.withIndices("pinyougou");  //设置从哪一个索引查询
        queryBuilder.withTypes("item"); //设置从哪一个类型中查询

        queryBuilder.withQuery(QueryBuilders.matchQuery("title","华为"));  //从title 中查询内容为商品的数据

        //3.创建 过滤查询（规格的过滤查询 多个过滤使用bool查询 ）
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.filter(QueryBuilders.termQuery("specMap.网络制式.keyword","移动4G"));
        boolQueryBuilder.filter(QueryBuilders.termQuery("specMap.机身内存.keyword","128G"));

        queryBuilder.withFilter(boolQueryBuilder);

        //4.构建 查询条件
        NativeSearchQuery searchQuery = queryBuilder.build();

        //5.执行查询
        AggregatedPage<TbItem> tbItems = elasticsearchTemplate.queryForPage(searchQuery, TbItem.class);

        //6.获取结果集
        int totalPages = tbItems.getTotalPages();
        System.out.println("总页数"+totalPages);
        long totalElements = tbItems.getTotalElements();//总记录数
        System.out.println("总记录数"+totalElements);

        List<TbItem> content = tbItems.getContent();
        for (TbItem tbItem : content) {
            System.out.println(tbItem.getTitle()+":>>>"+tbItem.getSpecMap());
        }

    }
}
