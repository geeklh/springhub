package com.geekli.dao;

import com.geekli.model.WxOrderList;

public interface WxOrderListMapper {
    int deleteByPrimaryKey(String outTradeNo);

    int insert(WxOrderList record);

    int insertSelective(WxOrderList record);

    WxOrderList selectByPrimaryKey(String outTradeNo);

    int updateByPrimaryKeySelective(WxOrderList record);

    int updateByPrimaryKey(WxOrderList record);
}