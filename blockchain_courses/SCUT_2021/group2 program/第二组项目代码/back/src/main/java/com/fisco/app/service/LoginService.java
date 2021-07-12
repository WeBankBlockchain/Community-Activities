package com.fisco.app.service;


import com.fisco.app.dto.LoginDTO;
import com.fisco.app.dto.UserDTO;
import com.fisco.app.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fisco.app.pojo.User;

/**
 * 提供登录服务
 */
@Service
public class LoginService {
    @Autowired
    UserMapper userMapper;

    public int loginVerify(LoginDTO loginDTO){
        return userMapper.loginVerify(loginDTO);
    }
}