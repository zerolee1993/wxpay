package com.zero.wxpay;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信扫码支付接口请求工具类
 *
 * @author Zero Lee
 * @date 2017-12-18
 * @time 10:45
 */
public class WXPaySender {

    /**
     * 微信支付API
     */
    private WXPay wxpay;

    /**
     * 微信支付API 配置类
     */
    private WXPayConfigImpl config;

    public WXPaySender() throws Exception {
        config = WXPayConfigImpl.getInstance();
        wxpay = new WXPay(config);
    }

    /**
     * 扫码支付：统一下单
     * @param product 产品名称 （命名规范：浏览器打开的网站主页title名 -商品概述 ）
     * @param trade_no 用户订单号（商户即接入方自己系统的订单号，要求：同一商户号下唯一，32个字符内，只能是数字、大小写字母_-|*@ ）
     * @param amount 订单总金额，单位：分
     * @param ip 调用终端的IP地址
     * @return 说明参考官方文档：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_1
     */
    public Map<String, String> doUnifiedOrder(String product, String trade_no, String amount, String ip) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("body", product);
        data.put("out_trade_no", trade_no);
        data.put("total_fee", amount);
        data.put("spbill_create_ip", ip);
        data.put("notify_url", Const.DO_UNIFIEDORDER_NOTIFY_URL);
        data.put("trade_type", Const.TRADE_TYPE);
        Map<String, String> result = null;
        try {
            result = wxpay.unifiedOrder(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 扫码支付：查询订单
     * @param trade_no 用户订单号（统一下单时所用的订单号）
     * @return 说明参考官方文档：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_2
     */
    public Map<String, String> doOrderQuery(String trade_no) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", trade_no);
        Map<String, String> result = null;
        try {
            result = wxpay.orderQuery(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 扫码支付：关闭订单
     * @param trade_no 用户订单号（统一下单时所用的订单号）
     * @return 说明参考官方文档：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_3
     */
    public Map<String, String> doOrderClose(String trade_no) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", trade_no);
        Map<String, String> result = null;
        try {
            result = wxpay.closeOrder(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 扫码支付：申请退款
     * @param trade_no 用户订单号（统一下单时所用的订单号）
     * @param refund_no 用户退款订单号（商户即接入方自己系统的退款订单号）
     * @param trade_amount 订单金额
     *  @param refund_amount 退款金额
     * @return 说明参考官方文档：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_4
     */
    public Map<String, String> doRefund(String trade_no, String refund_no, String trade_amount, String refund_amount) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", trade_no);
        data.put("out_refund_no", refund_no);
        data.put("total_fee", trade_amount);
        data.put("refund_fee", refund_amount);
        Map<String, String> result = null;
        try {
            result = wxpay.refund(data);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 扫码支付：查询退款
     * @param trade_no 用户订单号（统一下单时所用的订单号）
     * @return 说明参考官方文档：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_5
     */
    public Map<String, String> doRefundQuery(String trade_no) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", trade_no);
        Map<String, String> result = null;
        try {
            result = wxpay.refundQuery(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
