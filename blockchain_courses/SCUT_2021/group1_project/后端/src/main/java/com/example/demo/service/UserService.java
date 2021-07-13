package com.example.demo.service;

import com.example.demo.pojo.Result;

public interface UserService {
    Result signup(String user_id, String password, int user_type, String port_name);

    Result signin(String user_id, String password);
}
