package com.geekli.dao;

import com.geekli.model.WxPaymentOrder;

public interface WxPaymentOrderMapper {
    int deleteByPrimaryKey(String outTradeNo);

    int insert(WxPaymentOrder record);

    int insertSelective(WxPaymentOrder record);

    WxPaymentOrder selectByPrimaryKey(String outTradeNo);

    int updateByPrimaryKeySelective(WxPaymentOrder record);

    int updateByPrimaryKey(WxPaymentOrder record);
}