package com.fisco.app.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertSimpleDTO {
    private String certId;
    private String certName;
    private String content;
}
