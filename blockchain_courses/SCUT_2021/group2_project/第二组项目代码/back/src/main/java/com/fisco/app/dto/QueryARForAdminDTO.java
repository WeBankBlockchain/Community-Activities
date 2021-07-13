package com.fisco.app.dto;

import com.fisco.app.pojo.ApplyAgency;
import com.fisco.app.pojo.ApplyAgencyForAdmin;
import com.fisco.app.utils.TransApplyRole;
import com.fisco.app.utils.TransResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryARForAdminDTO {
    private int id;
    private String applyId;
    private String name;
    private String applyRole;
    private String USCC;
    private String result;

    public QueryARForAdminDTO(ApplyAgencyForAdmin applyAgency, TransResult transResult, TransApplyRole transApplyRole){
        this.id = applyAgency.getId();
        this.applyId = applyAgency.getApplyId();
        this.name = applyAgency.getName();
        this.applyRole = transApplyRole.transition(applyAgency.getApplyRole());
        this.USCC = applyAgency.getUSCC();
        this.result = transResult.transition(applyAgency.getResult());
    }
}
