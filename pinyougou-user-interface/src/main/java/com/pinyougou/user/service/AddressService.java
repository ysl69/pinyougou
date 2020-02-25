package com.pinyougou.user.service;
import java.util.List;
import com.pinyougou.pojo.TbAddress;

import com.github.pagehelper.PageInfo;
import com.pinyougou.core.service.CoreService;
/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface AddressService extends CoreService<TbAddress> {
	
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	 PageInfo<TbAddress> findPage(Integer pageNo, Integer pageSize);
	
	

	/**
	 * 分页
	 * @param pageNo 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	PageInfo<TbAddress> findPage(Integer pageNo, Integer pageSize, TbAddress Address);
	
}
