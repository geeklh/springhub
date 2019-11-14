package com.geekli.dao;

import com.geekli.model.Userbackform;

public interface UserbackformMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Userbackform record);

    int insertSelective(Userbackform record);

    Userbackform selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Userbackform record);

    int updateByPrimaryKey(Userbackform record);
}