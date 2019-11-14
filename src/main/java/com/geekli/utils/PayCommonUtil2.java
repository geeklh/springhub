package com.geekli.utils;

import com.alibaba.fastjson.JSONObject;
import com.geekli.model.WxPayProperties;
import com.geekli.model.WxPublicProperties;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class PayCommonUtil2 {
    public static final String TIME = "yyyyMMddHHmmss";
    public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    //创建微信交易对象
    public static SortedMap<Object, Object> getWXPrePayID() {
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("appid", WxPayProperties.appid);
        parameters.put("mch_id", WxPayProperties.mchid);
        parameters.put("nonce_str", PayCommonUtil2.CreateNoncestr());
        parameters.put("fee_type", "CNY");
        parameters.put("trade_type", "APP");
        return parameters;
    }

    //公众号交易对象
    public static SortedMap<Object, Object> getWXH5PrePayID() {
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("appid", WxPublicProperties.appid);
        parameters.put("mch_id", WxPublicProperties.mchid);
        parameters.put("nonce_str", PayCommonUtil2.CreateNoncestr());
        parameters.put("fee_type", "CNY");
        parameters.put("trade_type", "JSAPI");
        return parameters;
    }

    //再次签名，支付
    public static SortedMap<Object, Object> startWXPay(String result) {
        try {
            Map<String, String> map = XMLUtil.doXMLParse(result);
            SortedMap<Object, Object> parameterMap = new TreeMap<Object, Object>();
            parameterMap.put("appid", WxPayProperties.appid);
            parameterMap.put("partnerid", WxPayProperties.mchid);
            parameterMap.put("prepayid", map.get("prepay_id"));
            parameterMap.put("package", "Sign=WXPay");
            parameterMap.put("noncestr", PayCommonUtil2.CreateNoncestr());
            parameterMap.put("timestamp", Long.parseLong(String.valueOf(String.valueOf(System.currentTimeMillis()).toString().substring(0, 10))));
            String sign = PayCommonUtil2.createSign("UTF-8", parameterMap, WxPayProperties.key);
            return parameterMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //H5再次签名
    public static SortedMap<Object, Object> startWXH5Pay(String result) {
        try {
            Map<String, String> map = XMLUtil.doXMLParse(result);
            SortedMap<Object, Object> parameterMap = new TreeMap<Object, Object>();
            parameterMap.put("appId", WxPublicProperties.appid);// 这里注意一下,appId是大写的I
            parameterMap.put("nonceStr", PayCommonUtil2.CreateNoncestr());
            parameterMap.put("package", "prepay_id=" + map.get("prepay_id"));
            parameterMap.put("signType", "MD5");
            // 本来生成的时间戳是13位，但是ios必须是10位，所以截取了一下
            parameterMap.put("timeStamp", String.valueOf(System.currentTimeMillis()).toString().substring(0, 10));
            // H5的签名的密钥不同
            String sign = PayCommonUtil2.createSign("UTF-8", parameterMap, WxPublicProperties.mchid);
            parameterMap.put("paySign", sign);
            return parameterMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //创建随机数
    public static String CreateNoncestr() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < 16; i++) {
            Random random = new Random();
            res += chars.charAt(random.nextInt(chars.length() - 1));
        }
        return res;
    }

    //验证签名是否正确
    public static boolean isTenpaySign(String characterEncoding, SortedMap<Object, Object> packageParams, String key) {
        StringBuffer stringBuffer = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator iterator = es.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"sign".equals(k) && null != v && !"".equals(v)) {
                stringBuffer.append(k + "=" + v + "&");
            }
        }
        stringBuffer.append("key=" + key);
        String mysign = MD5Util.MD5Encode(stringBuffer.toString(), characterEncoding).toLowerCase();
        String tenpaySign = ((String) packageParams.get("sign")).toLowerCase();
        return tenpaySign.equals(mysign);
    }

    //创建签名
    public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters, String key) {
        StringBuffer stringBuffer = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                stringBuffer.append(k + "=" + v + "&");
            }
        }
        stringBuffer.append("key" + key);
        String sign = MD5Util.MD5Encode(stringBuffer.toString(), characterEncoding).toUpperCase();
        return sign;
    }

    //将请求参数转化成xml格式的string
    public static String getRequestXml(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    //返回到微信的参数
    public static String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";
    }

    //返回微信服务器响应信息
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        try {
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
            }
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while (null != (str = bufferedReader.readLine())) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
//            链接超时
        } catch (Exception e) {
//            请求异常
        }
        return null;
    }

    //通过JSONObject.get(key)的方式获取json对象的属性值
    public static JSONObject httpsRequest(String requestUrl, String requestMethod) {
        JSONObject jsonObject = null;
        try {
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setConnectTimeout(3000);
            conn.setRequestMethod(requestMethod);
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while (null != (str = bufferedReader.readLine())) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
//            链接超时
        } catch (Exception e) {
//            请求异常
        }
        return jsonObject;
    }

    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    //接收微信的异步通知
    public static String reciverWx(HttpServletRequest request) throws IOException {
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
        return sb.toString();
    }

    //产生num位的随机数
    public static String getRandByNum(int num) {
        String length = "1";
        for (int i = 0; i < num; i++) {
            length += "0";
        }
        Random rad = new Random();
        String result = rad.nextInt(Integer.parseInt(length)) + "";
        if (result.length() != num) {
            return getRandByNum(num);
        }
        return result;
    }

    //返回当前时间字符串
    public static String getDatedStr() {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME);
        return sdf.format(new Date());
    }

    //将日志保存至指定位置
    public static void saveLog(String path, String str) {
        File file = new File(path);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            fos.write(str.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveE(String path, Exception exception) {
        try {
            int i = 1 / 0;
        } catch (final Exception e) {
            try {
                new PrintWriter(new BufferedWriter(new FileWriter(path, true)), true).println(new Object() {
                    public String toString() {
                        StringWriter stringWriter = new StringWriter();
                        PrintWriter writer = new PrintWriter(stringWriter);
                        e.printStackTrace(writer);
                        StringBuffer buffer = stringWriter.getBuffer();
                        return buffer.toString();
                    }
                });
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
