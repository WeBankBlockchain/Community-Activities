package com.fisco.app.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertVerifyDTO {
    boolean isRight;
    CertSpecificDTO certInfo;

    public void setisRight(boolean state){
        this.isRight = state;
    }
}
