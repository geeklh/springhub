package com.geekli.dao;

import com.geekli.model.OrderList;

public interface OrderListMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderList record);

    int insertSelective(OrderList record);

    OrderList selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderList record);

    int updateByPrimaryKey(OrderList record);
}