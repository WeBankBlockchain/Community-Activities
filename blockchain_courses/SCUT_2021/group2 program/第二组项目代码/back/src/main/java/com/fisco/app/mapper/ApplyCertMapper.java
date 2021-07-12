package com.fisco.app.mapper;

import com.fisco.app.dto.AddCertDTO;
import com.fisco.app.pojo.ApplyCert;
import com.fisco.app.pojo.ApplyCertInfo;
import com.fisco.app.pojo.ApplyLeftJoinCert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * @author amer
 */
@Mapper
public interface ApplyCertMapper {
    int applyCertificate(ApplyCert applyCert);
    int updateResult(AddCertDTO addCertDTO);
    int findApplyCertValidity(String certName,String agencyId);
    ArrayList<ApplyLeftJoinCert> queryMineList(@Param("id") String agencyId);
    ArrayList<ApplyCertInfo> queryListForAdmin();
}
