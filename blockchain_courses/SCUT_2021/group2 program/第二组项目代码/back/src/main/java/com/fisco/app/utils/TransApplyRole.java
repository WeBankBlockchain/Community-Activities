package com.fisco.app.utils;

public class TransApplyRole {
    public String transition(int role){
        String applyRole = "-";
        switch (role){
            case 0:
                applyRole = "管理员";
                break;
            case 1:
                applyRole = "个人用户";
                break;
            case 2:
                applyRole = "企业";
                break;
            case 3:
                applyRole = "机构";
                break;
            case 4:
                applyRole = "企业&机构";
                break;
            default:
                break;
        }
        return applyRole;
    }
}
