package com.pinyougou.pay.service;

import java.util.Map;

/**
 * @Author ysl
 * @Date 2019/7/15 9:33
 * @Description:
 **/


public interface WeixinPayService {


    /**
     * 生成微信支付二维码
     * @param out_trade_no  订单号
     * @param total_free  金额(分)
     * @return
     */
    public Map createNative(String out_trade_no,String total_free);


    /**
     * 查询支付状态
     * @param out_trade_no
     * @return
     */
    public Map queryPayStatus(String out_trade_no);


    /**
     * 关闭订单
     * @param out_trade_no
     * @return
     */
    public Map closePay(String out_trade_no);
}
