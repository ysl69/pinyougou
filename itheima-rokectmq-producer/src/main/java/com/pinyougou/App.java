package com.pinyougou;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;


/**
 * @Author ysl
 * @Date 2019/7/7 16:33
 * @Description:
 **/


public class App {
    /**
     * 模拟发送消息
     *
     * @param args
     */
    public static void main(String[] args) throws Exception{
        //1.创建生产者 对象 并指定组名
        DefaultMQProducer producer = new DefaultMQProducer("producer_cluster_group1");

        //2.设置 nameserver的地址
        producer.setNamesrvAddr("127.0.0.1:9876");

        //3.开启连接 并使用
        producer.start();

        //4.发送消息
            //创建消息对象，并指定主题 标签 和消息体
        String messageinfo ="hello world";
            /**
             * 参数1:消息的(大的业务)主题
             * 参数2:消息的标签(小分类)
             * 参数3:key 业务的唯一标识
             * 参数4:消息体(消息内容)
             */
        Message msg = new Message("TopicTest","TagA","唯一的KEY",messageinfo.getBytes(RemotingHelper.DEFAULT_CHARSET));

        //发送消息到其中的一个broker中
        SendResult sendResult = producer.send(msg);
        System.out.printf("%s%n", sendResult);

        //5.关闭连接
        producer.shutdown();
    }
}