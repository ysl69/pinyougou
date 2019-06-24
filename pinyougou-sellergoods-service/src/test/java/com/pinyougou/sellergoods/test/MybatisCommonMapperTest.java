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
 * @Date 2019/6/23 22:17
 * @Description: TODO
 **/

@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
@RunWith(SpringRunner.class)
public class MybatisCommonMapperTest {

    @Autowired
    private TbBrandMapper brandMapper;


    /**
     * 插入
     */
    @Test
    public void testInsert(){
        TbBrand brand = new TbBrand();
        brand.setName("FFF");
        //brand.setFirstChar("");
        brandMapper.insert(brand);
        System.out.println("==============");


        TbBrand brand1 = new TbBrand();
        brand1.setName("FFFF");
        //brand1.setFirstChar("");
        brandMapper.insertSelective(brand1);
    }


    /**
     * 删除
     */
    @Test
    public void testDelete(){
        // 根据主键删除
        brandMapper.deleteByPrimaryKey(47L);

        System.out.println("==============");


        // 根据条件来删除  delete from tb_brand where id in (46)
        Example example = new Example(TbBrand.class);
        Example.Criteria criteria = example.createCriteria();
        ArrayList<Object> ids = new ArrayList<>();

        ids.add(46L);
        criteria.andIn("id",ids);
        brandMapper.deleteByExample(example);

        System.out.println("==================");

        // 根据条件来删除 等号条件
        //delete from tb_brand where id=37
        TbBrand record = new TbBrand();
        record.setId(37L);
        brandMapper.delete(record);
    }


    /**
     * 更新
     */
    @Test
    public void testUpdate(){
        //update  tb_brand set id=? ,name=? ,firstchar=? where id=36
        //如果 firstchar 没有传递updateByPrimaryKey() 方法将会更新成null 到数据库中   updateByPrimaryKeySelective() 则不会更新null到数据库中
        TbBrand tbBrand = new TbBrand();
        tbBrand.setId(36L);
        tbBrand.setName("NNA");
        brandMapper.updateByPrimaryKey(tbBrand);

        brandMapper.updateByPrimaryKeySelective(tbBrand);
    }


    /**
     * 查询
     */
    @Test
    public void testSelect(){
        //根据主键查询
        brandMapper.selectByPrimaryKey(30L);

        //根据条件查询 如果参数example 空 则查询所有

        //select * from tb_brand where id in (46)
        Example example = new Example(TbBrand.class);
        Example.Criteria criteria = example.createCriteria();
        List<Long> ids = new ArrayList<>();
        ids.add(46L);
        criteria.andIn("id",ids);
        brandMapper.selectByExample(example);

        //根据等号条件查询
        //select * from tb_brand where name ='呵呵' and first_char='H'
        TbBrand tbbrand = new TbBrand();
        tbbrand.setName("呵呵");
        tbbrand.setFirstChar("H");
        brandMapper.select(tbbrand);
    }


    /**
     * 分页
     */
    @Test
    public void testPage(){
        //page 当前页    size 每页显示多少条
        int page = 1,size = 10;
        //分页处理,只需要调用PageHelper.startPage静态方法即可。
        PageHelper.startPage(page,size);

        // 查询
        List<TbBrand> brands = brandMapper.selectAll();

        //获取分页信息,注意这里传入了brands集合对象
        PageInfo<TbBrand> pageInfo = new PageInfo<>(brands);
        System.out.println(pageInfo);
    }
}
