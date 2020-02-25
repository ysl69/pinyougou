package com.pinyougou.sellergoods.service.impl;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecificationOption;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo; 									  
import org.apache.commons.lang3.StringUtils;
import com.pinyougou.core.service.CoreServiceImpl;

import org.springframework.data.redis.core.RedisTemplate;
import tk.mybatis.mapper.entity.Example;

import com.pinyougou.mapper.TbTypeTemplateMapper;
import com.pinyougou.pojo.TbTypeTemplate;  

import com.pinyougou.sellergoods.service.TypeTemplateService;



/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class TypeTemplateServiceImpl extends CoreServiceImpl<TbTypeTemplate>  implements TypeTemplateService {

	
	private TbTypeTemplateMapper typeTemplateMapper;

	@Autowired
	private TbSpecificationOptionMapper optionMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	public TypeTemplateServiceImpl(TbTypeTemplateMapper typeTemplateMapper) {
		super(typeTemplateMapper, TbTypeTemplate.class);
		this.typeTemplateMapper=typeTemplateMapper;
	}


	/**
	 * 返回规格列表
	 * @param typeTemplateId
	 * @return
	 */
	@Override
	public List<Map> findSpecList(Long typeTemplateId) {
		//1.根据主键 查询模板的对象
		TbTypeTemplate tbTypeTemplate = typeTemplateMapper.selectByPrimaryKey(typeTemplateId);
		//2.获取模板的对象中的规格列表String
		// [{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
		String specIds = tbTypeTemplate.getSpecIds();
		//3.将字符串转成JSON对象数组
		List<Map> maps = JSON.parseArray(specIds, Map.class);

		for (Map map : maps) {
			//{"id":27,"text":"网络"}
			//4.循环遍历件SON数组 根据规格的ID 获取规格的下的所有的选项列表
			Integer id = (Integer) map.get("id");
			//select * from option where spec_id=id
			TbSpecificationOption condition = new TbSpecificationOption();
			condition.setSpecId(Long.valueOf(id));
			//[{optionName:'移动3G'},{optionName:'移动4G'}]
			List<TbSpecificationOption> optionList = optionMapper.select(condition);
			//5.拼接成：[{"id":27,"text":"网络",optionsList:[{optionName:'移动3G'},{optionName:'移动4G'}]},{"id":32,"text":"机身内存"}]
			map.put("optionList",optionList);
		}
		//6.返回
		return maps;
	}

	@Override
    public PageInfo<TbTypeTemplate> findPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<TbTypeTemplate> all = typeTemplateMapper.selectAll();
        PageInfo<TbTypeTemplate> info = new PageInfo<TbTypeTemplate>(all);

        //序列化再反序列化
        String s = JSON.toJSONString(info);
        PageInfo<TbTypeTemplate> pageInfo = JSON.parseObject(s, PageInfo.class);
        return pageInfo;
    }

	
	

	 @Override
    public PageInfo<TbTypeTemplate> findPage(Integer pageNo, Integer pageSize, TbTypeTemplate typeTemplate) {
        PageHelper.startPage(pageNo,pageSize);

        Example example = new Example(TbTypeTemplate.class);
        Example.Criteria criteria = example.createCriteria();

        if(typeTemplate!=null){			
						if(StringUtils.isNotBlank(typeTemplate.getName())){
				criteria.andLike("name","%"+typeTemplate.getName()+"%");
				//criteria.andNameLike("%"+typeTemplate.getName()+"%");
			}
			if(StringUtils.isNotBlank(typeTemplate.getSpecIds())){
				criteria.andLike("specIds","%"+typeTemplate.getSpecIds()+"%");
				//criteria.andSpecIdsLike("%"+typeTemplate.getSpecIds()+"%");
			}
			if(StringUtils.isNotBlank(typeTemplate.getBrandIds())){
				criteria.andLike("brandIds","%"+typeTemplate.getBrandIds()+"%");
				//criteria.andBrandIdsLike("%"+typeTemplate.getBrandIds()+"%");
			}
			if(StringUtils.isNotBlank(typeTemplate.getCustomAttributeItems())){
				criteria.andLike("customAttributeItems","%"+typeTemplate.getCustomAttributeItems()+"%");
				//criteria.andCustomAttributeItemsLike("%"+typeTemplate.getCustomAttributeItems()+"%");
			}
	
		}
        List<TbTypeTemplate> all = typeTemplateMapper.selectByExample(example);
        PageInfo<TbTypeTemplate> info = new PageInfo<TbTypeTemplate>(all);
        //序列化再反序列化
        String s = JSON.toJSONString(info);
        PageInfo<TbTypeTemplate> pageInfo = JSON.parseObject(s, PageInfo.class);


        // 1.获取模板数据
		 List<TbTypeTemplate> typeTemplateList = this.findAll();
		 //2.存储到redis中
		 for (TbTypeTemplate tbTypeTemplate : typeTemplateList) {
			 // 存储品牌列表
			 List<Map> brandList = JSON.parseArray(tbTypeTemplate.getBrandIds(), Map.class);
			 redisTemplate.boundHashOps("brandList").put(tbTypeTemplate.getId(),brandList);

			 // 存储规格列表
			 List<Map> specList = findSpecList(tbTypeTemplate.getId());// 根据模板id查询规格列表
			 redisTemplate.boundHashOps("specList").put(tbTypeTemplate.getId(),specList);
		 }
		 return pageInfo;
    }
	
}
