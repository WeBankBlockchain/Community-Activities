package com.fisco.app.controller;


import com.fisco.app.dto.*;
import com.fisco.app.result.JsonResult;
import com.fisco.app.result.JsonResultFactory;
import com.fisco.app.result.ResultCode;
import com.fisco.app.service.ApplyAgencyService;
import com.fisco.app.service.CertService;
import com.fisco.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class CertController {
    @Autowired
    CertService certService;

    @Autowired
    UserService userService;


    /**
     * 申请颁发证书
     * @author amer
     * @param item
     * @return result
     */
    @PostMapping("/Apply/Certificate")
    public JsonResult applyCertificate(@RequestBody ApplyCertDTO item){
        JsonResult result = null;
        JsonResultFactory jsonResultFactory = JsonResultFactory.getInstance();
        try{
            if(item.getCertName().isEmpty()||item.getAgencyId().isEmpty()||item.getCertName().isBlank()){
                result = jsonResultFactory.buildFailResult(ResultCode.PARAM_IS_BLANK);
            }
            else{
                certService.applyCertificate(item);
                result = jsonResultFactory.buildSuccessResult();
            }
            return result;
        }catch (DuplicateKeyException e){
            return jsonResultFactory.buildFailResult(ResultCode.DUPLICATE_REQUEST);
        }catch (DataIntegrityViolationException e){
            return jsonResultFactory.buildFailResult(ResultCode.DATABASE_INSERT_FAIL);
        }
    }

    /**
     * 授权颁发机构发放证书
     * @author amer
     * @param addCertDTO
     * @return
     */
    @PostMapping("/Auth/Certificate")
    public JsonResult authCertificate(@RequestBody AddCertDTO addCertDTO){
        JsonResultFactory jsonResultFactory = JsonResultFactory.getInstance();
        certService.addCertiicate(addCertDTO);
        JsonResult result = jsonResultFactory.buildSuccessResult();
        return result;
    }

    /**
     * 查询证书申请列表（用户）
     * @author amer
     * @return
     */
    @GetMapping("/Info/ApplyCertListForUser")
    public JsonResult ApplyCertListForUser(@RequestBody AgencyIdDTO agencyIdDTO){
        String agencyId = agencyIdDTO.getAgencyId();
        ArrayList<QueryCRListOfUserDTO> list = certService.findCRListForUser(agencyId);
        JsonResultFactory jsonResultFactory = JsonResultFactory.getInstance();
        JsonResult result = jsonResultFactory.buildSuccessResult(list);
        return result;
    }

    /**
     * 查询证书申请列表（管理员）
     * @return
     */
    @GetMapping("/Info/ApplyCertListForAdmin")
    public JsonResult ApplyCertListForAdmin(){
        ArrayList<QueryCRListForAdminDTO> list = certService.findCRListForAdmin();
        JsonResultFactory jsonResultFactory = JsonResultFactory.getInstance();
        JsonResult result = jsonResultFactory.buildSuccessResult(list);
        return result;
    }

    @PostMapping("/AwardCert")
    public JsonResult awardCertToUser(@RequestBody AwardCertDTO awardCertDTO) {
        JsonResult result = null;
        JsonResultFactory jsonResultFactory = JsonResultFactory.getInstance();
        //check
        if (!userService.IsExist(awardCertDTO.getUserId())) {
            result = jsonResultFactory.buildFailResult(ResultCode.PARAM_ERROR);
        }
        certService.addOwnEntity(awardCertDTO);
        result = jsonResultFactory.buildSuccessResult();
        return result;
    }

    /**
     * 查询所有证书(用户)
     * @return
     */
    @PostMapping("/GetCertificate")
    public JsonResult getCertificatesForUser(@RequestBody GetCertDTO getCertDTO){
        String user_id = getCertDTO.getUserId();
        UserCertDTO certList = certService.getCertificates(user_id);
        JsonResultFactory jsonResultFactory = JsonResultFactory.getInstance();
        JsonResult result = jsonResultFactory.buildSuccessResult(certList);
        return result;
    }

    /**
     * 查询证书详细信息(用户)
     * @return
     */
    @PostMapping("/GetCertInfo")
    public JsonResult getSpecificCertInfo(@RequestBody GetSpecificCertDTO getSpecificCertDTO){
        String user_id = getSpecificCertDTO.getUserId();
        String cert_id = getSpecificCertDTO.getCertId();
        CertSpecificDTO specificCert = certService.getSpecific(user_id,cert_id);
        JsonResultFactory jsonResultFactory = JsonResultFactory.getInstance();
        JsonResult result = jsonResultFactory.buildSuccessResult(specificCert);
        return result;
    }

    /**
     * 生成验证码(用户)
     * @return
     */
    @PostMapping("/CertificateCode")
    public JsonResult generateVerifyCode(@RequestBody GenerateVerifyCodeDTO generateVerifyCodeDTO) throws Exception {
        String user_id = generateVerifyCodeDTO.getUserId();
        String cert_id = generateVerifyCodeDTO.getCertId();

        CertificateCodeDTO VerifyCode = certService.generateVerifyCode(user_id,cert_id);

        JsonResultFactory jsonResultFactory = JsonResultFactory.getInstance();
        JsonResult result = jsonResultFactory.buildSuccessResult(VerifyCode);
        return result;
    }
    /**
     * 解析验证码(公司)
     * @return
     */
    @PostMapping("/CertificateVerify")
    public JsonResult verifyCertificate(@RequestBody CertificateCodeDTO certificateCodeDTO) throws Exception {
        String certificateCode = certificateCodeDTO.getCertificateCode();

        CertVerifyDTO verifyResult = certService.CertVerify(certificateCode);
        JsonResultFactory jsonResultFactory = JsonResultFactory.getInstance();
        JsonResult result = jsonResultFactory.buildSuccessResult(verifyResult);
        return result;
    }
}
