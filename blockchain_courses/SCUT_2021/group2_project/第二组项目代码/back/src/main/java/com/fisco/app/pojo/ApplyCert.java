package com.fisco.app.pojo;

import com.fisco.app.dto.ApplyCertDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 对应数据库apply_cert表
 * @author amer
 */

public class ApplyCert {
    private String certName;
    private String agencyId;
    private int validity;
    private int result;

    public ApplyCert(ApplyCertDTO item){
        this.certName = item.getCertName();
        this.agencyId = item.getAgencyId();
        this.validity = item.getValidity();
        this.result = 0;
    }

    public ApplyCert(String certName, String agencyId, int validity, int result) {
        this.certName = certName;
        this.agencyId = agencyId;
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
        return "ApplyCert{" +
                "certName='" + certName + '\'' +
                ", agencyId='" + agencyId + '\'' +
                ", validity=" + validity +
                ", result=" + result +
                '}';
    }
}
