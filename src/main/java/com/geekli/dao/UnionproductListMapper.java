package com.geekli.dao;

import com.geekli.model.UnionproductList;

public interface UnionproductListMapper {
    int deleteByPrimaryKey(String productid);

    int insert(UnionproductList record);

    int insertSelective(UnionproductList record);

    UnionproductList selectByPrimaryKey(String productid);

    int updateByPrimaryKeySelective(UnionproductList record);

    int updateByPrimaryKey(UnionproductList record);
}