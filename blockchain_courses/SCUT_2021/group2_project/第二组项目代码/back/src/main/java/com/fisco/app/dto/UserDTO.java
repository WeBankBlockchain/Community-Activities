package com.fisco.app.dto;

import com.fisco.app.pojo.User;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 用于向前端返回用户信息
 * @author amer
 */
@Data
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String name;
    private String role;
    private String USCC;

    public UserDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
        switch (user.getRole()){
            case 0:
                this.role = "管理员";
                break;
            case 1:
                this.role = "个人用户";
                break;
            case 2:
                this.role = "企业";
                break;
            case 3:
                this.role = "机构";
                break;
            case 4:
                this.role = "企业&机构";
                break;
            default:
                this.role = "-";
        }
        this.USCC = user.getUSCC();
    }
}
