package cn.edu.scut.medicalresourceflow.entity.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdatePwdBO {
    @NotEmpty
    private String password;
}

