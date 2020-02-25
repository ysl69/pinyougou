package com.pinyougou.search.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pinyougou.common.pojo.MessageInfo;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author ysl
 * @Date 2019/7/8 22:09
 * @Description:
 **/


public class GoodsMessageListener implements MessageListenerConcurrently {

    @Autowired
    private ItemSearchService dao;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        System.out.println(">>>>>>>>>>>>>>>>>>接收数据");

        try {
            if (msgs != null){
                for (MessageExt msg : msgs) {
                    byte[] body = msg.getBody();
                    String s = new String(body);
                    MessageInfo messageInfo = JSONObject.parseObject(s, MessageInfo.class);

                    switch (messageInfo.getMethod()){
                        case 1:////新增
                        {
                            String context1 = messageInfo.getContext().toString();// 获取到字符串
                            List<TbItem> tbItems = JSON.parseArray(context1, TbItem.class);

                            dao.updateIndex(tbItems);
                            break;
                        }
                        case 2://更新
                        {
                            String context1 =  messageInfo.getContext().toString();//获取到的是字符串
                            List<TbItem> tbItems = JSON.parseArray(context1, TbItem.class);
                            dao.updateIndex(tbItems);
                            break;
                        }
                        case 3://删除
                            String s1 = messageInfo.getContext().toString();
                            Long[] longs = JSON.parseObject(s1, Long[].class);
                            dao.deleteByIds(longs);
                            break;
                        default:
                            break;
                    }
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }
}
