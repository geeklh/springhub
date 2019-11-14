package com.geekli.model;

import java.math.BigDecimal;

public class Userpro {
    private String id;

    private String sname;

    private String classification;

    private BigDecimal dayprice;

    private BigDecimal monthprice;

    private BigDecimal yearprice;

    private String istry;

    private BigDecimal vipdis;

    private Integer trytime;

    private String describe;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname == null ? null : sname.trim();
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification == null ? null : classification.trim();
    }

    public BigDecimal getDayprice() {
        return dayprice;
    }

    public void setDayprice(BigDecimal dayprice) {
        this.dayprice = dayprice;
    }

    public BigDecimal getMonthprice() {
        return monthprice;
    }

    public void setMonthprice(BigDecimal monthprice) {
        this.monthprice = monthprice;
    }

    public BigDecimal getYearprice() {
        return yearprice;
    }

    public void setYearprice(BigDecimal yearprice) {
        this.yearprice = yearprice;
    }

    public String getIstry() {
        return istry;
    }

    public void setIstry(String istry) {
        this.istry = istry == null ? null : istry.trim();
    }

    public BigDecimal getVipdis() {
        return vipdis;
    }

    public void setVipdis(BigDecimal vipdis) {
        this.vipdis = vipdis;
    }

    public Integer getTrytime() {
        return trytime;
    }

    public void setTrytime(Integer trytime) {
        this.trytime = trytime;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }
}