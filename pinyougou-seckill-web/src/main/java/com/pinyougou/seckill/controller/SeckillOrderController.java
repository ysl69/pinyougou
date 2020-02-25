package com.pinyougou.seckill.controller;
import java.util.List;

import com.pinyougou.seckill.service.SeckillOrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeckillOrder;


import com.github.pagehelper.PageInfo;
import entity.Result;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/seckillOrder")
public class SeckillOrderController {

	@Reference
	private SeckillOrderService seckillOrderService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSeckillOrder> findAll(){			
		return seckillOrderService.findAll();
	}
	
	
	
	@RequestMapping("/findPage")
    public PageInfo<TbSeckillOrder> findPage(@RequestParam(value = "pageNo", defaultValue = "1", required = true) Integer pageNo,
                                      @RequestParam(value = "pageSize", defaultValue = "10", required = true) Integer pageSize) {
        return seckillOrderService.findPage(pageNo, pageSize);
    }
	
	/**
	 * 增加
	 * @param seckillOrder
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbSeckillOrder seckillOrder){
		try {
			seckillOrderService.add(seckillOrder);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param seckillOrder
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbSeckillOrder seckillOrder){
		try {
			seckillOrderService.update(seckillOrder);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne/{id}")
	public TbSeckillOrder findOne(@PathVariable(value = "id") Long id){
		return seckillOrderService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(@RequestBody Long[] ids){
		try {
			seckillOrderService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
	

	@RequestMapping("/search")
    public PageInfo<TbSeckillOrder> findPage(@RequestParam(value = "pageNo", defaultValue = "1", required = true) Integer pageNo,
                                      @RequestParam(value = "pageSize", defaultValue = "10", required = true) Integer pageSize,
                                      @RequestBody TbSeckillOrder seckillOrder) {
        return seckillOrderService.findPage(pageNo, pageSize, seckillOrder);
    }


	/**
	 * 秒杀下订单
	 * @param seckillId
	 * @return
	 */
	@RequestMapping("/submitOrder/{seckillId}")
	public Result submitOrder(@PathVariable(value = "seckillId") Long seckillId){
		try {
			String userId = SecurityContextHolder.getContext().getAuthentication().getName();
			if ("anonymousUser".equals(userId)){
				//表示没有登录
				return new Result(false,"403");
			}
			seckillOrderService.submitOrder(seckillId,userId);
			return new Result(true,"正在排队中,请稍等");
		} catch (RuntimeException e) {
			e.printStackTrace();
			return new Result(false,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return new Result(false, "抢单失败");
		}

	}


	/**
	 * 查询下单状态
	 * @return
	 */
	@RequestMapping("/queryOrderStatus")
	public Result queryOrderStatus(){
		try {
			String userId = SecurityContextHolder.getContext().getAuthentication().getName();

			if ("anonymousUser".equals(userId)) {
				//表示没有登录
				return new Result(false, "403");
			}

			TbSeckillOrder userOrderStatus = seckillOrderService.getUserOrderStatus(userId);
			//说明订单生成成功
			if (userOrderStatus != null) {
				return new Result(true, "待支付");
			} else {
				return new Result(false, "正在排队中,请稍等");
			}
		}catch (RuntimeException e){
			return new Result(false, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "抢单失败");
		}
	}
}
