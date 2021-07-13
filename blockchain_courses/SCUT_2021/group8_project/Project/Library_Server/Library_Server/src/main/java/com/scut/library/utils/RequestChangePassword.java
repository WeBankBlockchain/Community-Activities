package com.scut.library.utils;

public class RequestChangePassword {
    private String user_id;
    private String old_password;
    private String new_password;

    public String getUser_id() {
        return user_id;
    }

    public String getNew_password() {
        return new_password;
    }

    public String getOld_password() {
        return old_password;
    }
}
