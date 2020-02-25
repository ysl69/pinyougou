package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.util.IdWorker;
import com.pinyougou.order.service.OrderService;
import com.pinyougou.pay.service.WeixinPayService;
import com.pinyougou.pojo.TbPayLog;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private OrderService orderService;

    /**
     * 生成二维码
     * @return
     */
    @RequestMapping("/createNative")
    public Map<String,String> createNative() {
        //1.生成一个[支付]订单
        //IdWorker idWorker = new IdWorker(0, 1);

        //2.获取商品订单的总金额(先写死)
        //String total_fee = "1";//单位是分*/

        //3.调用服务(内部实现调用统一下单API)
        //return weixinPayService.createNative(idWorker.nextId()+"",total_fee);


        //1.获取用户的ID
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        //2.调用orderservice的服务 从redis获取支付日志对象
        TbPayLog payLog = orderService.searchPayLogFromRedis(userId);
        //3.调用服务(内部实现调用统一下单API)
        if (payLog != null) {
            return weixinPayService.createNative(payLog.getOutTradeNo(), payLog.getTotalFee() + "");
        }else {
            return null;
        }
}


    /**
     * 查询支付状态
     * @param out_trade_no
     * @return
     */
    @RequestMapping("/queryPayStatus")
    public Result queryPayStatus(String out_trade_no){
        int count = 0;
        Result result = null;
        while (true){
            //直接轮询调用 pay-service的接口方法 查询该out_trade_no对应的支付状态 返回数据
            Map<String,String> resultMap = weixinPayService.queryPayStatus(out_trade_no);

            if (resultMap == null){
                result  = new Result(false,"支付失败");
                break;
            }

            if ("SUCCESS".equals(resultMap.get("trade_state"))){
                result = new Result(true,"支付成功");
                orderService.updateOrderStatus(out_trade_no,resultMap.get("transaction_id"));
                break;
            }


            count++;
            //如果进过了5分钟还没支付就是表示超时
            if (count >= 100 ){
                result = new Result(false,"支付超时");
                break;
            }


            try {
                Thread.sleep(3000);// 间隔3秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;

    }
}
