package com.fisco.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 申请颁发证书-用于前端向后端返回申请证书的信息
 * @author amer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyCertDTO {
    private String certName;
    private String agencyId;
    private int validity;
}
