package com.fisco.app.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyLeftJoinCert {
    private String certId;
    private String certName;
    private String agencyId;
    private int validity;
    private int result;
}
