package com.geekli.service;

import com.geekli.model.WxPayModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface WxPayService {
    Map<Object, Object> getWxPay(WxPayModel wxPayModel, String ip);

    void weChatNotify(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
