package com.fisco.app.dto;

public class AwardCertDTO {
    private String userId;
    private String certId;
    private String content;

    public AwardCertDTO() {
    }

    public AwardCertDTO(String userId, String certId, String content) {
        this.userId = userId;
        this.certId = certId;
        this.content = content;
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
}
