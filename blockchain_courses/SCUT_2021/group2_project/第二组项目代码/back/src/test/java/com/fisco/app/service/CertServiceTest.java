package com.fisco.app.service;

import com.fisco.app.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CertServiceTest {
    @Autowired
    CertService certService;
    @Test
    public void getCertificatesTest(){
        UserCertDTO uctest = certService.getCertificates("100002");
        System.out.println(uctest);
    }

    @Test
    public void getSpecificTest(){
        CertSpecificDTO sctest = certService.getSpecific("100002","1235453");
        System.out.println(sctest);
    }

    @Test
    public void generateVerifyCodeTest() throws Exception {
        CertificateCodeDTO vctest = certService.generateVerifyCode("100002","1235453");
        System.out.println(vctest);
    }

    @Test
    public void CertVerifyTest() throws Exception {
        CertificateCodeDTO vctest = certService.generateVerifyCode("100002","1235453");
        String certificateCodetest = vctest.getCertificateCode();
        CertVerifyDTO cvtest = certService.CertVerify(certificateCodetest);
        System.out.println(cvtest);
    }
}
