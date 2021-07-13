package cn.edu.scut.medicalresourceflow.entity.vo;

import cn.edu.scut.medicalresourceflow.entity.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class UserVO implements Serializable {
    private UserDTO user;
    private String token;
}