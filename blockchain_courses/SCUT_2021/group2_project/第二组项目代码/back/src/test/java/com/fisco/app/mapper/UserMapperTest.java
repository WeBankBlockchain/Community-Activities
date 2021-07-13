package com.fisco.app.mapper;

import com.fisco.app.dto.AddAgencyDTO;
import com.fisco.app.dto.LoginDTO;
import com.fisco.app.pojo.ApplyAgency;
import com.fisco.app.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.UUID;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void queryUserListTest(){
        ArrayList<User> users = userMapper.queryUserList();
        System.out.println(users.toString());;
    }

    @Test
    public void updateRoleTest(){

    }

    @Test
    public void loginVerify(){
        System.out.println("User Role:"+userMapper.loginVerify(new LoginDTO("100001","123456")));
    }

    @Test
    public void addUser() {
        User user = new User();
        user.setId(UUID.randomUUID().toString().replaceAll("-",""));
        user.setName("xiaoming");
        user.setUSCC(null);
        user.setPwd("123456");
        user.setRole(1);
        userMapper.addUser(user.getId(),user.getName(),user.getPwd(),user.getRole(),user.getUSCC());
    }

    @Test
    public void modifyPwd() {
        userMapper.updatePwd("8a1512359913430485999acf52a80183","654321");
    }
}
