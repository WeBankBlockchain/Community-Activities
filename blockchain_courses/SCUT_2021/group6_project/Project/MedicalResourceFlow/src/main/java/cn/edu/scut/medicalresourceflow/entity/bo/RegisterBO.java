package cn.edu.scut.medicalresourceflow.entity.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterBO implements Serializable {

    /**
     * 用户邮箱
     */
    private String mail;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户省份
     */
    private String province;

    /**
     * 用户密码
     */
    private String password;

    private String captcha;
}
