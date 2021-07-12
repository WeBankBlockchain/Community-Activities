package cn.edu.scut.medicalresourceflow.entity;

import java.io.Serializable;
import java.util.Date;

import cn.edu.scut.medicalresourceflow.entity.bo.RegisterBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * user
 * @author 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户邮箱
     */
    private String mail;

    /**
     * 用户密码
     */
    private String password;

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

    /**
     * token版本
     */
    private Integer tokenVer;

    private static final long serialVersionUID = 1L;

    public User(RegisterBO registerBO){
        this.setMail(registerBO.getMail());
        this.setName(registerBO.getName());
        this.setProvince(registerBO.getProvince());
    }
}