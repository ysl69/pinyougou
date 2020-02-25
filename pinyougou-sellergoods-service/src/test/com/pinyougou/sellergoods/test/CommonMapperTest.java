package com.pinyougou.sellergoods.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ysl
 * @Date 2019/6/22 9:29
 * @Description: TODO
 **/

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class CommonMapperTest {

    @Autowired
    private TbBrandMapper brandMapper;

    // 新增
    @Test
    public void testInsert(){
        TbBrand brand = new TbBrand();
        brand.setName("HHH");
        brand.setFirstChar("H");
        brandMapper.insert(brand);
    }



    // 删除
    @Test
    public void testDelete(){
        // 根据主键删除
        brandMapper.deleteByPrimaryKey(32L);

        // 根据条件删除  delete from tb_brand where id in (32)
        Example example = new Example(TbBrand.class);
        Example.Criteria criteria = example.createCriteria();
        ArrayList<Object> ids = new ArrayList<>();
        ids.add(31L);
        criteria.andIn("id",ids);
        brandMapper.deleteByExample(example);


        // 根据条件来删除  delete from tb_brand where id=31
        TbBrand record = new TbBrand();
        record.setId(31L);
        brandMapper.delete(record);

    }


    // 修改
    @Test
    public void testUpdate(){
        //update  tb_brand set id=? ,name=? ,firstchar=? where id=36
        //如果 firstchar 没有传递updateByPrimaryKey() 方法将会更新成null 到数据库中   updateByPrimaryKeySelective() 则不会更新null到数据库中
        TbBrand tbBrand = new TbBrand();
        tbBrand.setId(31L);
        tbBrand.setName("海豚1");
        tbBrand.setFirstChar("H");
        brandMapper.updateByPrimaryKey(tbBrand);
    }



    // 查询
    @Test
    public void testSelect(){
        // 根据主键查询
        TbBrand tbBrand1 = brandMapper.selectByPrimaryKey(30L);
        System.out.println(tbBrand1.getName());


        // 根据条件查询，如果参数example空，则查询所有
        // select * from tb_brand where id in (31)
        Example example = new Example(TbBrand.class);
        Example.Criteria criteria = example.createCriteria();
        List<Long> ids = new ArrayList<>();
        ids.add(31L);
        criteria.andIn("id",ids);
        brandMapper.selectByExample(example);



        // 根据等号条件查询
        //select * from tb_brand name="海豚1" and first_char='H'
        TbBrand tbBrand = new TbBrand();
        tbBrand.setName("海豚1");
        tbBrand.setFirstChar("H");
        brandMapper.select(tbBrand);
    }




    // 分页
    @Test
    public void testPage(){
        //page 当前页    size 每页显示多少条
        int page = 1,size=10;

        //分页处理,只需要调用PageHelper.startPage静态方法即可。
        PageHelper.startPage(page,size);

        // 查询
        List<TbBrand> brands = brandMapper.selectAll();

        //获取分页信息,注意这里传入了brands集合对象
        PageInfo<TbBrand> pageInfo = new PageInfo<TbBrand>();
        System.out.println(pageInfo);
    }
}
