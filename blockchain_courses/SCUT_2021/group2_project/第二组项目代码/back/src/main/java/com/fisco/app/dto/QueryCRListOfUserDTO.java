package com.fisco.app.dto;


import com.fisco.app.pojo.ApplyCert;
import com.fisco.app.pojo.ApplyLeftJoinCert;
import com.fisco.app.utils.TransResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询证书申请列表（用户）----凭证发放机构可查看证书申请列表
 * @author amer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryCRListOfUserDTO {
    private String certId;
    private String certName;
    private int validity;
    private String result;

    public QueryCRListOfUserDTO(ApplyLeftJoinCert cert, TransResult transResult){
        String id = cert.getCertId();
        this.certId = id == null?"-":id;
        this.certName = cert.getCertName();
        this.validity = cert.getValidity();
        this.result = transResult.transition(cert.getResult());
    }
}
