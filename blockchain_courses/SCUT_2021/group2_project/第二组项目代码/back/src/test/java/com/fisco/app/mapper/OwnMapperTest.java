package com.fisco.app.mapper;


import com.fisco.app.client.OwnClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Date;


@SpringBootTest
public class OwnMapperTest {

    @Autowired
    OwnMapper ownMapper;

    @Autowired
    OwnClient ownClient;

    @Test
    public void addOwn() {
        Timestamp time = new Timestamp(new Date().getTime());
        System.out.println(ownMapper.addOwn("162406341","1235453","cet6", time));
    }

    @Test
    public void checkIsExist() {
        ownClient.IsExist("xiaoming","cet6");
    }
}