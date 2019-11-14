package com.geekli.service.lmpl;

import com.alibaba.fastjson.JSON;
import com.geekli.dao.UserBehavior;
import com.geekli.model.WxOrderList;
import com.geekli.model.WxPayModel;
import com.geekli.model.WxPayProperties;
import com.geekli.utils.PayCommonUtil2;
import com.geekli.utils.XMLUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.geekli.service.WxPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Service(value = "wxpayService")
public class WxPayServiceImpl implements WxPayService {
    @Autowired
    private UserBehavior userBehavior;

    private Log log = LogFactory.getLog(WxPayServiceImpl.class);

    @Override
    public Map<Object, Object> getWxPay(WxPayModel wxPayModel, String ip) {
        try {

            SortedMap<Object, Object> parameters = PayCommonUtil2.getWXPrePayID();
            parameters.put("body", wxPayModel.getBody());
            parameters.put("notify_url", wxPayModel.getNotifyUrl());
            parameters.put("spbill_create_ip", ip);
            parameters.put("out_trade_no", wxPayModel.getOutTradeNo());
            parameters.put("total_fee", wxPayModel.getTotalFee());
            String sign = PayCommonUtil2.createSign("UTF-8", parameters, WxPayProperties.appid);
            parameters.put("sign", sign);
            String resultXML = PayCommonUtil2.getRequestXml(parameters);
            //调用统一下单接口
            String result = PayCommonUtil2.httpsRequest(WxPayProperties.UNIFIED_ORDER_URL, "POST", resultXML);
            System.out.println("result=" + result);
            SortedMap<Object, Object> parMap = PayCommonUtil2.startWXPay(result);
            parMap.put("outtradeno", wxPayModel.getOutTradeNo());
            parMap.put("productId", wxPayModel.getProductId());
            userBehavior.insertwxorder(wxPayModel.getOutTradeNo(),
                    ip, wxPayModel.getNotifyUrl(),
                    wxPayModel.getTotalFee(), wxPayModel.getBody(),
                    sign, wxPayModel.getProductId());//订单表插入存储信息
            return parMap;
        } catch (Exception e) {
            e.printStackTrace();
            Map<Object, Object> errMap = new HashMap<>();
            errMap.put("errMsg", e.getMessage());
            return errMap;
        }
    }

    @Override
    public void weChatNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("微信回调——————————————————————" + request);
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while (null != (s = in.readLine())) {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        Map<String, String> m = new TreeMap<String, String>();
        m = XMLUtil.doXMLParse(sb.toString());
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);
            String v = "";
            if (null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        String key = WxPayProperties.key;
        if (PayCommonUtil2.isTenpaySign("UTF-8", packageParams, key)) {
            if ("SUCCESS".equals((String) packageParams.get("result_code"))) {
//                支付成功，做存储数据逻辑
                String mch_id = (String) packageParams.get("mch_id");//商户ID
                String openid = (String) packageParams.get("openid");//
                String is_subscribe = (String) packageParams.get("is_subscribe");//描述
                String out_trade_no = (String) packageParams.get("out_trade_no");//订单号
                String prepay_id = (String) packageParams.get("prepay_id");//预支付会话ID
                String total_fee = (String) packageParams.get("total_fee");//交易货币
                log.info(JSON.toJSONString(packageParams));
                userBehavior.insertwxpayment(out_trade_no, mch_id, openid, is_subscribe, prepay_id, total_fee);//数据存储
                SortedMap<Object, Object> prepayParams = new TreeMap<Object, Object>();
                prepayParams.put("prepay_id", prepay_id);
                prepayParams.put("return_code", "SUCCESS");
                prepayParams.put("result_code", "SUCCESS");
                String prepaySign = PayCommonUtil2.createSign("UTF-8", prepayParams, key);
                prepayParams.put("sign", prepaySign);
                String prepayXml = PayCommonUtil2.getRequestXml(prepayParams);
                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                out.write(prepayXml.getBytes());
                out.flush();
                out.close();
            }
        }
    }
}
