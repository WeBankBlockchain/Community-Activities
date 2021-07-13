package com.fisco.app.service;

import com.fisco.app.dto.*;
import com.fisco.app.client.OwnClient;
import com.fisco.app.dto.*;
import com.fisco.app.mapper.ApplyCertMapper;
import com.fisco.app.mapper.CertificateMapper;
import com.fisco.app.mapper.UserMapper;
import com.fisco.app.mapper.OwnMapper;
import com.fisco.app.pojo.ApplyCert;
import com.fisco.app.pojo.ApplyCertInfo;
import com.fisco.app.pojo.ApplyLeftJoinCert;
import com.fisco.app.pojo.Certificate;
import com.fisco.app.utils.RandomId;
import com.fisco.app.utils.TransResult;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.fisco.app.utils.AES;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

@Service
public class CertService {
    @Autowired
    ApplyCertMapper applyCertMapper;
    @Autowired
    CertificateMapper certificateMapper;
    @Autowired
    UserMapper userMapper;

    @Autowired
    OwnClient ownClient;

    @Autowired
    OwnMapper ownMapper;

    /**
     * 申请颁发证书
     * @author amer
     */
    public void applyCertificate(ApplyCertDTO certdto)throws DuplicateKeyException, DataIntegrityViolationException {
        applyCertMapper.applyCertificate(new ApplyCert(certdto));
    }

    /**
     * 授权颁发机构发放证书
     * @author amer
     * @param addCertDTO
     */
    public void addCertiicate(AddCertDTO addCertDTO){
        applyCertMapper.updateResult(addCertDTO);
        RandomId randomId = new RandomId();
        String id = randomId.getUUID(16);
        int validity = applyCertMapper.findApplyCertValidity(addCertDTO.getCertName(),addCertDTO.getAgencyId());
        Certificate certificate = new Certificate(id,addCertDTO.getCertName(),addCertDTO.getAgencyId(),validity);
        certificateMapper.addCertificate(certificate);
    }

    /**
     * 查询证书申请列表（用户）
     * @param agencyId
     * @return
     */
    public ArrayList<QueryCRListOfUserDTO> findCRListForUser(String agencyId){
        ArrayList<ApplyLeftJoinCert> applyCerts = applyCertMapper.queryMineList(agencyId);
        TransResult transResult = new TransResult();
        //transition
        ArrayList<QueryCRListOfUserDTO> list = new ArrayList<>();
        for (ApplyLeftJoinCert cert : applyCerts) {
            list.add(new QueryCRListOfUserDTO(cert,transResult));
        }
        return list;
    }

    /**
     * 查询证书申请列表（管理员）
     * @return
     */
    public ArrayList<QueryCRListForAdminDTO> findCRListForAdmin(){
        ArrayList<ApplyCertInfo> certInfos = applyCertMapper.queryListForAdmin();
        TransResult transResult = new TransResult();
        //transition
        ArrayList<QueryCRListForAdminDTO> list = new ArrayList<>();
        for (ApplyCertInfo certInfo : certInfos) {
            list.add(new QueryCRListForAdminDTO(certInfo,transResult));
        }
        return list;
    }

    public void addOwnEntity(AwardCertDTO awardCertDTO) {
        Timestamp time = new Timestamp(new Date().getTime());
        int num = ownMapper.addOwn(awardCertDTO.getUserId(),awardCertDTO.getCertId(),awardCertDTO.getContent(),time);
        // 这里是插入区块链中的内容
        if (num > 0) {
            ownClient.addOwn(awardCertDTO.getUserId(),awardCertDTO.getCertId(),awardCertDTO.getContent());
        }
    }

    public Boolean checkCertIsExist(String userId, String certId) {
        return ownClient.IsExist(userId, certId);
    }

    /**
     * 查看所有证书
     * @return
     */
    public UserCertDTO getCertificates(String user_id){
        UserCertDTO usercert = new UserCertDTO();
        usercert.getUserName(userMapper.getUserName(user_id));
        usercert.getCertSimpleList(certificateMapper.querySimpleCertList(user_id));
        return usercert;
    }

    /**
     * 查看证书详情
     * @return
     */
    public CertSpecificDTO getSpecific(String user_id,String cert_id){
        CertSpecificDTO certspecific = certificateMapper.certSpecific(user_id, cert_id);
        return certspecific;
    }

    /**
     * 生成验证码(用户)
     * @return
     */
    String key = "123456";
    public CertificateCodeDTO generateVerifyCode(String user_id,String cert_id) throws Exception {
        CertificateCodeDTO certificateCodeDTO = new CertificateCodeDTO();
        String content = user_id+" "+cert_id;

        byte[] VerifyCode = AES.encrypt(content.getBytes(), key.getBytes());

        certificateCodeDTO.getCertCode(VerifyCode);
        return certificateCodeDTO;
    }

    /**
     * 解析验证码(公司)
     * @return
     */
    public CertVerifyDTO CertVerify(String verifyCode) throws Exception {
        byte[] plainBytes = Base64.decodeBase64(verifyCode);//verifyCode.getBytes("ISO-8859-1");
        byte[] decode = AES.decrypt(plainBytes,key.getBytes());
        String userCert = new String(decode);
        String[] uc = userCert.split("\\s+");
        String userId = uc[0];
        String certId = uc[1];

        CertSpecificDTO certSpecific = certificateMapper.certSpecific(userId,certId);
        CertVerifyDTO certVerifyDTO = new CertVerifyDTO();
        boolean isRight = checkCertIsExist(userId,certId);
        certVerifyDTO.setisRight(isRight);
        certVerifyDTO.setCertInfo(certSpecific);

        return  certVerifyDTO;
    }
}
