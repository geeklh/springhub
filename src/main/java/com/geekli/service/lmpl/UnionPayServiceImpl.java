package com.geekli.service.lmpl;

import com.geekli.dao.UserBehavior;
import com.geekli.model.PayWay;
import com.geekli.model.Product;
import com.geekli.service.UnionPayService;
import com.geekli.utils.CommonUtil;
import com.geekli.utils.union.AcpService;
import com.geekli.utils.union.SDKConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.geekli.utils.union.UnionConfig;
import com.geekli.model.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Service(value = "unionpayService")
public class UnionPayServiceImpl implements UnionPayService {
    private static final Logger loggger = LoggerFactory.getLogger(UnionPayServiceImpl.class);

    private String notify_url; // 异步回调地址

    @Autowired
    private UserBehavior userBehavior;

    @Override
    public String unionPay(Product product) {
        Map<String, String> requestData = new HashMap<String, String>();
        /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
        requestData.put("version", UnionConfig.version);              //版本号，全渠道默认值
        requestData.put("encoding", UnionConfig.encoding_UTF8);      //字符集编码，可以使用UTF-8,GBK两种方式
        requestData.put("signMethod", "01");                          //签名方法，只支持 01：RSA方式证书加密
        requestData.put("txnType", "01");                          //交易类型 ，01：消费
        requestData.put("txnSubType", "01");                          //交易子类型， 01：自助消费
        requestData.put("bizType", "000201");                      //业务类型，B2C网关支付，手机wap支付
        requestData.put("channelType", "08");                       //07：PC支付；08，手机支付
        String frontUrl = "";//前端回调地址
        /***商户接入参数 测试账号***/
        requestData.put("merId", UnionConfig.merId);                  //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
        requestData.put("accessType", "0");                          //接入类型，0：直连商户
        requestData.put("orderId", product.getOutTradeNo());          //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
        requestData.put("txnTime", UnionConfig.getCurrentTime());     //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
        requestData.put("currencyCode", "156");                      //交易币种（境内商户一般是156 人民币）
        requestData.put("txnAmt", CommonUtil.subZeroAndDot(product.getTotalFee()));             //交易金额，单位分，不要带小数点
        //这里组织穿透数据 业务以及交易类型(使用json数据报错)
        requestData.put("reqReserved", "自定义参数");          //请求方保留域，如需使用请启用即可；透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节
        requestData.put("backUrl", notify_url);
        Map<String, String> submitFromDate = AcpService.sign(requestData, UnionConfig.encoding_UTF8);
        String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();
        String form = AcpService.createAutoFormHtml(requestFrontUrl, submitFromDate, UnionConfig.encoding_UTF8);
        loggger.info("打印请求HTML，此为请求报文，为联调排查问题的依据：{}", form);
        userBehavior.insertunion(product);//存数据
        return form;
    }

    @Override
    public String validate(Map<String, String> valideData, String encoding) {
        String message = Constants.SUCCESS;
        if (!AcpService.validate(valideData, encoding)) {
            message = Constants.FAIL;
        }
        return message;
    }

    @Override
    public void fileTrandfer() {
        Map<String, String> data = new HashMap<String, String>();
        /*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/

        // 版本号 全渠道默认值
        data.put("version", UnionConfig.version);

        // 字符集编码 可以使用UTF-8,GBK两种方式
        data.put("encoding", UnionConfig.encoding_UTF8);

        // 签名方法
        data.put("signMethod", "01");

        // 交易类型 76-对账文件下载
        data.put("txnType", "76");

        // 交易子类型 01-对账文件下载
        data.put("txnSubType", "01");

        // 业务类型，固定
        data.put("bizType", "000000");

        /*** 商户接入参数 ***/

        // 接入类型，商户接入填0，不需修改

        data.put("accessType", "0");

        // 商户代码，请替换正式商户号测试，如使用的是自助化平台注册的777开头的商户号，该商户号没有权限测文件下载接口的，

        // 请使用测试参数里写的文件下载的商户号和日期测。如需777商户号的真实交易的对账文件，请使用自助化平台下载文件。

        data.put("merId", UnionConfig.merId);

        // 清算日期，如果使用正式商户号测试则要修改成自己想要获取对账文件的日期，
        // 测试环境如果使用700000000000001商户号则固定填写0119

        //data.put("settleDate", settleDate);

        // 订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效

        data.put("txnTime", UnionConfig.getCurrentTime());

        // 文件类型，一般商户填写00即可

        data.put("fileType", "00");

        /** 请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文-------------> **/

        Map<String, String> reqData = AcpService.sign(data,
                UnionConfig.encoding_UTF8);

        // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。

        String url = SDKConfig.getConfig().getFileTransUrl();

        // 获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.fileTransUrl

        Map<String, String> rspData = AcpService.post(reqData, url,
                UnionConfig.encoding_UTF8);
        if (!rspData.isEmpty()) {
            if (AcpService.validate(rspData, UnionConfig.encoding_UTF8)) {
                loggger.info("验证签名成功");
                String respCode = rspData.get("respCode");
                if ("00".equals(respCode)) {
                } else {
                }//排错操作
            } else {
                loggger.info("验证签名失败");
            }
        } else {
            loggger.info("未获取到返回报文或返回http状态码非200");
        }
    }
}
