package com.fisco.app.mapper;

import com.fisco.app.dto.AddAgencyDTO;
import com.fisco.app.pojo.ApplyAgency;
import com.fisco.app.pojo.ApplyAgencyForAdmin;
import com.fisco.app.pojo.ApplyAgencyForUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * @author amer
 */
@Mapper
public interface ApplyAgencyMapper {
    int updateResult(AddAgencyDTO addAgencyDTO);
    ApplyAgency findApplyAgency(@Param("Id") String Id);
    ArrayList<ApplyAgencyForUser> QueryARForUser(@Param("applyId")String applyId);
    ArrayList<ApplyAgencyForAdmin> QueryARForAdmin();
    void addApplyAgency(@Param("userId")String userId,@Param("uscc")String uscc,
                        @Param("role")Integer role);
}
