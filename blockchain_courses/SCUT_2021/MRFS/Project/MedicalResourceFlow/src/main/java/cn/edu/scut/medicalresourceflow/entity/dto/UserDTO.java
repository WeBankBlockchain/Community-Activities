package cn.edu.scut.medicalresourceflow.entity.dto;

import cn.edu.scut.medicalresourceflow.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO implements Serializable {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户邮箱
     */
    private String mail;

    /**
     * 用户角色
     */
    private String role;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户省份
     */
    private String province;

    /**
     * 注册时间
     */
    private Date registerAt;

    /**
     * 更新时间
     */
    private Date updateAt;

    public UserDTO(User user){
        this.setMail(user.getMail());
        this.setRegisterAt(user.getRegisterAt());
        this.setRole(user.getRole());
        this.setUpdateAt(user.getUpdateAt());
        this.setUserId(user.getUserId());
        this.setName(user.getName());
        this.setProvince(user.getProvince());
    }
}

