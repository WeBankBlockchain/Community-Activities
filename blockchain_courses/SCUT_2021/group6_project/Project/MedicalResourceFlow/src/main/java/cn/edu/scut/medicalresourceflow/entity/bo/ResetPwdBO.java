package cn.edu.scut.medicalresourceflow.entity.bo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class ResetPwdBO {
    @NotEmpty
    @Email
    private String mail;

    @NotEmpty
    private String password;

    @NotEmpty
    private String captcha;
}