package com.pinyougou.page.listener;

import com.alibaba.fastjson.JSON;
import com.pinyougou.common.pojo.MessageInfo;
import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.TbItem;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;

/**
 * @Author ysl
 * @Date 2019/7/8 22:43
 * @Description:
 **/


public class PageMessageListener implements MessageListenerConcurrently {

    @Autowired
    private ItemPageService itemPageService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        System.out.println(">>>>当前的线程>>>>" + Thread.currentThread().getName());
        try {
            if (msgs != null)
                //1.循环遍历
                for (MessageExt msg : msgs) {
                    //2.获取消息体 bytes
                    byte[] body = msg.getBody();
                    //3.转成字符串  info
                    String info = new String(body);
                    //4.转成对象
                    MessageInfo messageInfo = JSON.parseObject(info, MessageInfo.class);
                    //5.判断 类型 //add  update  delete  分别进行生成静态页 和删除静态页
                    switch (messageInfo.getMethod()) {
                        case MessageInfo.METHOD_ADD: //新增
                        {
                            updatePageHtml(messageInfo);
                            break;
                        }
                        case MessageInfo.METHOD_UPDATE: //更新
                        {
                            updatePageHtml(messageInfo);
                            break;
                        }
                        case MessageInfo.METHOD_DELETE: //删除
                        {
                            String s = messageInfo.getContext().toString();
                            //获取Long数组
                            Long[] longs = JSON.parseObject(s, Long[].class);
                            itemPageService.deleteById(longs);
                            break;
                        }
                        default:
                            break;
                    }
                }
            //直接返回消费成功,如果消费失败 就是消费延迟 会重新发送消息。
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            //直接返回消费成功,如果消费失败 就是消费延迟 会重新发送消息。
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }

    }

    private void updatePageHtml(MessageInfo info) {
        String context1 = info.getContext().toString();//获取到的是Map对象 并不能直接序列化回来 需要直接转成字符串
        List<TbItem> tbItems = JSON.parseArray(context1, TbItem.class);
        HashSet<Long> set = new HashSet<>();
        for (TbItem tbItem : tbItems) {
            //循环遍历进行生成静态页面。
            Long goodsId = tbItem.getGoodsId();
            set.add(goodsId);
        }
        //循环遍历 生成静态页面
        for (Long aLong : set) {
            itemPageService.genItemHtml(aLong);
        }
        return;
    }
}
