package com.fisco.app.controller;

import com.fisco.app.dto.LoginDTO;
import com.fisco.app.result.JsonResult;
import com.fisco.app.result.JsonResultFactory;
import com.fisco.app.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;


    /**
     * 登录验证
     * @param loginDTO
     */
    @PostMapping("/login")
    public JsonResult loginVerify(@RequestBody LoginDTO loginDTO){
        JsonResult result = null;
        JsonResultFactory jsonResultFactory = JsonResultFactory.getInstance();
        result = jsonResultFactory.buildSuccessResult(loginService.loginVerify(loginDTO));
        return result;
    }
}
