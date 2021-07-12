package com.example.demo.pojo;

import java.math.BigInteger;

public class LoginRes {
    private String token;
    private BigInteger type;
    public LoginRes(String token, BigInteger type){
        this.token = token;
        this.type = type;
    }
    public String getToken(){return token;}

    public BigInteger getType() {
        return type;
    }
}
