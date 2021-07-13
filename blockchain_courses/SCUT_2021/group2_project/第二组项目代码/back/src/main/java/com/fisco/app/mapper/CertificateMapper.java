package com.fisco.app.mapper;

import com.fisco.app.dto.CertSimpleDTO;
import com.fisco.app.dto.UserCertDTO;
import com.fisco.app.dto.CertSpecificDTO;
import com.fisco.app.pojo.Certificate;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * @author amer
 */
@Mapper
public interface CertificateMapper {
    int addCertificate(Certificate certificate);
    ArrayList<CertSimpleDTO> querySimpleCertList(String user_id);
    CertSpecificDTO certSpecific(String user_id, String cert_id);
}
