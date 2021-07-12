package com.fisco.app.pojo;


import com.fisco.app.dto.ApplyCertDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 对应数据库certificate表
 * @author amer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {
    private String certId;
    private String certName;
    private String agencyId;
    private int validity;


    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
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

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
    }
}
