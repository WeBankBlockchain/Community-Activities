package org.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.pojo.User;
import org.example.demo.request.UserRequest;
import org.example.demo.response.Result;
import org.example.demo.service.ContractService;
import org.example.demo.service.UserService;
import org.example.demo.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContractService contractService;

    @CrossOrigin
    @PostMapping(value = "/api/login")
    @ResponseBody
    public Result<?> login(@RequestBody UserRequest loginRequest, HttpSession session)
            throws Exception {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        User user = userService.get(username, password);
        if (null == user) {
            return Result.fail("wrong username or password");
        } else {
//            session.setAttribute("PrivateKey", user.getPrivateKey()); //session保存address
            String privateKey = user.getPrivateKey();
            log.info("user's key:{}",privateKey);
            contractService.setContracts(privateKey);
            return Result.success("login success");
        }
    }

    @CrossOrigin
    @PostMapping(value = "/api/signin")
    @ResponseBody
    public Result<?> signin(@RequestBody UserRequest signInRequest)
            throws NoSuchAlgorithmException {
        String username = signInRequest.getUsername();
        String password = signInRequest.getPassword();
        if(userService.isExists(username)) {
            return Result.fail("username exists");
        }
        else {
            log.info("username doesn't exist, creating account");
            userService.add(username, password);
            log.info("account create success");
            return Result.success("sign in success");
        }
    }


}
