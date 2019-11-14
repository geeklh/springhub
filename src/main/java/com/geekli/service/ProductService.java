package com.geekli.service;

import com.geekli.model.MyResult;
import com.geekli.model.Userpro;

import java.util.List;

public interface ProductService {
    MyResult addpro(Userpro userpro);

    MyResult deletepro(String sname, String id);

    MyResult deleteProvider(List<Userpro> list);

    MyResult updatepro(Userpro userpro);

    MyResult findByName(String sname, String id);

    MyResult findAll();
}
