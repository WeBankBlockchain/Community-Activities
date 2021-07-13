package com.fisco.app.pojo;


/**
 * 对应ApplyCertMapper中queryListForAdmin查询里的封装类，用于向管理员展示证书申请内容
 */
public class ApplyCertInfo {
    private String certName;
    private String agencyId;
    private String name;
    private int validity;
    private int result;

    public ApplyCertInfo(String certName, String agencyId, String name, int validity, int result) {
        this.certName = certName;
        this.agencyId = agencyId;
        this.name = name;
        this.validity = validity;
        this.result = result;
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ApplyCertInfo{" +
                "certName='" + certName + '\'' +
                ", agencyId='" + agencyId + '\'' +
                ", name='" + name + '\'' +
                ", validity=" + validity +
                ", result=" + result +
                '}';
    }
}
