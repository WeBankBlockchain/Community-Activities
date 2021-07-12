package cn.edu.scut.medicalresourceflow.service;

import cn.edu.scut.medicalresourceflow.entity.User;
import cn.edu.scut.medicalresourceflow.entity.dto.UserDTO;

import java.util.List;

public interface UserService {
    int deleteByPrimaryKey(Integer userId);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    User selectByMail(String mail);

    List<UserDTO> selectByRoleNameProvince(String role, String name, String province);

    List<UserDTO> selectByProvince(String province);

    int updateByPrimaryKeySelective(User record);

    int getCountByRole(String role);
}
