package com.fisco.app.mapper;

import com.fisco.app.pojo.ApplyAgency;
import com.fisco.app.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;

//这个注解表示了这是一个mybatis的mapper类
@Mapper
@Repository
public interface OwnMapper {
    public Integer addOwn(@Param("userId")String userId, @Param("certId")String certId,
                       @Param("content")String content, @Param("time")Timestamp time);
}
