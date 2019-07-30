package com.pinyougou.listener;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @Author ysl
 * @Date 2019/7/30 18:39
 * @Description:
 **/


public class MyMessageListener implements MessageListenerConcurrently {
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        System.out.println("Receive New Messages:"+Thread.currentThread().getName());
        if(msgs!=null){

            for (MessageExt msg : msgs) {
                System.out.println("mssageid===>"+msg.getMsgId()+"====>"+new String(msg.getBody())+"=====>>"+msg.getTags());
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }

        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
    }
}
