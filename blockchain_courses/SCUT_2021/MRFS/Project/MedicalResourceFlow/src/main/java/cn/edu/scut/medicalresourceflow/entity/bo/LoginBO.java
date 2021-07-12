package cn.edu.scut.medicalresourceflow.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class LoginBO implements Serializable {

    @NotEmpty(message = "邮箱为空")
    @Email(message = "邮箱格式错误")
    private String mail;

    @NotEmpty(message = "密码为空")
    private String password;

}