package com.geekli.controller;

import com.geekli.model.PayWay;
import com.geekli.model.Product;
import com.geekli.service.UnionPayService;
import com.geekli.utils.union.AcpService;
import com.geekli.utils.union.SDKConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map.Entry;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@Api(value = "银联支付接口", tags = "对接银联")
@CrossOrigin(maxAge = 3600)
@RequestMapping(value = "/union")
public class UnionPayController {
    private static final Logger logger = LoggerFactory.getLogger(UnionPayController.class);
    @Autowired
    private UnionPayService unionPayService;

    @RequestMapping(value = "/index.do", method = RequestMethod.GET)
    @ApiOperation(value = "银联支付主页")
    @ResponseBody
    public String index() {
        return "unionpay/index";
    }

    @ApiOperation(value = "手机支付")
    @RequestMapping(value = "/mobiledPay.do", method = RequestMethod.POST)
    @ResponseBody
    public String pcPay(Product product) {
        logger.info("手机银联支付开始");
        String form = unionPayService.unionPay(product);
        return "unionpay/pay";
    }

    @RequestMapping(value = "/pay.do", method = RequestMethod.POST)
    @ApiOperation(value = "银联回调接口")
    @ResponseBody
    public void union_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("银联接收通知开始");
        String encoding = request.getParameter(SDKConstants.param_encoding);
        Map<String, String> reqParam = getAllRequestParam(request);
        logger.info(reqParam.toString());
        Map<String, String> valideData = null;
        if (null != reqParam && !reqParam.isEmpty()) {
            Iterator<Map.Entry<String, String>> it = reqParam.entrySet().iterator();
            valideData = new HashMap<String, String>(reqParam.size());
            while (it.hasNext()) {
                Entry<String, String> e = it.next();
                String key = (String) e.getKey();
                String value = (String) e.getValue();
                value = new String(value.getBytes(encoding), encoding);
                valideData.put(key, value);
            }
        }
//        验证签名之前不要修改reparam中键值对的内容
        if (!AcpService.validate(valideData, encoding)) {
            logger.info("银联验证签名结果[失败]");
        } else {
            logger.info("银联验证签名结果[成功]");
            String outtradeno = valideData.get("orderId");
            String reqReserved = valideData.get("reqReserved");
            response.getWriter().print("ok");
        }
    }

    //    获取请求参数中所有信息
    public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                if (null != res.get(en) || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }
}
