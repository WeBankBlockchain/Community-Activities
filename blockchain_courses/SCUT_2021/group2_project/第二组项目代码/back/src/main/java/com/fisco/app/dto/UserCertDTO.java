package com.fisco.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCertDTO {
    private String userName;
    private ArrayList<CertSimpleDTO> certSimpleList;

    public void getUserName(String user_Name){
        this.userName = user_Name;
    }

    public void getCertSimpleList(ArrayList<CertSimpleDTO> certSimpleList){
        this.certSimpleList = certSimpleList;
    }
}
