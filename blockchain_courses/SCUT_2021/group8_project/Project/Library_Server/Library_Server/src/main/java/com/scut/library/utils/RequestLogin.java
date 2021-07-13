package com.scut.library.utils;

import lombok.Data;

@Data
public class RequestLogin {
    private String user_id;
    private String password;

    public String getPassword() {
        return password;
    }

    public String getUser_id() {
        return user_id;
    }
}
