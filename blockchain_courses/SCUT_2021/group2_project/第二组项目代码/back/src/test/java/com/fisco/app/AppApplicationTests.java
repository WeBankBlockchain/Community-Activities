package com.fisco.app;

import com.fisco.app.mapper.UserMapper;
import com.fisco.app.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class AppApplicationTests {
    @Autowired
    DataSource dataSource;
    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() throws SQLException {
        //
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());

        List<User> users = userMapper.queryUserList();
        for (User user : users) {
            System.out.println(user.toString());
        }
    }

}
