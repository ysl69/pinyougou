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
 * @Date 2019/6/21 19:11
 * @Description: TODO
 **/


@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class MybatisTest {

    @Autowired
    private TbBrandMapper brandMapper;


    // 查询
    @Test
    public void testSelect(){
        List<TbBrand> tbBrands = brandMapper.selectByExample(null);
        for (TbBrand tbBrand : tbBrands) {
            System.out.println("id:"+tbBrand.getId()+";name:"+tbBrand.getName());
        }
    }




    // 新增
    @Test
    public void testInsert(){
        TbBrand brand = new TbBrand();
        brand.setName("HHH");
        brand.setFirstChar("H");
        brandMapper.insert(brand);
    }




    // 修改
    @Test
    public void testUpdate(){
        TbBrand brand = new TbBrand();
        brand.setId(37L);
        brand.setName("HHHS");
        brand.setFirstChar("H");
        brandMapper.updateByPrimaryKey(brand);
    }


    // 删除
    @Test
    public void testDelete(){
        brandMapper.deleteByPrimaryKey(37L);
    }
}
