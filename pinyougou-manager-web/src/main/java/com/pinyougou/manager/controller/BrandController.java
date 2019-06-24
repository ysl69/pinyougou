package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import entity.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author ysl
 * @Date 2019/6/24 10:29
 * @Description: TODO
 **/

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;


    /**
     * 查询所有的品牌列表
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbBrand> findAll(){
        return brandService.findAll();
    }


    /**
     * 品牌列表分页
     * @return
     */
    @RequestMapping("/findPage")
    public PageInfo<TbBrand> findPage(@RequestParam(value = "pageNo",defaultValue = "1",required = true)Integer pageNo,
                                      @RequestParam(value = "pageSize",defaultValue = "10",required = true)Integer pageSize){
        return brandService.findPage(pageNo,pageSize);
    }


    /**
     * 实现品牌条件查询功能，输入品牌名称、首字母后查询，并分页
     * @param pageNo
     * @param pageSize
     * @param brand
     * @return
     */
    @RequestMapping(value = "/search")
    public PageInfo<TbBrand> findPage(@RequestParam(value = "pageNo",defaultValue = "1",required = true)Integer pageNo,
                                      @RequestParam(value = "pageSize",defaultValue = "10",required = true)Integer pageSize,
                                      @RequestBody TbBrand brand){
        return brandService.findPage(pageNo,pageSize,brand);
    }



    /**
     * 添加品牌
     * @param brand
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody TbBrand brand){
        try {
            brandService.add(brand);
            return new Result(true,"增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"增加失败");
        }
    }


    /**
     * 更新品牌
     * @param brand
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody TbBrand brand){
        try {
            brandService.update(brand);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }


    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    @RequestMapping("/findOne/{id}")
    public TbBrand findOne(@PathVariable(value = "id")Long id){
        return brandService.findOne(id);
    }


    /**
     * 删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(@RequestBody Long[] ids){
        try {
            brandService.delete(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }
}
