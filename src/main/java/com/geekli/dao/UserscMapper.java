package com.geekli.dao;

import com.geekli.model.UserscKey;

public interface UserscMapper {
    int deleteByPrimaryKey(UserscKey key);

    int insert(UserscKey record);

    int insertSelective(UserscKey record);
}