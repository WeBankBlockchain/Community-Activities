package cn.edu.scut.medicalresourceflow.entity.dto;

import cn.edu.scut.medicalresourceflow.validate.Insert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDTO {
    AccountDTO owner;

    BigInteger resourceId;

    BigInteger resourceNum;

    String resourceName;

    String category;

    String batchCode;

    Boolean isUsed;

    String province;

    String imgUrl="";
}
