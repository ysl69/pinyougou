package com.pinyougou.sellergoods.service.impl;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo; 									  
import org.apache.commons.lang3.StringUtils;
import com.pinyougou.core.service.CoreServiceImpl;

import tk.mybatis.mapper.entity.Example;

import com.pinyougou.sellergoods.service.GoodsService;



/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class GoodsServiceImpl extends CoreServiceImpl<TbGoods>  implements GoodsService {

	
	private TbGoodsMapper goodsMapper;

	@Autowired
	private TbGoodsDescMapper goodsDescMapper;

	@Autowired
	public GoodsServiceImpl(TbGoodsMapper goodsMapper) {
		super(goodsMapper, TbGoods.class);
		this.goodsMapper=goodsMapper;
	}

	
	

	
	@Override
    public PageInfo<TbGoods> findPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<TbGoods> all = goodsMapper.selectAll();
        PageInfo<TbGoods> info = new PageInfo<TbGoods>(all);

        //序列化再反序列化
        String s = JSON.toJSONString(info);
        PageInfo<TbGoods> pageInfo = JSON.parseObject(s, PageInfo.class);
        return pageInfo;
    }

	
	

	 @Override
    public PageInfo<TbGoods> findPage(Integer pageNo, Integer pageSize, TbGoods goods) {
        PageHelper.startPage(pageNo,pageSize);

        Example example = new Example(TbGoods.class);
        Example.Criteria criteria = example.createCriteria();

        if(goods!=null){			
        	if(StringUtils.isNotBlank(goods.getSellerId())){
				//criteria.andLike("sellerId","%"+goods.getSellerId()+"%");
				//criteria.andSellerIdLike("%"+goods.getSellerId()+"%");
				criteria.andEqualTo("sellerId",goods.getSellerId());
			}
			if(StringUtils.isNotBlank(goods.getGoodsName())){
				criteria.andLike("goodsName","%"+goods.getGoodsName()+"%");
				//criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			}
			if(StringUtils.isNotBlank(goods.getAuditStatus())){
				criteria.andLike("auditStatus","%"+goods.getAuditStatus()+"%");
				//criteria.andAuditStatusLike("%"+goods.getAuditStatus()+"%");
			}
			if(StringUtils.isNotBlank(goods.getIsMarketable())){
				criteria.andLike("isMarketable","%"+goods.getIsMarketable()+"%");
				//criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
			}
			if(StringUtils.isNotBlank(goods.getCaption())){
				criteria.andLike("caption","%"+goods.getCaption()+"%");
				//criteria.andCaptionLike("%"+goods.getCaption()+"%");
			}
			if(StringUtils.isNotBlank(goods.getSmallPic())){
				criteria.andLike("smallPic","%"+goods.getSmallPic()+"%");
				//criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
			}
			if(StringUtils.isNotBlank(goods.getIsEnableSpec())){
				criteria.andLike("isEnableSpec","%"+goods.getIsEnableSpec()+"%");
				//criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
			}
	
		}
        List<TbGoods> all = goodsMapper.selectByExample(example);
        PageInfo<TbGoods> info = new PageInfo<TbGoods>(all);
        //序列化再反序列化
        String s = JSON.toJSONString(info);
        PageInfo<TbGoods> pageInfo = JSON.parseObject(s, PageInfo.class);

        return pageInfo;
    }


    @Autowired
    private TbItemCatMapper itemCatMapper;

	@Autowired
	private TbSellerMapper sellerMapper;

	@Autowired
	private TbBrandMapper brandMapper;

	@Autowired
	private TbItemMapper itemMapper;

	@Override
	public void add(Goods goods) {
		//1.获取goods
		TbGoods tbGoods = goods.getGoods();
		tbGoods.setAuditStatus("0");
		tbGoods.setIsDelete(false);
		goodsMapper.insert(tbGoods);

		//2.获取goodsDesc
		TbGoodsDesc goodsDesc = goods.getGoodsDesc();
		goodsDesc.setGoodsId(tbGoods.getId());
		goodsDescMapper.insert(goodsDesc);

		saveItems(goods,tbGoods,goodsDesc);
	}


	@Autowired
	private TbItemMapper tbItemMapper;


	/**
	 * 根据id获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Goods findOne(Long id) {
		Goods goods = new Goods();
		TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
		TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(id);

		TbItem record = new TbItem();
		record.setGoodsId(id);
		List<TbItem> tbItemList = tbItemMapper.select(record);
		goods.setGoods(tbGoods);
		goods.setGoodsDesc(tbGoodsDesc);
		goods.setItemList(tbItemList);
		return goods;
	}


	/**
	 * 更新数据
	 * @param goods
	 */
	@Override
	public void update(Goods goods) {
		TbGoods tbGoods = goods.getGoods();
		tbGoods.setAuditStatus("0");
		goodsMapper.updateByPrimaryKey(tbGoods);

		//更新描述
		goodsDescMapper.updateByPrimaryKey(goods.getGoodsDesc());

		// 更新sku 先删除原来的SKUid对应的SKU的列表
		TbItem tbItem = new TbItem();
		tbItem.setGoodsId(tbGoods.getId());
		tbItemMapper.delete(tbItem);

		// 新增 判断是否为启用状态
		saveItems(goods,tbGoods,goods.getGoodsDesc());

	}


	/**
	 * 批量修改状态
	 * @param ids
	 * @param status
	 */
	@Override
	public void updateStatus(Long[] ids, String status) {
		TbGoods tbGoods = new TbGoods();
		tbGoods.setAuditStatus(status);
		Example example = new Example(TbGoods.class);
		example.createCriteria().andIn("id",Arrays.asList(ids));
		goodsMapper.updateByExampleSelective(tbGoods,example);//update set status=1 where id in (12,3)
	}


	private void saveItems(Goods goods, TbGoods tbGoods,TbGoodsDesc goodsDesc) {

		if("1".equals(tbGoods.getIsEnableSpec())) {

			//3.获取skuList TODO
			//先获取sku列表
			List<TbItem> itemList = goods.getItemList();

			for (TbItem tbItem : itemList) {
				//设置title  SPU名 + 空格+ 规格名称 +
				String spec = tbItem.getSpec();//{"网络":"移动4G","机身内存":"16G"}
				String title = tbGoods.getGoodsName();
				Map map = JSON.parseObject(spec, Map.class);
				for (Object key : map.keySet()) {
					Object o1 = map.get(key);
					title += "" + o1;
				}
				tbItem.setTitle(title);

				//设置图片从goodsDesc中获取
				//[{"color":"黑色","url":"http://192.168.25.133/group1/M00/00/03/wKgZhVq7N-qAEDgSAAJfMemqtP8461.jpg"}]
				String itemImages = goods.getGoodsDesc().getItemImages();
				List<Map> maps = JSON.parseArray(itemImages, Map.class);
				// 图片地址
				String url = maps.get(0).get("url").toString();
				tbItem.setImage(url);

				// 设置分类
				TbItemCat tbItemCat = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id());
				tbItem.setCategoryid(tbItemCat.getId());
				tbItem.setCategory(tbItemCat.getName());

				//时间
				tbItem.setCreateTime(new Date());
				tbItem.setUpdateTime(new Date());

				// 设置SPU的id
				tbItem.setGoodsId(tbGoods.getId());

				//设置商家
				TbSeller tbSeller = sellerMapper.selectByPrimaryKey(tbGoods.getSellerId());
				tbItem.setSellerId(tbSeller.getSellerId());
				tbItem.setSeller(tbSeller.getName());//店铺名

				//设置店铺名
				TbBrand tbBrand = brandMapper.selectByPrimaryKey(tbGoods.getBrandId());
				tbItem.setBrand(tbBrand.getName());

				itemMapper.insert(tbItem);
			}
		}else {
			//插入到SKU表 一条记录
			TbItem tbItem = new TbItem();
			tbItem.setTitle(tbGoods.getGoodsName());
			tbItem.setPrice(tbGoods.getPrice());
			tbItem.setNum(999);//默认一个
			tbItem.setStatus("1");//正常启用
			tbItem.setIsDefault("1");//默认的

			tbItem.setSpec("{}");


			//设置图片从goodsDesc中获取
			//[{"color":"黑色","url":"http://192.168.25.133/group1/M00/00/03/wKgZhVq7N-qAEDgSAAJfMemqtP8461.jpg"}]
			String itemImages = goodsDesc.getItemImages();//

			List<Map> maps = JSON.parseArray(itemImages, Map.class);

			String url = maps.get(0).get("url").toString();//图片的地址
			tbItem.setImage(url);

			//设置分类
			TbItemCat tbItemCat = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id());
			tbItem.setCategoryid(tbItemCat.getId());
			tbItem.setCategory(tbItemCat.getName());

			//时间
			tbItem.setCreateTime(new Date());
			tbItem.setUpdateTime(new Date());

			//设置SPU的ID
			tbItem.setGoodsId(tbGoods.getId());

			//设置商家
			TbSeller tbSeller = sellerMapper.selectByPrimaryKey(tbGoods.getSellerId());
			tbItem.setSellerId(tbSeller.getSellerId());
			tbItem.setSeller(tbSeller.getNickName());//店铺名

			//设置品牌名后
			TbBrand tbBrand = brandMapper.selectByPrimaryKey(tbGoods.getBrandId());
			tbItem.setBrand(tbBrand.getName());
			itemMapper.insert(tbItem);
		}
	}


}
