package com.geekli.model;

import java.util.Date;

public class AliPaymentOrder {
    private Long id;

    private String outtradeno;

    private Byte tradestatus;

    private Double totalamount;

    private Double receiptamount;

    private Double invoiceamount;

    private Double buyerpayamount;

    private Double refundfee;

    private Date notifytime;

    private Date gmtcreate;

    private Date gmtpayment;

    private Date gmtrefund;

    private Date gmtclose;

    private String tradeno;

    private String outbizno;

    private String buyerlogonid;

    private String sellerid;

    private String selleremail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOuttradeno() {
        return outtradeno;
    }

    public void setOuttradeno(String outtradeno) {
        this.outtradeno = outtradeno == null ? null : outtradeno.trim();
    }

    public Byte getTradestatus() {
        return tradestatus;
    }

    public void setTradestatus(Byte tradestatus) {
        this.tradestatus = tradestatus;
    }

    public Double getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(Double totalamount) {
        this.totalamount = totalamount;
    }

    public Double getReceiptamount() {
        return receiptamount;
    }

    public void setReceiptamount(Double receiptamount) {
        this.receiptamount = receiptamount;
    }

    public Double getInvoiceamount() {
        return invoiceamount;
    }

    public void setInvoiceamount(Double invoiceamount) {
        this.invoiceamount = invoiceamount;
    }

    public Double getBuyerpayamount() {
        return buyerpayamount;
    }

    public void setBuyerpayamount(Double buyerpayamount) {
        this.buyerpayamount = buyerpayamount;
    }

    public Double getRefundfee() {
        return refundfee;
    }

    public void setRefundfee(Double refundfee) {
        this.refundfee = refundfee;
    }

    public Date getNotifytime() {
        return notifytime;
    }

    public void setNotifytime(Date notifytime) {
        this.notifytime = notifytime;
    }

    public Date getGmtcreate() {
        return gmtcreate;
    }

    public void setGmtcreate(Date gmtcreate) {
        this.gmtcreate = gmtcreate;
    }

    public Date getGmtpayment() {
        return gmtpayment;
    }

    public void setGmtpayment(Date gmtpayment) {
        this.gmtpayment = gmtpayment;
    }

    public Date getGmtrefund() {
        return gmtrefund;
    }

    public void setGmtrefund(Date gmtrefund) {
        this.gmtrefund = gmtrefund;
    }

    public Date getGmtclose() {
        return gmtclose;
    }

    public void setGmtclose(Date gmtclose) {
        this.gmtclose = gmtclose;
    }

    public String getTradeno() {
        return tradeno;
    }

    public void setTradeno(String tradeno) {
        this.tradeno = tradeno == null ? null : tradeno.trim();
    }

    public String getOutbizno() {
        return outbizno;
    }

    public void setOutbizno(String outbizno) {
        this.outbizno = outbizno == null ? null : outbizno.trim();
    }

    public String getBuyerlogonid() {
        return buyerlogonid;
    }

    public void setBuyerlogonid(String buyerlogonid) {
        this.buyerlogonid = buyerlogonid == null ? null : buyerlogonid.trim();
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid == null ? null : sellerid.trim();
    }

    public String getSelleremail() {
        return selleremail;
    }

    public void setSelleremail(String selleremail) {
        this.selleremail = selleremail == null ? null : selleremail.trim();
    }
}