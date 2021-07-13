package com.fisco.app.mapper;

import com.fisco.app.dto.AddAgencyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplyAgencyMapperTest {

    @Autowired
    ApplyAgencyMapper applyAgencyMapper;

    @Test
    public void updateResultTest(){
        System.out.println(applyAgencyMapper.updateResult(new AddAgencyDTO(10000,"100002", 1)));
    }

    @Test
    public void findApplyAgency(){
        System.out.println(applyAgencyMapper.findApplyAgency("100002").toString());
    }

    @Test
    public void QueryARForUserTest(){
        System.out.println(applyAgencyMapper.QueryARForUser("100002").toString());
    }

    @Test
    public void QueryARForAdminTest(){
        System.out.println(applyAgencyMapper.QueryARForAdmin().toString());
    }
}
