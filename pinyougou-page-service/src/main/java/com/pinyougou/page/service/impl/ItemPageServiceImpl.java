package com.pinyougou.page.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author ysl
 * @Date 2019/7/28 21:21
 * @Description:
 **/


@Service
public class ItemPageServiceImpl implements ItemPageService {
    @Autowired
    private TbGoodsMapper tbGoodsMapper;

    @Value("${pageDir}")
    private String pageDir;

    @Autowired
    private TbGoodsDescMapper goodsDescMapper;

    @Autowired
    private FreeMarkerConfigurer configurer;

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Autowired
    private TbItemMapper tbItemMapper;


    @Override
    public void genItemHtml(Long goodsId) {
        //查询数据库的商品的数据   生成静态页面

        //1.根据SPU的ID 查询商品的信息（goods  goodsDesc  ）
        TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(goodsId);
        TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);

        //2.使用freemarker 创建模板  使用数据集 生成静态页面 (数据集 和模板)
        genHTML("item.ftl", tbGoods, tbGoodsDesc);
    }


    private void genHTML(String templateName, TbGoods tbGoods, TbGoodsDesc tbGoodsDesc) {
        //FileWriter writer = null;
        BufferedWriter writer = null;
        try {
            //1.创建一个configuration对象
            //2.设置字符编码 和 模板加载的目录
            Configuration configuration = configurer.getConfiguration();
            //3.获取模板对象
            Template template = configuration.getTemplate(templateName);
            //4.获取数据集
            Map model = new HashMap();
            model.put("tbGoods", tbGoods);
            model.put("tbGoodsDesc", tbGoodsDesc);

            //5.创建一个写流
            writer =
                    new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(pageDir + tbGoods.getId() + ".html")), "UTF-8"));
            //6.调用模板对象的process 方法输出到指定的文件中
            template.process(model, writer);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //7.关闭流
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
