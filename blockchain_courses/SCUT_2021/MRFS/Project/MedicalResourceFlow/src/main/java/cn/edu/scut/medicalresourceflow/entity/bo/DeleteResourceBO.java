package cn.edu.scut.medicalresourceflow.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteResourceBO {

    @NotNull
    BigInteger resourceId;

    String info = "物资被使用";
}
