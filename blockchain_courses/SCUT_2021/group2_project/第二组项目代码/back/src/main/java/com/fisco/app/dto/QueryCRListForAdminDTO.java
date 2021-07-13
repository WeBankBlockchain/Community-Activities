package com.fisco.app.dto;


import com.fisco.app.pojo.ApplyCertInfo;
import com.fisco.app.utils.TransResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询证书申请列表（管理员）----管理员可查看证书申请列表
 * @author amer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryCRListForAdminDTO {
    private String certName;
    private String agencyId;
    private String name;
    private int validity;
    private String result;

    public QueryCRListForAdminDTO(ApplyCertInfo info, TransResult transResult){
        this.certName = info.getCertName();
        this.agencyId = info.getAgencyId();
        this.name = info.getName();
        this.validity = info.getValidity();
        this.result = transResult.transition(info.getResult());
    }
}
