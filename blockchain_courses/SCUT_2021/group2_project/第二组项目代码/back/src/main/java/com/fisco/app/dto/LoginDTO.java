package com.fisco.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDTO {
    private String userId;
    private String pwd;

    public LoginDTO(String userId,String pwd){
        this.userId = userId;
        this.pwd = pwd;
    }

}
