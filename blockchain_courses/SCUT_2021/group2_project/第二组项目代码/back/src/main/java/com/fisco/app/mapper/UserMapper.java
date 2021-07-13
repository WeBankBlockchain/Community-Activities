package com.fisco.app.mapper;

import com.fisco.app.dto.AddAgencyDTO;
import com.fisco.app.dto.LoginDTO;
import com.fisco.app.pojo.ApplyAgency;
import com.fisco.app.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


//这个注解表示了这是一个mybatis的mapper类
@Mapper
@Repository
public interface UserMapper {
    ArrayList<User> queryUserList();
    ArrayList<User> queryListOfUser();
    ArrayList<User> queryListOfCompany();
    ArrayList<User> queryListOfAgency();
    int updateRole(ApplyAgency applyAgency);
    // add user
    int addUser(@Param("userId") String userId, @Param("name") String name,
                @Param("pwd") String pwd, @Param("role")int role,@Param("uscc")String uscc);
    int updatePwd(@Param("userId") String userId, @Param("pwd")String pwd);

    Integer getRoleById(@Param("userId")String userId);
    User findById(@Param("userId") String userId);

    //login
    int loginVerify(LoginDTO user);
    String getUserName(String user_id);
}
