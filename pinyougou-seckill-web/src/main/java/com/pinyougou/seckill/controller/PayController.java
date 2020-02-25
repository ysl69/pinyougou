package com.pinyougou.seckill.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pay.service.WeixinPayService;
import com.pinyougou.pojo.TbSeckillOrder;
import com.pinyougou.seckill.service.SeckillOrderService;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ysl
 * @Date 2019/7/15 20:41
 * @Description:
 **/


@RestController
@RequestMapping("/pay")
public class PayController {


    @Reference
    private WeixinPayService weixinPayService;

    @Reference
    private SeckillOrderService seckillOrderService;


    /**
     * 生成二维码
     * @return
     */
    @RequestMapping("/createNative")
    public Map<String,String> createNative() {

        //1.获取用户的ID
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        //2.从redis中获取预订单 获取预订单的金额  和 支付订单号
        TbSeckillOrder seckillOrder = seckillOrderService.getUserOrderStatus(userId);

        if (seckillOrder != null) {
            double v = seckillOrder.getMoney().doubleValue()*100;
            long fen = (long) v;
            return weixinPayService.createNative(seckillOrder.getId()+"", fen + "");
        }else {
            return new HashMap<>();
        }
}


    /**
     * 查询支付状态
     * @param out_trade_no
     * @return
     */
    @RequestMapping("/queryPayStatus")
    public Result queryPayStatus(String out_trade_no){
        Result result = new Result(false,"支付失败");

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        try {
            int count = 0;
            //1.调用支付的服务 不停的查询 状态
            while (true){
                //直接轮询调用 pay-service的接口方法 查询该out_trade_no对应的支付状态 返回数据
                Map<String,String> resultMap = weixinPayService.queryPayStatus(out_trade_no);

                count++;

                //如果进过了5分钟还没支付就是表示超时
                if (count >= 10 ){  //100
                    result = new Result(false,"支付超时");

                    //关闭微信订单
                    Map map = weixinPayService.closePay(out_trade_no);
                    if ("ORDERPAID".equals(map.get("err_code"))){
                        //已经支付则更新入库
                        seckillOrderService.updateOrderStatus(resultMap.get("transaction_id"),userId);
                    }else if ("SUCCESS".equals(resultMap.get("result_code")) || "ORDERCLOSED".equals(resultMap.get("err_code"))) {
                        //删除预订单
                        seckillOrderService.deleteOrder(userId);
                    }else {
                        System.out.println("由于微信端错误");
                    }
                    break;
                }


                if ("SUCCESS".equals(resultMap.get("trade_state"))){
                    result = new Result(true,"支付成功");
                    seckillOrderService.updateOrderStatus(resultMap.get("transaction_id"),userId);
                    break;
                }


                try {
                    Thread.sleep(3000);// 间隔3秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
}
