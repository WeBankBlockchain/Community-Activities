package com.fisco.app.service;

import com.fisco.app.dto.LoginDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoginServiceTest {
    @Autowired
    LoginService loginService;
    @Test
    public void loginVerifyTest(){
        System.out.println(loginService.loginVerify(new LoginDTO("100001","123456")));
    }
}
