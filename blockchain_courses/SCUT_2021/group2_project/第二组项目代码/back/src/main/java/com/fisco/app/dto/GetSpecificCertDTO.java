package com.fisco.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSpecificCertDTO {
    private String certId;
    private String userId;
}
