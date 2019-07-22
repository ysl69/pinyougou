package com.pinyougou.es.service.impl;

import com.alibaba.fastjson.JSON;
import com.pinyougou.es.dao.ItemDao;
import com.pinyougou.es.service.ItemService;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author ysl
 * @Date 2019/7/22 22:25
 * @Description:
 **/

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao dao;

    @Autowired
    private TbItemMapper itemMapper;

    /**
     * 从数据库中获取数据 导入到ES的索引库
     */
    @Override
    public void ImportDataToEs() {
        //1.从数据库查询出符合条件的tbitem的数据
        TbItem tbItem = new TbItem();

        tbItem.setStatus("1"); //审核过的
        List<TbItem> itemList = itemMapper.select(tbItem);
        for (TbItem item : itemList) {
            String spec = item.getSpec();
            if (StringUtils.isNotBlank(spec)){
                Map<String,String> map = JSON.parseObject(spec, Map.class);
                tbItem.setSpecMap(map);
            }
        }
        //2.保存即可
        dao.saveAll(itemList);
    }
}
