package com.pinyougou.seckill.listener;

import com.alibaba.fastjson.JSON;
import com.pinyougou.common.pojo.MessageInfo;
import com.pinyougou.mapper.TbSeckillGoodsMapper;
import com.pinyougou.pojo.TbSeckillGoods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ysl
 * @Date 2019/7/18 22:41
 * @Description:
 **/


public class PageMessageListener implements MessageListenerConcurrently {
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                    ConsumeConcurrentlyContext consumeConcurrentlyContext) {

        try {
            if (msgs != null) {
                for (MessageExt msg : msgs) {
                    //1.获取消息体
                    byte[] body = msg.getBody();
                    //2.转成String
                    String s = new String(body);
                    //3.转成JSON对象 Messageinfo
                    MessageInfo messageInfo = JSON.parseObject(s, MessageInfo.class);
                    //4.判断是否是ADD ---生成静态页面
                    switch (messageInfo.getMethod()) {
                        case 1: //add
                        {
                            // 获取对象
                            String context1 = messageInfo.getContext().toString();
                            //转成long[] 数组
                            Long[] longs = JSON.parseObject(context1, Long[].class);
                            //查询数据库的数据
                            for (Long aLong : longs) {
                                //使用freemarker 生成静态页
                                genHTML("item.ftl", aLong);
                            }
                            break;
                        }
                        case 2://update
                        {
                            break;
                        }
                        case 3:// delete
                        {

                            break;
                        }
                        default: {
                            break;
                        }
                    }
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }


    @Autowired
    private FreeMarkerConfigurer configurer;

    @Autowired
    private TbSeckillGoodsMapper seckillGoodsMapper;


    @Value("${PageDir}")
    private String pageDir;

    //生成静态页面  数据集 + 模板  =html
    private void genHTML(String templateName, Long id) {
       // FileWriter writer = null;
        BufferedWriter writer = null;

        //1.创建一个configuration对象
        //2.设置字符编码 和 模板加载的目录
        try {
            Configuration configuration = configurer.getConfiguration();
            //3.获取模板对象
            Template template = configuration.getTemplate(templateName);
            //4.获取数据集
            Map model = new HashMap();
            TbSeckillGoods seckillGoods = seckillGoodsMapper.selectByPrimaryKey(id);
            model.put("seckillGoods",seckillGoods);
            //5.创建一个写流
            //writer = new FileWriter(new File(pageDir+seckillGoods.getId()+".shtml"));
            writer =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(pageDir + seckillGoods.getId() + ".html")), "UTF-8"));
            //6.调用模板对象的process 方法输出到指定的文件中
            template.process(model,writer);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //7.关闭流
            if (writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
