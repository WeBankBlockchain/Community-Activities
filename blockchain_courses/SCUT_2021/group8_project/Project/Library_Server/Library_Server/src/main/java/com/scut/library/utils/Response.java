package com.scut.library.utils;

import lombok.Data;

@Data
public class Response {
    private String code;
    private String message;
    private Object data;
    public Response(String code, String msg,Object data){
        this.code = code;
        this.message = msg;
        this.data=data;
    }
}
