package com.geekli.dao;

import com.geekli.model.AliPaymentOrder;

public interface AliPaymentOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AliPaymentOrder record);

    int insertSelective(AliPaymentOrder record);

    AliPaymentOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AliPaymentOrder record);

    int updateByPrimaryKey(AliPaymentOrder record);
}