package com.pinyougou.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.wxpay.sdk.WXPayUtil;
import com.pinyougou.common.util.HttpClient;
import com.pinyougou.pay.service.WeixinPayService;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ysl
 * @Date 2019/7/15 9:36
 * @Description:
 **/


@Service
public class WeixinPayServiceImpl implements WeixinPayService {

    @Value("${appid}")
    private String appid;


    @Value("${partner}")
    private String partner;

    @Value("${partnerkey}")
    private String partnerkey;


    /**
     * 生成二维码
     * @param out_trade_no  订单号
     * @param total_fee  金额(分)
     * @return
     */
    @Override
    public Map createNative(String out_trade_no, String total_fee) {

        //1.组合参数集 存储到map中 map转换成XML
        // 创建参数
        Map<String, String> paramMap = new HashMap<>();
        // 公众号
        paramMap.put("appid",appid);
        // 商户号
        paramMap.put("mch_id",partner);
        //随机字符串
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        //商品描述
        paramMap.put("body","品优购");
        // 商户订单号
        paramMap.put("out_trade_no",out_trade_no);
        // 标价金额
        paramMap.put("total_fee",total_fee);
        //终端ip
        paramMap.put("spbill_create_ip","127.0.0.1");
        //通知地址(回调地址 随便写)
        paramMap.put("notify_url","http://a31ef7db.ngrok.io/WeChatPay/WeChatPayNotify");
        // 交易类型
        paramMap.put("trade_type","NATIVE");


        try {
            //自动添加签名 而且转成字符串
            String signedXml = WXPayUtil.generateSignedXml(paramMap, partnerkey);
            //System.out.println(signedXml);

            //2.使用httpclient 调用 接口 发送请求
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpClient.setHttps(true);
            httpClient.setXmlParam(signedXml);//请求体
            httpClient.post();


            //3.获取结果 XML  转成MAP(code_url)
            String resultXml = httpClient.getContent();
            //System.out.println(resultXml);
            Map<String, String> map = WXPayUtil.xmlToMap(resultXml);

            HashMap<String, String> resultMap = new HashMap<>();
            //我二维码连接
            resultMap.put("code_url",map.get("code_url"));
            //商户订单号
            resultMap.put("out_trade_no",out_trade_no);
            //标价金额
            resultMap.put("total_fee",total_fee);

            //4.返回map
            return resultMap;

        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap();
        }
    }


    /**
     * 查询支付状态
     * @param out_trade_no
     * @return
     */
    @Override
    public Map queryPayStatus(String out_trade_no) {
        //1.组合参数集 存储到map中 map转换成XML
        Map<String, String> paramMap = new HashMap<>();
        //公众号id
        paramMap.put("appid",appid);
        //商户号
        paramMap.put("mch_id",partner);
        //商户订单号
        paramMap.put("out_trade_no",out_trade_no);
        //随机字符串
        paramMap.put("nonce_str",WXPayUtil.generateNonceStr());


        try {
            //自动添加签名 而且转成字符串
            String signedXml = WXPayUtil.generateSignedXml(paramMap, partnerkey);

            //2.使用httpclient 调用 接口 发送请求
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            httpClient.setHttps(true);
            httpClient.setXmlParam(signedXml);
            httpClient.post();

            //3.获取结果 XML  转成MAP(code_url)
            String resultXml = httpClient.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(resultXml);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 关闭订单
     * @param out_trade_no
     * @return
     **/

    @Override
    public Map closePay(String out_trade_no) {
        try {
            Map<String,String> paramMap = new HashMap<String,String>();
            paramMap.put("appid","wx8397f8696b538317"); //应用ID
            paramMap.put("mch_id","1473426802");    //商户编号
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符
            paramMap.put("out_trade_no",out_trade_no);   //商家的唯一编号

            //将Map数据转成XML字符
            String xmlParam = WXPayUtil.generateSignedXml(paramMap,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb");

            //确定url
            String url = "https://api.mch.weixin.qq.com/pay/closeorder";

            //发送请求
            HttpClient httpClient = new HttpClient(url);
            //https
            httpClient.setHttps(true);
            //提交参数
            httpClient.setXmlParam(xmlParam);

            //提交
            httpClient.post();

            //获取返回数据
            String content = httpClient.getContent();

            //将返回数据解析成Map
            return  WXPayUtil.xmlToMap(content);

        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap();
        }
    }
}
