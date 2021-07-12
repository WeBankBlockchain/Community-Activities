package com.fisco.app.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 授权颁发机构发放证书-用于前端向后端返回授权证书的结果
 * @author amer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCertDTO {
    private String certName;
    private String agencyId;
    private int result;
}
