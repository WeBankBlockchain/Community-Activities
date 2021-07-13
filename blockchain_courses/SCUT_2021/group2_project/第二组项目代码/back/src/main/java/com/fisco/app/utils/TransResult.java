package com.fisco.app.utils;

public class TransResult {
    public String transition(int temp){
        String result = "未审核";
        switch (temp) {
            case 1:
                result = "通过";
                break;
            case 2:
                result = "未通过";
                break;
            default:
                break;
        }
        return result;
    }
}
