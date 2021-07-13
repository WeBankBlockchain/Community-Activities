package com.fisco.app.mapper;

import com.fisco.app.dto.AddCertDTO;
import com.fisco.app.pojo.ApplyCert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplyCertMapperTest {
    @Autowired
    ApplyCertMapper applyCertMapper;

    @Test
    public void applyCertificateTest(){
        System.out.println(applyCertMapper.applyCertificate(new ApplyCert("Teacher certification", "100005", 30, 0)));
    }

    @Test
    public void addCertificateTest(){
        AddCertDTO addCertDTO = new AddCertDTO("Teacher certification","100004",1);
        applyCertMapper.updateResult(addCertDTO);
    }

    @Test
    public void queryMineListTest(){
        System.out.println(applyCertMapper.queryMineList("100004").toString());
    }

    @Test
    public void ApplyCertInfoTest(){
        System.out.println(applyCertMapper.queryListForAdmin().toString());
    }


}


