package com.geekli.dao;

import com.geekli.model.SoftLink;

public interface SoftLinkMapper {
    int insert(SoftLink record);

    int insertSelective(SoftLink record);
}