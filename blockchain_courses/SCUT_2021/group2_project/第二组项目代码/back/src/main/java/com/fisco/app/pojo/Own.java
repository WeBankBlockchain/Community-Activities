package com.fisco.app.pojo;

import java.sql.Timestamp;

public class Own {
    private String userId;
    private String certId;
    private String content;
    private Timestamp timestamp;

    public Own() {
    }

    public Own(String userId, String certId, String content, Timestamp timestamp) {
        this.userId = userId;
        this.certId = certId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
