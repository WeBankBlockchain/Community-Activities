package cn.edu.scut.medicalresourceflow.entity.bo;

import cn.edu.scut.medicalresourceflow.validate.Insert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceBO {
    String ownerAddress;

    BigInteger resourceId;

    BigInteger resourceNum;

    @NotEmpty(groups = {Insert.class})
    String resourceName;

    @NotEmpty(groups = {Insert.class})
    String category;

    @NotEmpty(groups = {Insert.class})
    String batchCode;

    Boolean isUsed;

    String province;

    String imgUrl="";

    String info = "创建物资";
}
