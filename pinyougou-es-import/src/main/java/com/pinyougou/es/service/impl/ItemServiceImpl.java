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
 * @Date 2019/7/2 21:48
 * @Description:
 **/

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao dao;

    @Autowired
    private TbItemMapper itemMapper;


    @Override
    public void ImportDataToEs() {
        //1.从数据库查询出符合条件的tbitem的数据
        TbItem record = new TbItem();
        record.setStatus("1");//审核过的
        List<TbItem> itemList = itemMapper.select(record);
        for (TbItem tbItem : itemList) {
            String spec = tbItem.getSpec();
            if (StringUtils.isNotBlank(spec)){
                Map<String,String> map = JSON.parseObject(spec, Map.class);
                tbItem.setSpecMap(map);
            }
        }
        // 2.保存
        dao.saveAll(itemList);
    }
}
