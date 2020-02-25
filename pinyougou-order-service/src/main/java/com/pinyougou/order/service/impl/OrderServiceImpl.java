package com.pinyougou.order.service.impl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pinyougou.common.util.IdWorker;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.mapper.TbOrderItemMapper;
import com.pinyougou.mapper.TbPayLogMapper;
import com.pinyougou.order.service.OrderService;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbOrderItem;
import com.pinyougou.pojo.TbPayLog;
import entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo; 									  
import org.apache.commons.lang3.StringUtils;
import com.pinyougou.core.service.CoreServiceImpl;

import org.springframework.data.redis.core.RedisTemplate;
import tk.mybatis.mapper.entity.Example;

import com.pinyougou.mapper.TbOrderMapper;
import com.pinyougou.pojo.TbOrder;  




/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class OrderServiceImpl extends CoreServiceImpl<TbOrder>  implements OrderService {

	
	private TbOrderMapper orderMapper;

	@Autowired
	public OrderServiceImpl(TbOrderMapper orderMapper) {
		super(orderMapper, TbOrder.class);
		this.orderMapper=orderMapper;
	}

	
	

	
	@Override
    public PageInfo<TbOrder> findPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<TbOrder> all = orderMapper.selectAll();
        PageInfo<TbOrder> info = new PageInfo<TbOrder>(all);

        //序列化再反序列化
        String s = JSON.toJSONString(info);
        PageInfo<TbOrder> pageInfo = JSON.parseObject(s, PageInfo.class);
        return pageInfo;
    }

	
	

	 @Override
    public PageInfo<TbOrder> findPage(Integer pageNo, Integer pageSize, TbOrder order) {
        PageHelper.startPage(pageNo,pageSize);

        Example example = new Example(TbOrder.class);
        Example.Criteria criteria = example.createCriteria();

        if(order!=null){			
						if(StringUtils.isNotBlank(order.getPaymentType())){
				criteria.andLike("paymentType","%"+order.getPaymentType()+"%");
				//criteria.andPaymentTypeLike("%"+order.getPaymentType()+"%");
			}
			if(StringUtils.isNotBlank(order.getPostFee())){
				criteria.andLike("postFee","%"+order.getPostFee()+"%");
				//criteria.andPostFeeLike("%"+order.getPostFee()+"%");
			}
			if(StringUtils.isNotBlank(order.getStatus())){
				criteria.andLike("status","%"+order.getStatus()+"%");
				//criteria.andStatusLike("%"+order.getStatus()+"%");
			}
			if(StringUtils.isNotBlank(order.getShippingName())){
				criteria.andLike("shippingName","%"+order.getShippingName()+"%");
				//criteria.andShippingNameLike("%"+order.getShippingName()+"%");
			}
			if(StringUtils.isNotBlank(order.getShippingCode())){
				criteria.andLike("shippingCode","%"+order.getShippingCode()+"%");
				//criteria.andShippingCodeLike("%"+order.getShippingCode()+"%");
			}
			if(StringUtils.isNotBlank(order.getUserId())){
				criteria.andLike("userId","%"+order.getUserId()+"%");
				//criteria.andUserIdLike("%"+order.getUserId()+"%");
			}
			if(StringUtils.isNotBlank(order.getBuyerMessage())){
				criteria.andLike("buyerMessage","%"+order.getBuyerMessage()+"%");
				//criteria.andBuyerMessageLike("%"+order.getBuyerMessage()+"%");
			}
			if(StringUtils.isNotBlank(order.getBuyerNick())){
				criteria.andLike("buyerNick","%"+order.getBuyerNick()+"%");
				//criteria.andBuyerNickLike("%"+order.getBuyerNick()+"%");
			}
			if(StringUtils.isNotBlank(order.getBuyerRate())){
				criteria.andLike("buyerRate","%"+order.getBuyerRate()+"%");
				//criteria.andBuyerRateLike("%"+order.getBuyerRate()+"%");
			}
			if(StringUtils.isNotBlank(order.getReceiverAreaName())){
				criteria.andLike("receiverAreaName","%"+order.getReceiverAreaName()+"%");
				//criteria.andReceiverAreaNameLike("%"+order.getReceiverAreaName()+"%");
			}
			if(StringUtils.isNotBlank(order.getReceiverMobile())){
				criteria.andLike("receiverMobile","%"+order.getReceiverMobile()+"%");
				//criteria.andReceiverMobileLike("%"+order.getReceiverMobile()+"%");
			}
			if(StringUtils.isNotBlank(order.getReceiverZipCode())){
				criteria.andLike("receiverZipCode","%"+order.getReceiverZipCode()+"%");
				//criteria.andReceiverZipCodeLike("%"+order.getReceiverZipCode()+"%");
			}
			if(StringUtils.isNotBlank(order.getReceiver())){
				criteria.andLike("receiver","%"+order.getReceiver()+"%");
				//criteria.andReceiverLike("%"+order.getReceiver()+"%");
			}
			if(StringUtils.isNotBlank(order.getInvoiceType())){
				criteria.andLike("invoiceType","%"+order.getInvoiceType()+"%");
				//criteria.andInvoiceTypeLike("%"+order.getInvoiceType()+"%");
			}
			if(StringUtils.isNotBlank(order.getSourceType())){
				criteria.andLike("sourceType","%"+order.getSourceType()+"%");
				//criteria.andSourceTypeLike("%"+order.getSourceType()+"%");
			}
			if(StringUtils.isNotBlank(order.getSellerId())){
				criteria.andLike("sellerId","%"+order.getSellerId()+"%");
				//criteria.andSellerIdLike("%"+order.getSellerId()+"%");
			}
	
		}
        List<TbOrder> all = orderMapper.selectByExample(example);
        PageInfo<TbOrder> info = new PageInfo<TbOrder>(all);
        //序列化再反序列化
        String s = JSON.toJSONString(info);
        PageInfo<TbOrder> pageInfo = JSON.parseObject(s, PageInfo.class);

        return pageInfo;
    }


	/**
	 * 读取支付日志
	 * @param userId
	 * @return
	 */
	@Override
	public TbPayLog searchPayLogFromRedis(String userId) {
		return (TbPayLog) redisTemplate.boundHashOps("payLog").get(userId);
	}


	/**
	 * 修改订单状态
	 * @param out_trade_no
	 * @param transaction_id
	 */
	@Override
	public void updateOrderStatus(String out_trade_no, String transaction_id) {
		//1.根据订单号 查询支付日志对象  更新他的状态
		TbPayLog payLog = payLogMapper.selectByPrimaryKey(out_trade_no);
		payLog.setPayTime(new Date());
		payLog.setTradeState("1");// 已支付
		payLog.setTransactionId(transaction_id);//交易号

		payLogMapper.updateByPrimaryKey(payLog);

		//2.根据支付日志 获取到商品订单列表 更新商品订单状态
		String orderList = payLog.getOrderList(); //获取订单号列表   37,38
		String[] orderIds  = orderList.split(","); //获取订单号数组 37 38
		for (String orderId : orderIds ) {
			TbOrder tbOrder = orderMapper.selectByPrimaryKey(Long.parseLong(orderId));
			tbOrder.setStatus("2");//已付款
			tbOrder.setUpdateTime(new Date());
			tbOrder.setPaymentTime(tbOrder.getUpdateTime());

			orderMapper.updateByPrimaryKey(tbOrder);
		}

		// 3.根据支付日志 获取到USER_id 删除reids中的记录
		// 清除redis缓存数据
		redisTemplate.boundHashOps("payLog").delete(payLog.getUserId());
	}


	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private IdWorker idWorker;

	@Autowired
	private TbOrderItemMapper orderItemMapper;

	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private TbPayLogMapper payLogMapper;

	@Override
	public void add(TbOrder order) {
		//1.获取页面传递的数据

		//2.插入到订单表中         拆单(一个商家就是一个订单)  订单的ID 要生成

		//2.1 获取reids中的购物车数据
		List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("CART_REDIS_KEY").get(order.getUserId());

		double total_money = 0;//总金额 （元）


		ArrayList<String> orderList = new ArrayList<>();
		//2.2 循环遍历 每一个Cart 对象  就是一个商家
		for (Cart cart : cartList) {
			long orderId = idWorker.nextId();
			orderList.add(orderId+"");
			//System.out.println("sellerId:"+cart.getSellerId());
			//创建订单对象
			TbOrder tbOrder = new TbOrder();
			//订单id
			tbOrder.setOrderId(orderId);
			//用户名
			tbOrder.setUserId(order.getUserId());
			//支付类型
			tbOrder.setPaymentType(order.getPaymentType());
			//状态：未付款
			tbOrder.setStatus("1");
			//订单创建日期
			tbOrder.setCreateTime(new Date());
			//订单更新日期
			tbOrder.setUpdateTime(new Date());
			//地址
			tbOrder.setReceiverAreaName(order.getReceiverAreaName());
			//手机号
			tbOrder.setReceiverMobile(order.getReceiverMobile());
			//收货人
			tbOrder.setReceiver(order.getReceiver());
			//订单来源
			tbOrder.setSourceType(order.getSourceType());
			//商家Id
			tbOrder.setSellerId(cart.getSellerId());

			//循环购物车明细
			double money = 0;
			for (TbOrderItem orderItem : cart.getOrderItemList()) {
				//3.插入到订单选项表中
				orderItem.setId(idWorker.nextId());
				//订单id
				orderItem.setOrderId(orderId);
				orderItem.setSellerId(cart.getSellerId());
				//商品
				TbItem item = itemMapper.selectByPrimaryKey(orderItem.getItemId());
				//设置商品的spu的id
				orderItem.setGoodsId(item.getGoodsId());
				//金额累加
				money += orderItem.getTotalFee().doubleValue();
				orderItemMapper.insert(orderItem);
			}
			tbOrder.setPayment(new BigDecimal(money));

			total_money += money;
			orderMapper.insert(tbOrder);
		}

		//添加支付的日志
		TbPayLog payLog = new TbPayLog();
		//支付订单号
		payLog.setOutTradeNo(idWorker.nextId()+"");
		//创建时间
		payLog.setCreateTime(new Date());
		//支付金额
		long fen = (long) (total_money * 100);//fen
		payLog.setTotalFee(fen);
		//用户ID
		payLog.setUserId(order.getUserId());
		//交易状态
		payLog.setTradeState("0");//未支付
		//订单号列表，逗号分隔
		String ids=orderList.toString().replace("[", "").replace("]", "").replace("", "");//[1,2]
		payLog.setOrderList(ids);
		//支付类型
		payLog.setPayType(order.getPaymentType());// 微信支付

		//插入到支付日志表
		payLogMapper.insert(payLog);
		//存储到redis中  bigkey field  value
		redisTemplate.boundHashOps("payLog").put(order.getUserId(),payLog);


		//删除redis中的购物车数据
		redisTemplate.boundHashOps("cartList").delete(order.getUserId());

	}
}
