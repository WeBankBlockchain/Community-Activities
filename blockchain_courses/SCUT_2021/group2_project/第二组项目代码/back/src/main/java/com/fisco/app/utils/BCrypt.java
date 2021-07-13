package com.fisco.app.utils;


//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 用户密码加密工具类
 * @author amer
 */

public class BCrypt {
    /**
    private BCryptPasswordEncoder passwordEncoder;

    public BCrypt(){
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public String encrypt(String pwd){
        String newPwd = passwordEncoder.encode(pwd);
        return newPwd;
    }

    public boolean isMatch(String pwd1, String pwd2){
        return passwordEncoder.matches(pwd1,pwd2);
    }
     */
}
