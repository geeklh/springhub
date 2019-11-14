package com.geekli.controller;

import com.alibaba.fastjson.JSON;
import com.geekli.model.MyResult;
import com.geekli.model.WxPayModel;
import com.geekli.service.WxPayService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@Api(value = "微信支付", tags = "微信支付接口")
@CrossOrigin(maxAge = 3600)
@RequestMapping(value = "/WxPay")
public class WxPayController {
    private Log log = LogFactory.getLog(WxPayController.class);
    @Autowired
    WxPayService wxPayService;

    @ApiImplicitParams({@ApiImplicitParam(name = "wxPayModel", value = "支付对象", required = true, dataType = "wxPayModel", paramType = "body")})
    @ApiOperation(value = "支付接口")
    @RequestMapping(value = "/GetWxPay.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<Object, Object> getWxPay(@RequestBody WxPayModel wxPayModel) {
        log.info(JSON.toJSONString(wxPayModel));
        Map<Object, Object> map = wxPayService.getWxPay(wxPayModel, "");
        return map;
    }

    @ApiOperation(value = "回调接口")
    @RequestMapping(value = "/wxnotify.do", method = RequestMethod.POST)
    @ResponseBody
    public MyResult weChatNotify(HttpServletRequest request, HttpServletResponse response) {
        MyResult result = new MyResult();
        result.setObj(null);
        try {
            wxPayService.weChatNotify(request, response);
            result.setMsg("success");
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setMsg(e.getMessage());
            return result;
        }
    }
}
