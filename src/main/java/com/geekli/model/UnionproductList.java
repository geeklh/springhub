package com.geekli.model;

public class UnionproductList {
    private String productid;

    private String subject;

    private String body;

    private String totalfee;

    private String outtradeno;

    private String spillcreateip;

    private String attach;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid == null ? null : productid.trim();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body == null ? null : body.trim();
    }

    public String getTotalfee() {
        return totalfee;
    }

    public void setTotalfee(String totalfee) {
        this.totalfee = totalfee == null ? null : totalfee.trim();
    }

    public String getOuttradeno() {
        return outtradeno;
    }

    public void setOuttradeno(String outtradeno) {
        this.outtradeno = outtradeno == null ? null : outtradeno.trim();
    }

    public String getSpillcreateip() {
        return spillcreateip;
    }

    public void setSpillcreateip(String spillcreateip) {
        this.spillcreateip = spillcreateip == null ? null : spillcreateip.trim();
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach == null ? null : attach.trim();
    }
}