package com.example.demo.service;

import com.example.demo.pojo.Result;

public interface ChainService {
    Result genId(String user_id, String token);

    Result initHead(String user_id, String id, String time, String location, String token);

    Result giveRight(String from, String to, String id, String token);

    Result addNode(String user_id, String id, String in_time, String out_time, String token);
}
