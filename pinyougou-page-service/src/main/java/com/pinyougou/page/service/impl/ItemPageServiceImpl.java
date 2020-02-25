package com.pinyougou.page.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemCat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import tk.mybatis.mapper.entity.Example;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ysl
 * @Date 2019/7/6 22:05
 * @Description:
 **/

@Service
public class ItemPageServiceImpl implements ItemPageService {


    @Autowired
    private TbGoodsMapper tbGoodsMapper;

    @Autowired
    private TbGoodsDescMapper goodsDescMapper;

    @Autowired
    private FreeMarkerConfigurer configurer;

    @Value("${pageDir}")
    private String pageDir;

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Autowired
    private TbItemMapper itemMapper;


    @Override
    public void genItemHtml(Long goodsId) {
        // 查询数据库的商品数据 生成静态页面

        //1.根据SPU的ID 查询商品的信息（goods  goodsDesc  ）
        TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(goodsId);
        TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);

        //2.使用freemarker 创建模板  使用数据集 生成静态页面 (数据集 和模板)   模板 + 数据集=html）
        genHtml("item.ftl",tbGoods,tbGoodsDesc);
    }

    @Override
    public void deleteById(Long[] goodsId) {
        try {
            for (Long aLong : goodsId) {
                FileUtils.forceDelete(new File(pageDir + aLong + ".html"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void genHtml(String templateName, TbGoods tbGoods, TbGoodsDesc tbGoodsDesc) {
        //FileWriter writer = null;
        BufferedWriter writer = null;
        try {
            //1.创建一个configuration对象
            //2.设置字符编码 和 模板加载的目录
            Configuration configuration = configurer.getConfiguration();
            //3.获取模板对象
            Template template = configuration.getTemplate(templateName);



            //4.获取数据
            Map<String, Object> model = new HashMap<>();
            model.put("tbGoods",tbGoods);
            model.put("tbGoodsDesc",tbGoodsDesc);


            //根据分类的ID 获取分类的对象里面的名称 设置到数据集中 返回给页面显示
            TbItemCat tbItemCat1 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory1Id());
            TbItemCat tbItemCat2 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory2Id());
            TbItemCat tbItemCat3 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id());
            model.put("tbItemCat1",tbItemCat1.getName());
            model.put("tbItemCat2",tbItemCat2.getName());
            model.put("tbItemCat3",tbItemCat3.getName());


            //获取该SPU的所有的SKU的列表数据 存储到model中
            //select * from tb_item where goods_id = 1 and status=1 order by is_default desc
            Example exmaple = new Example(TbItem.class);
            Example.Criteria criteria = exmaple.createCriteria();
            criteria.andEqualTo("goodsId",tbGoods.getId());
            criteria.andEqualTo("status","1");
            exmaple.setOrderByClause("is_default desc");//order by  is_default desc

            List<TbItem> tbItems = itemMapper.selectByExample(exmaple);

            model.put("skuList",tbItems);

            //5.创建输出流 指定输出的目录文件
            //writer = new FileWriter(new File(pageDir+tbGoods.getId()+".html"));
           writer =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(pageDir + tbGoods.getId() + ".html")), "UTF-8"));

            //6.调用模板对象的process 方法输出到指定的文件中  (执行输出的动作生成静态页面)
            template.process(model,writer);

            //7.关闭流
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
