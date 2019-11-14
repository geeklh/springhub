package com.geekli.model;

public class OrderList {
    private Long id;

    private String outtradeno;

    private String totalamount;

    private String body;

    private String subjecy;

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

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount == null ? null : totalamount.trim();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body == null ? null : body.trim();
    }

    public String getSubjecy() {
        return subjecy;
    }

    public void setSubjecy(String subjecy) {
        this.subjecy = subjecy == null ? null : subjecy.trim();
    }
}