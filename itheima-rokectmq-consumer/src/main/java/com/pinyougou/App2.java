package com.pinyougou;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * Hello world!
 */
public class App2 {
    //接收下消息
    public static void main(String[] args) throws Exception{
        //1.创建消费者  并创建消费者组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_group1");

        //2.设置nameserver地址
        consumer.setNamesrvAddr("127.0.0.1:9876");

        //3.设置消费模式 (1.集群模式(默认)2.广播模式)
        //consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.setMessageModel(MessageModel.BROADCASTING); //消费者广播模式

        //4.设置(订阅)消费主题,并执行消费的 标签  * 表示指定所有标签
        //参数1 指定主题
        //参数2 指定主题里面的TAG 指定具体的TAG 或者使用表达式  * 表示所有的TAG
        consumer.subscribe("TopicTest","*");

        //5.设置监听器  同时消费 不需要按住顺序来消费。 (目的监听主题 获取里面的消息)
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                try {
                    if (msgs != null) {
                        for (MessageExt msg : msgs) {
                            System.out.println("topic:" + msg.getTopic());
                            System.out.println("tag:" + msg.getTags());
                            System.out.println("keys:" + msg.getKeys());

                            byte[] body = msg.getBody();
                            String messageinfo = new String(body);
                            System.out.println("哈哈11111111:" + messageinfo);
                        }
                    }
                    // 消费成功
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                } catch (Exception e) {
                    e.printStackTrace();
                    //重新消费
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
        });
        // 6.开始连接 发布开始消费
        consumer.start();
        //收回连接，关闭资源 关闭socket通道(不管)
        //consumer.shutdown();
    }
}
