package com.fisco.app.mapper;

import com.fisco.app.dto.CertSimpleDTO;
import com.fisco.app.dto.CertSpecificDTO;
import com.fisco.app.dto.LoginDTO;
import com.fisco.app.pojo.Certificate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class CertificateMapperTest {

    @Autowired
    CertificateMapper certificateMapper;

    @Test
    public void addCertificateTest(){
        System.out.println(certificateMapper.addCertificate(new Certificate("1235453", "高级软件工程师认证", "100004", 2)));
    }

    @Test
    public void getCertificateTest(){
        ArrayList<CertSimpleDTO> certList = certificateMapper.querySimpleCertList("100002");
        System.out.println(certList.toString());
    }

    @Test
    public  void getcertSpecificTest(){
        CertSpecificDTO certSpecific = certificateMapper.certSpecific("100002","1235453");
        System.out.println(certSpecific.toString());
    }
}
