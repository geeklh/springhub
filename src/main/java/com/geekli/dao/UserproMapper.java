package com.geekli.dao;

import com.geekli.model.Userpro;

public interface UserproMapper {
    int deleteByPrimaryKey(String id);

    int insert(Userpro record);

    int insertSelective(Userpro record);

    Userpro selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Userpro record);

    int updateByPrimaryKey(Userpro record);
}