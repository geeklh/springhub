package com.geekli.model;

public class Backmessage {
    private Long id;

    private String username;

    private String usermessage;

    private String backmessage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUsermessage() {
        return usermessage;
    }

    public void setUsermessage(String usermessage) {
        this.usermessage = usermessage == null ? null : usermessage.trim();
    }

    public String getBackmessage() {
        return backmessage;
    }

    public void setBackmessage(String backmessage) {
        this.backmessage = backmessage == null ? null : backmessage.trim();
    }
}