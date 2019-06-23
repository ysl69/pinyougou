package com.pinyougou.sellergoods.test;

import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author ysl
 * @Date 2019/6/23 17:37
 * @Description: TODO
 **/


@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
@RunWith(SpringRunner.class)
public class MybatisTest {

    @Autowired
    private TbBrandMapper mapper;


    //查询
    @Test
    public void testSelect(){
        List<TbBrand> tbBrands = mapper.selectByExample(null);
        for (TbBrand tbBrand : tbBrands) {
            System.out.println("id:"+tbBrand.getId()+";name:"+tbBrand.getName());
        }
    }
    //新增
    @Test
    public void testInsert(){
        TbBrand brand = new TbBrand();
        brand.setName("HHH");
        brand.setFirstChar("H");
        mapper.insert(brand);
    }

    //修改
    @Test
    public void testUpdate(){
        TbBrand brand = new TbBrand();
        brand.setId(37L);
        brand.setName("HHHS");
        brand.setFirstChar("H");
        mapper.updateByPrimaryKey(brand);
    }
    //删除

    @Test
    public void testDelete(){
        mapper.deleteByPrimaryKey(37L);
    }


}

