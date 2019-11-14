package com.geekli.model;

public class WxPublicProperties {
    public static String appid = "";
    public static String mchid = "";
    public static String key = "";
    //    Api证书绝对路径
    public static String certPath = "";
    //    HTTP链接时间
    public int httpConnectTimeoutMs = 8000;
    //    HTTP读数据时间
    public int httpReadTimeoutMs = 10000;
    //    微信支付异步通知地址
    public String payNotifyUrl = "";
    //    微信退款异步通知地址
    public String refundNotifyUrl = "";
    //    统一下单接口
    public static String UNIFIED_ORDER_URL = "";
}
