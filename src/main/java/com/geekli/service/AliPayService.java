package com.geekli.service;

import com.geekli.model.OrderList;

import java.util.Map;

public interface AliPayService {
    String getAliPayOrderStr(OrderList orderList);

    String notify(Map<String, String> conversionParams);

    Byte checkAlipay(String outTradeNo);
}
