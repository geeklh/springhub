package com.geekli.service.lmpl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.geekli.dao.UserBehavior;
import com.geekli.model.AliPaymentOrder;
import com.geekli.model.AlipayConfig;
import com.geekli.model.OrderList;
import com.geekli.service.AliPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service(value = "alipayService")
public class AliPayServicelmpl implements AliPayService {
    @Autowired
    private UserBehavior userBehavior;

    private Date dateFormat(String time) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String getAliPayOrderStr(OrderList orderList) {
        String orderString = "";
        System.out.println("订单号" + orderList.getOuttradeno());
        AliPaymentOrder aliPaymentOrder = new AliPaymentOrder();
        aliPaymentOrder.setId(orderList.getId());
        aliPaymentOrder.setOuttradeno(orderList.getOuttradeno());
        aliPaymentOrder.setTradestatus((byte) 0);
        aliPaymentOrder.setTotalamount(Double.parseDouble(orderList.getTotalamount()));
        aliPaymentOrder.setReceiptamount(0.00);
        aliPaymentOrder.setInvoiceamount(0.00);
        aliPaymentOrder.setBuyerpayamount(0.00);
        aliPaymentOrder.setRefundfee(0.00);
        userBehavior.insertalipayorder(orderList);//插入订单
        userBehavior.insertalipayment(orderList.getOuttradeno());//支付单插入订单号，号码是一样的
        try {
//            实例化客户端
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID,
                    AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                    AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setBody(orderList.getBody());
            model.setSubject(orderList.getSubjecy());
            model.setOutTradeNo(orderList.getOuttradeno());
            model.setTotalAmount(orderList.getTotalamount());
            model.setProductCode("QUICK_MSECURITY_PAY");
            ali_request.setNotifyUrl(AlipayConfig.notify_url);
            ali_request.setReturnUrl(AlipayConfig.return_url);
            AlipayTradeAppPayResponse alipayTradeAppPayResponse = alipayClient.sdkExecute(ali_request);
            orderString = alipayTradeAppPayResponse.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return orderString;
    }

    //    异步请求处理
    public String notify(Map<String, String> conversionParams) {
        System.out.println("异步请求处理");
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCertCheckV1(conversionParams, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (signVerified) {
            String appId = conversionParams.get("app_id");//支付宝分配给开发者的应用Id
            String notifyTime = conversionParams.get("notify_time");//通知时间:yyyy-MM-dd HH:mm:ss
            String gmtCreate = conversionParams.get("gmt_create");//交易创建时间:yyyy-MM-dd HH:mm:ss
            String gmtPayment = conversionParams.get("gmt_payment");//交易付款时间
            String gmtRefund = conversionParams.get("gmt_refund");//交易退款时间
            String gmtClose = conversionParams.get("gmt_close");//交易结束时间
            String tradeNo = conversionParams.get("trade_no");//支付宝的交易号
            String outTradeNo = conversionParams.get("out_trade_no");//获取商户之前传给支付宝的订单号（商户系统的唯一订单号）
            String outBizNo = conversionParams.get("out_biz_no");//商户业务号(商户业务ID，主要是退款通知中返回退款申请的流水号)
            String buyerLogonId = conversionParams.get("buyer_logon_id");//买家支付宝账号
            String sellerId = conversionParams.get("seller_id");//卖家支付宝用户号
            String sellerEmail = conversionParams.get("seller_email");//卖家支付宝账号
            String totalAmount = conversionParams.get("total_amount");//订单金额:本次交易支付的订单金额，单位为人民币（元）
            String receiptAmount = conversionParams.get("receipt_amount");//实收金额:商家在交易中实际收到的款项，单位为元
            String invoiceAmount = conversionParams.get("invoice_amount");//开票金额:用户在交易中支付的可开发票的金额
            String buyerPayAmount = conversionParams.get("buyer_pay_amount");//付款金额:用户在交易中支付的金额
            String tradeStatus = conversionParams.get("trade_status");// 获取交易状态
            AliPaymentOrder aliPaymentOrder = userBehavior.selecttradeno(outTradeNo);//根据同步调用存储的单号拿出来做更新操作
            if (aliPaymentOrder != null &&
                    totalAmount.equals(aliPaymentOrder.getTotalamount().toString()) &&
                    AlipayConfig.APPID.equals(appId)) {
                aliPaymentOrder.setNotifytime(dateFormat(notifyTime));
                aliPaymentOrder.setGmtcreate(dateFormat(gmtCreate));
                aliPaymentOrder.setGmtpayment(dateFormat(gmtPayment));
                aliPaymentOrder.setGmtrefund(dateFormat(gmtRefund));
                aliPaymentOrder.setGmtclose(dateFormat(gmtClose));
                aliPaymentOrder.setTradeno(tradeNo);
                aliPaymentOrder.setOuttradeno(outBizNo);
                aliPaymentOrder.setBuyerlogonid(buyerLogonId);
                aliPaymentOrder.setSelleremail(sellerEmail);
                aliPaymentOrder.setTotalamount(Double.parseDouble(totalAmount));
                aliPaymentOrder.setReceiptamount(Double.parseDouble(receiptAmount));
                aliPaymentOrder.setInvoiceamount(Double.parseDouble(invoiceAmount));
                aliPaymentOrder.setBuyerpayamount(Double.parseDouble(buyerPayAmount));
                switch (tradeStatus) {
                    case "TRADE_FINISHED":
                        aliPaymentOrder.setTradestatus((byte) 3);
                        break;
                    case "TRADE_SUCCESS":
                        aliPaymentOrder.setTradestatus((byte) 2);
                        break;
                    case "TRADE_CLOSED":
                        aliPaymentOrder.setTradestatus((byte) 1);
                        break;
                    case "WAIT_BUYER_PAY":
                        aliPaymentOrder.setTradestatus((byte) 0);
                        break;
                    default:
                        break;
                }
                int returnResult = userBehavior.updateTrade(aliPaymentOrder);//更新表格
                if (tradeStatus.equals("TRADE_SUCCESS")) {
                    if (returnResult > 0) {
                        return "success";
                    } else {
                        return "fail";
                    }
                } else {
                    return "fail";
                }
            } else {
                return "fail";
            }
        } else {
            return "fail";
        }
    }


    //    校验最终的付款情况
    @Override
    public Byte checkAlipay(String outTradeNo) {
        System.out.print(outTradeNo);
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL,
                    AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT,
                    AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
            AlipayTradeQueryRequest alipayTradeQueryRequest = new AlipayTradeQueryRequest();
            alipayTradeQueryRequest.setBizContent("{" + "\"out_trade_no\":\"" + outTradeNo + "\"" + "}");
            AlipayTradeQueryResponse alipayTradeQueryResponse = alipayClient.execute(alipayTradeQueryRequest);
            if (alipayTradeQueryResponse.isSuccess()) {
                AliPaymentOrder aliPaymentOrder = userBehavior.selecttradeno(outTradeNo);
                aliPaymentOrder.setTradeno(alipayTradeQueryResponse.getTradeNo());
                aliPaymentOrder.setBuyerlogonid(alipayTradeQueryResponse.getBuyerLogonId());
                aliPaymentOrder.setTotalamount(Double.parseDouble(alipayTradeQueryResponse.getTotalAmount()));
                aliPaymentOrder.setReceiptamount(Double.parseDouble(alipayTradeQueryResponse.getReceiptAmount()));
                aliPaymentOrder.setInvoiceamount(Double.parseDouble(alipayTradeQueryResponse.getInvoiceAmount()));
                aliPaymentOrder.setBuyerpayamount(Double.parseDouble(alipayTradeQueryResponse.getBuyerPayAmount()));
                switch (alipayTradeQueryResponse.getTradeStatus()) {
                    case "TRADE_FINISHED":
                        aliPaymentOrder.setTradestatus((byte) 3);
                        break;
                    case "TRADE_SUCCESS":
                        aliPaymentOrder.setTradestatus((byte) 2);
                        break;
                    case "TRADE_CLOSED":
                        aliPaymentOrder.setTradestatus((byte) 1);
                        break;
                    case "WAIT_BUYER_PAY":
                        aliPaymentOrder.setTradestatus((byte) 0);
                        break;
                    default:
                        break;
                }
                userBehavior.updateTrade(aliPaymentOrder);//校验表格
                return aliPaymentOrder.getTradestatus();
            } else {
                System.out.println("调用查询接口失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
