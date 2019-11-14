package com.geekli.controller;

import com.geekli.model.OrderList;
import com.geekli.service.AliPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@Api(value = "支付宝接口", tags = "对接支付宝")
@CrossOrigin(maxAge = 3600)
@RequestMapping(value = "/ali")
public class AlipayController {
    @Autowired
    private AliPayService aliPayService;

    @RequestMapping(value = "/pay.do", method = RequestMethod.POST)
    @ApiOperation(value = "支付接口")
    @ResponseBody
    public String getAliPayOrderStr(@RequestBody OrderList orderList) {
        return aliPayService.getAliPayOrderStr(orderList);
    }

    @RequestMapping(value = "/notify.do", method = RequestMethod.POST)
    @ApiOperation(value = "异步回调")
    @ResponseBody
    public String notify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> aliParams = request.getParameterMap();
        Map<String, String> conversionParams = new HashMap<String, String>();
        for (Iterator<String> iter = aliParams.keySet().iterator(); ((Iterator) iter).hasNext(); ) {
            String key = iter.next();
            String[] values = aliParams.get(key);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            conversionParams.put(key, valueStr);
        }
        System.out.println("返回参数集合" + conversionParams);
        String status = aliPayService.notify(conversionParams);
        return status;
    }

    @RequestMapping(value = "/checkpay.do", method = RequestMethod.POST)
    @ApiOperation(value = "查单")
    @ResponseBody
    public Byte testpay(@RequestBody String outTradeNo) {
        return aliPayService.checkAlipay(outTradeNo);
    }
}
