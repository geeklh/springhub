package com.geekli.model;

import java.math.BigDecimal;

public class Userorder {
    private Long ordernum;

    private String orderuser;

    private BigDecimal price;

    private String orderform;

    private String payform;

    private Long userphone;

    private String productname;

    public Long getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Long ordernum) {
        this.ordernum = ordernum;
    }

    public String getOrderuser() {
        return orderuser;
    }

    public void setOrderuser(String orderuser) {
        this.orderuser = orderuser == null ? null : orderuser.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getOrderform() {
        return orderform;
    }

    public void setOrderform(String orderform) {
        this.orderform = orderform == null ? null : orderform.trim();
    }

    public String getPayform() {
        return payform;
    }

    public void setPayform(String payform) {
        this.payform = payform == null ? null : payform.trim();
    }

    public Long getUserphone() {
        return userphone;
    }

    public void setUserphone(Long userphone) {
        this.userphone = userphone;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname == null ? null : productname.trim();
    }
}