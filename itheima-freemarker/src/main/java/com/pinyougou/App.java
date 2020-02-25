package com.pinyougou;


import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * @Author ysl
 * @Date 2019/7/6 11:05
 * @Description:
 **/


public class App {
    public static void main(String[] args) throws Exception{
        // 1.创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());

        //2.设置模板所在的目录
        configuration.setDirectoryForTemplateLoading(new File("G:\\practice\\pinuyougou1\\itheima-freemarker\\src" +
                "\\main\\resources\\template"));

        //3.设置字符集
        configuration.setDefaultEncoding("utf-8");

        //4.加载模板
        Template template = configuration.getTemplate("template.ftl");

        //5.创建数据模型
        Map<String,Object> model = new HashMap<>();

        model.put("name","张三");
        model.put("message", "欢迎来到神奇的品优购世界！");
        model.put("success",true);


        List goodsList=new ArrayList();
        Map goods1=new HashMap();
        goods1.put("name", "苹果");
        goods1.put("price", 5.8);
        Map goods2=new HashMap();
        goods2.put("name", "香蕉");
        goods2.put("price", 2.5);
        Map goods3=new HashMap();
        goods3.put("name", "橘子");
        goods3.put("price", 3.2);
        goodsList.add(goods1);
        goodsList.add(goods2);
        goodsList.add(goods3);
        model.put("goodsList", goodsList);



        model.put("today",new Date());


        model.put("point",102920122);

        //6.创建Writer对象
        FileWriter writer = new FileWriter(new File("G:\\practice\\pinuyougou1\\itheima-freemarker\\src\\main" +
                "\\resources\\html\\test.html"));

        //7.输出
        template.process(model,writer);

        //8.关闭Writer对象
        writer.close();
    }
}
