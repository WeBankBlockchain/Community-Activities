package cn.edu.scut.medicalresourceflow.entity.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
public class ChangeRoleBO {
    @NotNull
    Integer userId;

    @NotEmpty
    String role;
}
