package com.pinyougou.seckill.service.impl;
import java.util.*;

import com.github.wxpay.sdk.WXPayUtil;
import com.pinyougou.common.util.HttpClient;
import com.pinyougou.common.util.IdWorker;
import com.pinyougou.common.util.SysConstants;
import com.pinyougou.mapper.TbSeckillGoodsMapper;
import com.pinyougou.pojo.TbSeckillGoods;
import com.pinyougou.seckill.pojo.SeckillStatus;
import com.pinyougou.seckill.service.SeckillOrderService;
import com.pinyougou.seckill.thread.CreateOrderThread;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo; 									  
import org.apache.commons.lang3.StringUtils;
import com.pinyougou.core.service.CoreServiceImpl;

import org.springframework.data.redis.core.RedisTemplate;
import tk.mybatis.mapper.entity.Example;

import com.pinyougou.mapper.TbSeckillOrderMapper;
import com.pinyougou.pojo.TbSeckillOrder;




/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SeckillOrderServiceImpl extends CoreServiceImpl<TbSeckillOrder>  implements SeckillOrderService {


	private TbSeckillOrderMapper seckillOrderMapper;

	@Autowired
	public SeckillOrderServiceImpl(TbSeckillOrderMapper seckillOrderMapper) {
		super(seckillOrderMapper, TbSeckillOrder.class);
		this.seckillOrderMapper=seckillOrderMapper;
	}





	@Override
    public PageInfo<TbSeckillOrder> findPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<TbSeckillOrder> all = seckillOrderMapper.selectAll();
        PageInfo<TbSeckillOrder> info = new PageInfo<TbSeckillOrder>(all);

        //序列化再反序列化
        String s = JSON.toJSONString(info);
        PageInfo<TbSeckillOrder> pageInfo = JSON.parseObject(s, PageInfo.class);
        return pageInfo;
    }




	 @Override
    public PageInfo<TbSeckillOrder> findPage(Integer pageNo, Integer pageSize, TbSeckillOrder seckillOrder) {
        PageHelper.startPage(pageNo,pageSize);

        Example example = new Example(TbSeckillOrder.class);
        Example.Criteria criteria = example.createCriteria();

        if(seckillOrder!=null){
						if(StringUtils.isNotBlank(seckillOrder.getUserId())){
				criteria.andLike("userId","%"+seckillOrder.getUserId()+"%");
				//criteria.andUserIdLike("%"+seckillOrder.getUserId()+"%");
			}
			if(StringUtils.isNotBlank(seckillOrder.getSellerId())){
				criteria.andLike("sellerId","%"+seckillOrder.getSellerId()+"%");
				//criteria.andSellerIdLike("%"+seckillOrder.getSellerId()+"%");
			}
			if(StringUtils.isNotBlank(seckillOrder.getStatus())){
				criteria.andLike("status","%"+seckillOrder.getStatus()+"%");
				//criteria.andStatusLike("%"+seckillOrder.getStatus()+"%");
			}
			if(StringUtils.isNotBlank(seckillOrder.getReceiverAddress())){
				criteria.andLike("receiverAddress","%"+seckillOrder.getReceiverAddress()+"%");
				//criteria.andReceiverAddressLike("%"+seckillOrder.getReceiverAddress()+"%");
			}
			if(StringUtils.isNotBlank(seckillOrder.getReceiverMobile())){
				criteria.andLike("receiverMobile","%"+seckillOrder.getReceiverMobile()+"%");
				//criteria.andReceiverMobileLike("%"+seckillOrder.getReceiverMobile()+"%");
			}
			if(StringUtils.isNotBlank(seckillOrder.getReceiver())){
				criteria.andLike("receiver","%"+seckillOrder.getReceiver()+"%");
				//criteria.andReceiverLike("%"+seckillOrder.getReceiver()+"%");
			}
			if(StringUtils.isNotBlank(seckillOrder.getTransactionId())){
				criteria.andLike("transactionId","%"+seckillOrder.getTransactionId()+"%");
				//criteria.andTransactionIdLike("%"+seckillOrder.getTransactionId()+"%");
			}

		}
        List<TbSeckillOrder> all = seckillOrderMapper.selectByExample(example);
        PageInfo<TbSeckillOrder> info = new PageInfo<TbSeckillOrder>(all);
        //序列化再反序列化
        String s = JSON.toJSONString(info);
        PageInfo<TbSeckillOrder> pageInfo = JSON.parseObject(s, PageInfo.class);

        return pageInfo;
    }


    @Autowired
    private RedisTemplate redisTemplate;

	@Autowired
	private TbSeckillGoodsMapper seckillGoodsMapper;

	@Autowired
	private IdWorker idWorker;

	@Autowired
	private CreateOrderThread createOrderThread;

	/**
	 * 秒杀下单
	 * @param seckillId  秒杀商品的ID
	 * @param userId	下单的用户ID
	 */
	@Override
	public void submitOrder(Long seckillId, String userId) {



		//1.获取redis中的商品
		TbSeckillGoods killgoods = (TbSeckillGoods) redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).get(seckillId);

		//2.判断商品是否存在 ，如果不存在 提示商品售罄
		//3.判断商品的库存是否 大于0 如果不是 提示商品已经售罄
		/*if (killgoods==null ||  killgoods.getStockCount()<=0){
			//说明商品已经没有库存了
			throw new RuntimeException("商品已被抢光");
		}*/


		//判断是否已经在排队中了
		if (redisTemplate.boundHashOps(SysConstants.SEC_USER_QUEUE_FLAG_KEY).get(userId) != null){
			throw new RuntimeException("你已在排队中了别抢了，请稍等");
		}

		//判断是否有未支付的订单
		if (redisTemplate.boundHashOps(SysConstants.SEC_KILL_ORDER).get(userId) != null){
			throw new RuntimeException("你有未支付的订单，请先支付");
		}

		//一个商品就是一个队列，队列的长度就是商品的库存长度
		Long goodsId = (Long) redisTemplate.boundListOps(SysConstants.SEC_KILL_GOODS_PREFIX + seckillId).rightPop();
		if (goodsId == null){
			//说明商品已经没有库存了
			throw new RuntimeException("商品已被抢光");
		}


		//用户进入排队
		redisTemplate.boundListOps(SysConstants.SEC_KILL_USER_ORDER_LIST).leftPush(new SeckillStatus(userId,seckillId
				,SeckillStatus.SECKILL_queuing));

		//设置排队标识
		redisTemplate.boundHashOps(SysConstants.SEC_USER_QUEUE_FLAG_KEY).put(userId,seckillId);

		//多线程处理下单
		createOrderThread.handleOrder();

		//4.扣减商品库存
		killgoods.setStockCount(killgoods.getStockCount()-1);//减少
		redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).put(seckillId,killgoods);

		//5.判断当库存为0 时 更新到数据库中
		if (killgoods.getStockCount() == 0){
			seckillGoodsMapper.updateByPrimaryKeySelective(killgoods);
			redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).delete(seckillId);//将redis中的该商品清除掉
		}

		//6.创建预订单对象，存放在REDIS中
		long orderId = idWorker.nextId();

		TbSeckillOrder seckillOrder = new TbSeckillOrder();

		seckillOrder.setId(orderId);//设置订单的ID 这个就是out_trade_no
		seckillOrder.setCreateTime(new Date());//创建时间
		seckillOrder.setMoney(killgoods.getCostPrice());//秒杀价格  价格
		seckillOrder.setSeckillId(seckillId);//秒杀商品的ID
		seckillOrder.setSellerId(killgoods.getSellerId());
		seckillOrder.setUserId(userId);//设置用户ID
		seckillOrder.setStatus("0");//状态 未支付

		//将构建的订单保存到redis中
		redisTemplate.boundHashOps(SysConstants.SEC_KILL_ORDER).put(userId,seckillOrder);
	}


	/**
	 * 查询下单状态
	 * @param userId
	 * @return
	 */
	@Override
	public TbSeckillOrder getUserOrderStatus(String userId) {
		// 返回订单对象
		return (TbSeckillOrder) redisTemplate.boundHashOps(SysConstants.SEC_KILL_ORDER).get(userId);
	}


	/**
	 * 更新订单的状态 支付成功的时候执行
	 * @param transaction_id 交易订单号
	 * @param userId 支付的用户的ID
	 */
	@Override
	public void updateOrderStatus(String transaction_id, String userId) {
		TbSeckillOrder seckillOrder = (TbSeckillOrder) redisTemplate.boundHashOps(SysConstants.SEC_KILL_ORDER).get(userId);

		if (seckillOrder != null){
			seckillOrder.setPayTime(new Date());
			seckillOrder.setStatus("1");
			seckillOrder.setTransactionId(transaction_id);
			//seckillOrder.setSeckillId(seckillOrder.getSeckillId());

			//存储到数据库中
			seckillOrderMapper.insert(seckillOrder);

			//删除预订单即可
			redisTemplate.boundHashOps(SysConstants.SEC_KILL_ORDER).delete(userId);
		}
	}


	/**
	 * 支付超时的时候执行。用于删除订单
	 * @param userId
	 */
	@Override
	public void deleteOrder(String userId) {
		//获取秒杀订单
		TbSeckillOrder seckillOrder = (TbSeckillOrder) redisTemplate.boundHashOps(SysConstants.SEC_KILL_ORDER).get(userId);

		if (seckillOrder == null){
			System.out.println("没有带订单");
			return;
		}

		Long seckillId = seckillOrder.getSeckillId();

		//获取商品对象
		TbSeckillGoods seckillGoods = (TbSeckillGoods) redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).get(seckillId);

		//说明 redis中已经没有了
		if (seckillGoods == null){
			//从数据库查询
			seckillGoods = seckillGoodsMapper.selectByPrimaryKey(seckillId);
			seckillGoods.setStockCount(seckillGoods.getStockCount()+1);

			//重新存储到REDIS中
			redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).put(seckillId,seckillGoods);

		}else {
			//1.恢复库存
			seckillGoods.setStockCount(seckillGoods.getStockCount()+1);
			redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).put(seckillId,seckillGoods);
		}

		//商品队列中恢复元素
		redisTemplate.boundListOps(SysConstants.SEC_KILL_GOODS_PREFIX+seckillId).leftPush(seckillId);

		//删除该预订单
		redisTemplate.boundHashOps(SysConstants.SEC_KILL_ORDER).delete(userId);
	}

}
