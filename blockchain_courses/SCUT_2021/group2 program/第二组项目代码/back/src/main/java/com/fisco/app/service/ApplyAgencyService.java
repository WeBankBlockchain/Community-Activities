package com.fisco.app.service;

import com.fisco.app.dto.AddAgencyDTO;
import com.fisco.app.dto.AwardCertDTO;
import com.fisco.app.dto.QueryARForAdminDTO;
import com.fisco.app.dto.QueryARForUserDTO;
import com.fisco.app.mapper.ApplyAgencyMapper;
import com.fisco.app.pojo.ApplyAgency;
import com.fisco.app.pojo.ApplyAgencyForAdmin;
import com.fisco.app.pojo.ApplyAgencyForUser;
import com.fisco.app.utils.TransApplyRole;
import com.fisco.app.utils.TransResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ApplyAgencyService {

    @Autowired
    ApplyAgencyMapper applyAgencyMapper;

    /**
     * @author amer
     * @param addAgencyDTO
     * @return
     */
    public boolean updateResult(AddAgencyDTO addAgencyDTO){
        int i = applyAgencyMapper.updateResult(addAgencyDTO);
        if(i == 0){
            return false;
        }
        return true;
    }

    /**
     * 根据id查询机构申请结果
     * @author amer
     * @param id
     * @return
     */
    public ApplyAgency findApplyAgency(String id){
        return applyAgencyMapper.findApplyAgency(id);
    }

    /**
     * 查询机构/企业认证申请列表（用户）
     * @author amer
     * @param applyId
     * @return
     */
    public ArrayList<QueryARForUserDTO> queryARForUser(String applyId){
        ArrayList<ApplyAgencyForUser> agencyForUsers = applyAgencyMapper.QueryARForUser(applyId);
        System.out.println(agencyForUsers.toString());
        ArrayList<QueryARForUserDTO> list = new ArrayList<>();
        TransResult transResult = new TransResult();
        TransApplyRole transApplyRole = new TransApplyRole();
        for (ApplyAgencyForUser agency : agencyForUsers) {
            list.add(new QueryARForUserDTO(agency,transResult, transApplyRole));
        }
        return list;
    }

    /**
     * 查询机构/企业认证申请列表（管理员）
     * @author amer
     * @return
     */
    public ArrayList<QueryARForAdminDTO> queryARForAdmin(){
        ArrayList<ApplyAgencyForAdmin> applyAgencyForAdmins = applyAgencyMapper.QueryARForAdmin();
        TransResult transResult = new TransResult();
        TransApplyRole transApplyRole = new TransApplyRole();
        ArrayList<QueryARForAdminDTO> list = new ArrayList<>();
        for (ApplyAgencyForAdmin agency : applyAgencyForAdmins) {
            list.add(new QueryARForAdminDTO(agency,transResult,transApplyRole));
        }
        return list;
    }

    public void addAgencyEnt(String userId, String uscc) {
        applyAgencyMapper.addApplyAgency(userId, uscc, 2);
    }

    public void addAgencyCA(String userId, String uscc) {
        applyAgencyMapper.addApplyAgency(userId, uscc, 3);
    }



}
