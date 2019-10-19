package com.itheima.sms.listener;

import com.alibaba.fastjson.JSON;
import com.itheima.sms.util.SmsUtil;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.Map;

/**
 * @Author ysl
 * @Date 2019/9/6 13:52
 * @Description: 监听器 用于监听消息 调用阿里大鱼的API发送短信
 **/


public class SMSMessageListener implements MessageListenerConcurrently {
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        try {
            if (msgs != null){
                for (MessageExt msg : msgs) {
                    byte[] body = msg.getBody();
                    String ss = new String(body);
                    //获取到相关信息
                    Map<String,String> map = JSON.parseObject(ss, Map.class);//有签名和其他的信息
                    SmsUtil.sendSms(map);
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }
}
