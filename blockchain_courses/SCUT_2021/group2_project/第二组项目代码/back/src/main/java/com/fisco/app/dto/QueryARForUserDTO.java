package com.fisco.app.dto;

import com.fisco.app.pojo.ApplyAgencyForUser;
import com.fisco.app.utils.TransApplyRole;
import com.fisco.app.utils.TransResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryARForUserDTO {
    private int id;
    private String applyRole;
    private String USCC;
    private String result;

    public QueryARForUserDTO(ApplyAgencyForUser applyAgency, TransResult transResult, TransApplyRole transApplyRole) {
        this.id = applyAgency.getId();
        this.applyRole = transApplyRole.transition(applyAgency.getApplyRole());
        this.USCC = applyAgency.getUSCC();
        this.result = transResult.transition(applyAgency.getResult());
    }
}
