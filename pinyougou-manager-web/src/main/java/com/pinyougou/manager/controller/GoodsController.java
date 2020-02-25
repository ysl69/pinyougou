package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.MessageInfo;
import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import com.pinyougou.sellergoods.service.GoodsService;
import entity.Goods;
import entity.Result;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Reference
	private GoodsService goodsService;

	@Reference
	private ItemSearchService itemSearchService;

	@Reference
	private ItemPageService pageService;

	@Autowired
	private DefaultMQProducer producer;


	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){			
		return goodsService.findAll();
	}
	
	
	
	@RequestMapping("/findPage")
    public PageInfo<TbGoods> findPage(@RequestParam(value = "pageNo", defaultValue = "1", required = true) Integer pageNo,
                                      @RequestParam(value = "pageSize", defaultValue = "10", required = true) Integer pageSize) {
        return goodsService.findPage(pageNo, pageSize);
    }

	@RequestMapping("/add")
	public Result add(@RequestBody Goods goods){
		try {
			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			goods.getGoods().setSellerId(name);
			goodsService.add(goods);
			return new Result(true,"增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"增加失败");
		}
	}


	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody Goods goods){
		try {
			goodsService.update(goods);
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
	public Goods findOne(@PathVariable(value = "id") Long id){
		return goodsService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(@RequestBody Long[] ids){
		try {
			goodsService.delete(ids);
			//调用搜素服务的方法  执行删除ES的数据。
			//itemSearchService.deleteByIds(ids);

			//发送消息
			//2.消息封装到POJO
			MessageInfo info = new MessageInfo(ids, "Goods_Topic", "goods_delete_tag", "delete", MessageInfo.METHOD_DELETE);
			//3.将数据作为消息体 发送mq服务器上
			Message mssage = new Message(info.getTopic(),info.getTags(),info.getKeys(),JSON.toJSONString(info).getBytes());
			SendResult send = producer.send(mssage);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
	

	@RequestMapping("/search")
    public PageInfo<TbGoods> findPage(@RequestParam(value = "pageNo", defaultValue = "1", required = true) Integer pageNo,
                                      @RequestParam(value = "pageSize", defaultValue = "10", required = true) Integer pageSize,
                                      @RequestBody TbGoods goods) {
		//goods.setSellerId(SecurityContextHolder.getContext().getAuthentication().getName());
        return goodsService.findPage(pageNo, pageSize, goods);
    }


	//调用搜索服务的方法 实现同步 //更新
    @RequestMapping("/updateStatus")
    public Result updateStatus(@RequestBody Long[] ids, String status){
		try {
			goodsService.updateStatus(ids,status);

			if (status.equals("1")){
				// 发送消息
				//1.获取审核的数据
				List<TbItem> tbItemList = goodsService.findTbItemListByIds(ids);

				//2.消息封装到POJO
				MessageInfo info = new MessageInfo(tbItemList, "Goods_Topic", "goods_update_tag", "updateStatus", MessageInfo.METHOD_UPDATE);

				//3.将数据作为消息体 发送mq服务器上
				Message mssage = new Message(info.getTopic(),info.getTags(),info.getKeys(), JSON.toJSONString(info).getBytes());
				SendResult send = producer.send(mssage);
				System.out.println(">>>>"+send.getSendStatus());

				/*//1.根据审核的SPU的ID 获取SKU的列表数据
				List<TbItem> tbItemList = goodsService.findTbItemListByIds(ids);
				//2.调用搜索服务的方法 （传递SKU的列表数据过去）内部执行更新的动作。
				itemSearchService.updateIndex(tbItemList);

				//3.生成静态页面
				//调用静态化服务的方法 根据 商品的ID(spu的ID) 直接从数据库查询 并生成静态页面（服务方法的内部的）
				for (Long id : ids) {
					pageService.genItemHtml(id);
				}*/
			}
			return new Result(true,"更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"更新失败");
		}
	}

}
