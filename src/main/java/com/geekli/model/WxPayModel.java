package com.geekli.model;

public class WxPayModel {
    public String body;
    public String outTradeNo;
    public String deviceInfo;
    public String feeType;
    public String totalFee;
    public String spbillCreateIp;
    public String notifyUrl;
    public String productId;
    public Double refundFee;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Double refundFee) {
        this.refundFee = refundFee;
    }

    public String getProductId() {
        return productId;
    }
}
