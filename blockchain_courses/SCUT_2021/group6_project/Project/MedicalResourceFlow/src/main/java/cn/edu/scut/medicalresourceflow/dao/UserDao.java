package cn.edu.scut.medicalresourceflow.dao;

import cn.edu.scut.medicalresourceflow.entity.User;
import cn.edu.scut.medicalresourceflow.entity.dto.UserDTO;

import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    User selectByMail(String mail);

    List<UserDTO> selectByRoleNameProvince(String role,String name,String province);

    List<UserDTO> selectByProvince(String province);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int getUserTokenVerByPrimaryKey(Integer userId);

    int updateTokenVerByPrimaryKey(Integer userId);

    int getCountByRole(String role);



}