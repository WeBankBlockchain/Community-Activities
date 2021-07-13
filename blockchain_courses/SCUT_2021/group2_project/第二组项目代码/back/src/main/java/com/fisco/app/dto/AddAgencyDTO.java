package com.fisco.app.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 授权个人用户成为企业/机构-用于前端向后端返回授权的结果
 * @author amer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddAgencyDTO {
    private int id;
    private String applyId;
    private int result;
}
