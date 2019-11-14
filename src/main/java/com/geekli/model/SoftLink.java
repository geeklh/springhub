package com.geekli.model;

import java.math.BigDecimal;

public class SoftLink {
    private BigDecimal editionnum;

    private String softdis;

    private String link;

    private String softname;

    public BigDecimal getEditionnum() {
        return editionnum;
    }

    public void setEditionnum(BigDecimal editionnum) {
        this.editionnum = editionnum;
    }

    public String getSoftdis() {
        return softdis;
    }

    public void setSoftdis(String softdis) {
        this.softdis = softdis == null ? null : softdis.trim();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    public String getSoftname() {
        return softname;
    }

    public void setSoftname(String softname) {
        this.softname = softname == null ? null : softname.trim();
    }
}