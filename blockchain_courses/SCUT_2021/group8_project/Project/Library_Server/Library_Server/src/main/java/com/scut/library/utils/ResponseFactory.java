package com.scut.library.utils;

import java.util.HashMap;
import java.util.Map;

public class ResponseFactory {
    Map<Integer,String>msg=new HashMap<>();
    public ResponseFactory(){
        msg.put(1,"成功！");
        msg.put(-200,"未登录！");
    }
    public void put(int code, String message){
        msg.put(code,message);
    }
    public Response getResponse(int code,Object data){
        return new Response(String.valueOf(code),msg.get(code),data);
    }
}
