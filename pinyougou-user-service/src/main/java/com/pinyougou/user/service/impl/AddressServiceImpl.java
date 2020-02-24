package com.pinyougou.user.service.impl;
import java.util.Arrays;
import java.util.List;

import com.pinyougou.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo; 									  
import org.apache.commons.lang3.StringUtils;
import com.pinyougou.core.service.CoreServiceImpl;

import tk.mybatis.mapper.entity.Example;

import com.pinyougou.mapper.TbAddressMapper;
import com.pinyougou.pojo.TbAddress;  





/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class AddressServiceImpl extends CoreServiceImpl<TbAddress>  implements AddressService {

	
	private TbAddressMapper addressMapper;

	@Autowired
	public AddressServiceImpl(TbAddressMapper addressMapper) {
		super(addressMapper, TbAddress.class);
		this.addressMapper=addressMapper;
	}

	
	

	
	@Override
    public PageInfo<TbAddress> findPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<TbAddress> all = addressMapper.selectAll();
        PageInfo<TbAddress> info = new PageInfo<TbAddress>(all);

        //序列化再反序列化
        String s = JSON.toJSONString(info);
        PageInfo<TbAddress> pageInfo = JSON.parseObject(s, PageInfo.class);
        return pageInfo;
    }

	
	

	 @Override
    public PageInfo<TbAddress> findPage(Integer pageNo, Integer pageSize, TbAddress address) {
        PageHelper.startPage(pageNo,pageSize);

        Example example = new Example(TbAddress.class);
        Example.Criteria criteria = example.createCriteria();

        if(address!=null){			
						if(StringUtils.isNotBlank(address.getUserId())){
				criteria.andLike("userId","%"+address.getUserId()+"%");
				//criteria.andUserIdLike("%"+address.getUserId()+"%");
			}
			if(StringUtils.isNotBlank(address.getProvinceId())){
				criteria.andLike("provinceId","%"+address.getProvinceId()+"%");
				//criteria.andProvinceIdLike("%"+address.getProvinceId()+"%");
			}
			if(StringUtils.isNotBlank(address.getCityId())){
				criteria.andLike("cityId","%"+address.getCityId()+"%");
				//criteria.andCityIdLike("%"+address.getCityId()+"%");
			}
			if(StringUtils.isNotBlank(address.getTownId())){
				criteria.andLike("townId","%"+address.getTownId()+"%");
				//criteria.andTownIdLike("%"+address.getTownId()+"%");
			}
			if(StringUtils.isNotBlank(address.getMobile())){
				criteria.andLike("mobile","%"+address.getMobile()+"%");
				//criteria.andMobileLike("%"+address.getMobile()+"%");
			}
			if(StringUtils.isNotBlank(address.getAddress())){
				criteria.andLike("address","%"+address.getAddress()+"%");
				//criteria.andAddressLike("%"+address.getAddress()+"%");
			}
			if(StringUtils.isNotBlank(address.getContact())){
				criteria.andLike("contact","%"+address.getContact()+"%");
				//criteria.andContactLike("%"+address.getContact()+"%");
			}
			if(StringUtils.isNotBlank(address.getIsDefault())){
				criteria.andLike("isDefault","%"+address.getIsDefault()+"%");
				//criteria.andIsDefaultLike("%"+address.getIsDefault()+"%");
			}
			if(StringUtils.isNotBlank(address.getNotes())){
				criteria.andLike("notes","%"+address.getNotes()+"%");
				//criteria.andNotesLike("%"+address.getNotes()+"%");
			}
			if(StringUtils.isNotBlank(address.getAlias())){
				criteria.andLike("alias","%"+address.getAlias()+"%");
				//criteria.andAliasLike("%"+address.getAlias()+"%");
			}
	
		}
        List<TbAddress> all = addressMapper.selectByExample(example);
        PageInfo<TbAddress> info = new PageInfo<TbAddress>(all);
        //序列化再反序列化
        String s = JSON.toJSONString(info);
        PageInfo<TbAddress> pageInfo = JSON.parseObject(s, PageInfo.class);

        return pageInfo;
    }
	
}
