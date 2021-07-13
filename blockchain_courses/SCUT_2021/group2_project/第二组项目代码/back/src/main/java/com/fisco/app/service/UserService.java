package com.fisco.app.service;


import com.fisco.app.dto.UserDTO;
import com.fisco.app.mapper.UserMapper;
import com.fisco.app.pojo.ApplyAgency;
import com.fisco.app.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 提供用户服务
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    /**
     * 返回用户列表(管理员)
     * @author amer
     */
    public ArrayList<UserDTO> getUsers(int i){
        ArrayList<User> userList = new ArrayList<>();
        switch (i){
            case 1:
                userList = userMapper.queryListOfUser();
                break;
            case 2:
                userList = userMapper.queryListOfCompany();
                break;
            case 3:
                userList = userMapper.queryListOfAgency();
                break;
        }
        //data processing
        ArrayList<UserDTO> users = new ArrayList<>();
        for (User user : userList) {
            users.add(new UserDTO(user));
        }
        return users;
    }

    /**
     * 更新角色权限和USCC
     * @author amer
     * @param applyAgency
     * @return
     */
    public boolean updateRole(ApplyAgency applyAgency){
        int num = userMapper.updateRole(applyAgency);
        if(num == 0){
            return false;
        }
        return true;
    }


    /**
     * add user
     * @param userId
     * @param name
     * @param password
     * @return
     */
    public Boolean addUser(String userId, String name, String password) {
        return  userMapper.addUser(userId, name, password, 1 , null) != 0;
    }

    public boolean IsExist(String userId) {
        return userMapper.findById(userId) != null;
    }
    public Boolean updatePwd(String userId, String pwd) {
        return userMapper.updatePwd(userId, pwd) != 0;
    }

    public Integer findRoleById(String userId) {
        return userMapper.getRoleById(userId);
    }
}
