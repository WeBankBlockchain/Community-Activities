package com.fisco.app.utils;

import java.util.UUID;

/**
 * 生成指定长度的随机id
 * @author amer
 */
public class RandomId {
    public String getUUID(int length) {
        System.out.println(UUID.randomUUID());
        int end = 32<length?32:length;
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "").substring(0,end);
        return uuid;
    }
}
