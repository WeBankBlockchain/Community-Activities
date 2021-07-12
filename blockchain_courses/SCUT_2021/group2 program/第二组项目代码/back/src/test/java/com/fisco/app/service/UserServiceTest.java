package com.fisco.app.service;

import com.fisco.app.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Test
    public void getUsersTest(){
        ArrayList<UserDTO> users = userService.getUsers(1);
        System.out.println(users.toString());
    }
}
