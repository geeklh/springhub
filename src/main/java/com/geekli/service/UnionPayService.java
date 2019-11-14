package com.geekli.service;

import com.geekli.model.Product;

import java.util.Map;

public interface UnionPayService {
    String unionPay(Product product);//支付

    String validate(Map<String, String> valideDate, String encoding);//异步回调

    void fileTrandfer();//下载单，校验
}
