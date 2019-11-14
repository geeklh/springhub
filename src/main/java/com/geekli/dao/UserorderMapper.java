package com.geekli.dao;

import com.geekli.model.Userorder;

public interface UserorderMapper {
    int insert(Userorder record);

    int insertSelective(Userorder record);
}