package com.geekli.dao;

import com.geekli.model.Backmessage;

public interface BackmessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Backmessage record);

    int insertSelective(Backmessage record);

    Backmessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Backmessage record);

    int updateByPrimaryKey(Backmessage record);
}