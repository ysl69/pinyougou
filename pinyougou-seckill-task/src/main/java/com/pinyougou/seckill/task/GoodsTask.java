package com.pinyougou.seckill.task;

import com.pinyougou.common.util.SysConstants;
import com.pinyougou.mapper.TbSeckillGoodsMapper;
import com.pinyougou.pojo.TbSeckillGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Author ysl
 * @Date 2019/7/18 10:45
 * @Description: 任务类的作用就是每隔30秒
 *              重新从数据库中查询所有的秒杀商品 将其存入到redis中，请注意条件
 **/



@Component
public class GoodsTask {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbSeckillGoodsMapper seckillGoodsMapper;

    //每隔30秒执行一次
    @Scheduled(cron = "0/5 * * * * ?")
    public void pushGoods(){


        Example example = new Example(TbSeckillGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status","1");//审核通过的
        criteria.andGreaterThan("stockCount",0);//大于0
        Date date = new Date();
        criteria.andLessThan("startTime",date);
        criteria.andGreaterThan("endTime",date);

        //排除 已经在redis中的商品
        Set<Long> keys = redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).keys();
        if (keys!=null && keys.size()>0){
            criteria.andNotIn("id",keys);
        }
        List<TbSeckillGoods> tbSeckillGoodsList = seckillGoodsMapper.selectByExample(example);

        //全部储存到redis中
        for (TbSeckillGoods tbSeckillGoods : tbSeckillGoodsList) {
            redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).put(tbSeckillGoods.getId(),tbSeckillGoods);
            pushGoodsList(tbSeckillGoods);
        }


    }


    /**
     * 一个队列 就是一种商品
     *  队列的长度 就是 商品的库存
     * @param tbSeckillGoods
     */
    private void pushGoodsList(TbSeckillGoods tbSeckillGoods){
        //向同一个队列中压入商品数据
        for (Integer i = 0; i < tbSeckillGoods.getStockCount(); i++) {
            //库存为多少就是多少个SIZE 值就是id即可
            redisTemplate.boundListOps(SysConstants.SEC_KILL_GOODS_PREFIX+tbSeckillGoods.getId()).leftPush(tbSeckillGoods.getId());
        }
    }
}
