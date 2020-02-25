package com.pinyougou.seckill.thread;

import com.alibaba.fastjson.JSON;
import com.pinyougou.common.pojo.MessageInfo;
import com.pinyougou.common.util.IdWorker;
import com.pinyougou.common.util.SysConstants;
import com.pinyougou.mapper.TbSeckillGoodsMapper;
import com.pinyougou.pojo.TbSeckillGoods;
import com.pinyougou.pojo.TbSeckillOrder;
import com.pinyougou.seckill.pojo.SeckillStatus;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;

/**
 * @Author ysl
 * @Date 2019/7/20 11:39
 * @Description:
 **/


public class CreateOrderThread {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbSeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private IdWorker idWorker;

    //多线程执行下单操作  异步方法
    @Async//启用异步调用
    public void handleOrder(){
        try {
            System.out.println("模拟处理订单开始============"+Thread.currentThread().getName());
            Thread.sleep(10000);
            System.out.println("模拟处理订单结束 总共耗费10秒钟============="+Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //1.从队列中获取元素
        SeckillStatus seckillStatus = (SeckillStatus) redisTemplate.boundListOps(SysConstants.SEC_KILL_USER_ORDER_LIST).rightPop();
        if (seckillStatus != null){
            //2.获取用户ID 和 秒杀商品的ID
            TbSeckillGoods killgoods = (TbSeckillGoods) redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).get(seckillStatus.getGoodsId());
            //3. 减库存
            killgoods.setStockCount(killgoods.getStockCount()-1);
            redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).put(seckillStatus.getGoodsId(),killgoods);

            //4.如果库存为0 更新到数据库中
            if (killgoods.getStockCount() <= 0){//如果已经被秒光
                //同步到数据库
                seckillGoodsMapper.updateByPrimaryKey(killgoods);
                //将redis中的该商品清除掉
                redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).delete(seckillStatus.getGoodsId());
            }
            //5.创建一个预订单到redis中
            long orderId = idWorker.nextId();
            TbSeckillOrder seckillOrder = new TbSeckillOrder();
            seckillOrder.setId(orderId);//设置订单的ID 这个就是out_trade_no
            seckillOrder.setCreateTime(new Date());// 创建时间
            seckillOrder.setMoney(killgoods.getCostPrice());//秒杀价格  价格
            seckillOrder.setSellerId(killgoods.getSellerId());
            seckillOrder.setUserId(seckillStatus.getUserId());//设置用户ID;
            seckillOrder.setStatus("0");//状态 未支付
            seckillOrder.setSeckillId(seckillStatus.getGoodsId());

            //将构建的订单保存到redis中
            redisTemplate.boundHashOps(SysConstants.SEC_KILL_ORDER).put(seckillStatus.getUserId(),seckillOrder);

            //移除排队标识  标识下单成功
            redisTemplate.boundHashOps(SysConstants.SEC_KILL_USER_ORDER_LIST).delete(seckillStatus.getUserId());

            //立即发送延时消息
            //sendMessage(seckillOrder);
        }
    }


    @Autowired
    private DefaultMQProducer defaultMQProducer;

    private void sendMessage(TbSeckillOrder seckillOrder) {
        try {
            MessageInfo messageInfo = new MessageInfo(seckillOrder, "TOPIC_SECKILL_DELAY", "TAG_SECKILL_DELAY",
                    "handleOrder_DELAY", MessageInfo.METHOD_UPDATE);
            //
            System.out.println("多线程下单============");
            Message message = new Message(messageInfo.getTopic(),messageInfo.getTags(),messageInfo.getKeys(), JSON.toJSONString(messageInfo).getBytes());
            //1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            //设置消息演示等级 16=30m
            message.setDelayTimeLevel(5);
            defaultMQProducer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
