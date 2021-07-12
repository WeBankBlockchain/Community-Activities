package com.example.demo.controller;

import com.example.demo.pojo.Result;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/usr")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    @ResponseBody
    public Result signup(@RequestParam("user_id") String user_id,
                         @RequestParam("password") String password,
                         @RequestParam("user_type") int user_type,
                         @RequestParam(value = "port_name", defaultValue = "") String port_name) {
        return userService.signup(user_id, password, user_type, port_name);
    }

    @PostMapping("/signin")
    @ResponseBody
    public Result signin(@RequestParam("user_id") String user_id,
                         @RequestParam("password") String password) {
        return userService.signin(user_id, password);
    }
}

