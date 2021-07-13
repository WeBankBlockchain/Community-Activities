package cn.edu.scut.medicalresourceflow.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    /**
     * 账户address
     */
    private String address;

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
     * user_id
     */
    private Integer userId;

}
