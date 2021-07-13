package com.fisco.app.controller;

import com.fisco.app.dto.ApplyIdDTO;
import com.fisco.app.dto.QueryARForAdminDTO;
import com.fisco.app.dto.QueryARForUserDTO;
import com.fisco.app.result.JsonResult;
import com.fisco.app.result.JsonResultFactory;
import com.fisco.app.result.ResultCode;
import com.fisco.app.service.ApplyAgencyService;
import com.fisco.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class ApplyAgencyController {

    @Autowired
    ApplyAgencyService applyAgencyService;

    @Autowired
    UserService userService;

    /**
     * 查询证书申请列表（用户）
     * @author amer
     * @return
     */
    @GetMapping("/Info/ApplyAgencyList")
    public JsonResult queryARListForUser(@RequestBody ApplyIdDTO applyIdDTO){
        String applyId = applyIdDTO.getApplyId();
        JsonResult result = null;
        JsonResultFactory jsonResultFactory = JsonResultFactory.getInstance();
        ArrayList<QueryARForUserDTO> list = applyAgencyService.queryARForUser(applyId);
        result = jsonResultFactory.buildSuccessResult(list);
        return result;
    }

    /**
     * 查询机构/企业认证申请列表（管理员）
     * @author amer
     * @return
     */
    @GetMapping("/Info/ApplyAgencyListForAdmin")
    public JsonResult queryARListForAdmin(){
        JsonResult result = null;
        JsonResultFactory jsonResultFactory = JsonResultFactory.getInstance();
        ArrayList<QueryARForAdminDTO> list = applyAgencyService.queryARForAdmin();
        result = jsonResultFactory.buildSuccessResult(list);
        return result;
    }

    @PostMapping("/Apply/AgencyEnt")
    public JsonResult applyToEnt(@RequestBody Map<String,String> map) {
        JsonResult result = null;
        JsonResultFactory jsonResultFactory = JsonResultFactory.getInstance();
        String applyId = map.get("applyId");
        String uscc = map.get("uscc");
        if (applyId == null || uscc == null) {
            result = jsonResultFactory.buildFailResult(ResultCode.PARAM_IS_BLANK);
            return result;
        }
        // check
        Integer role = userService.findRoleById(applyId);
        if (role == 2 || role == 4) {
            result = jsonResultFactory.buildFailResult(ResultCode.PARAM_FORMAT_ERROR);
            return result;
        }
        applyAgencyService.addAgencyEnt(applyId, uscc);
        result = jsonResultFactory.buildSuccessResult();
        return result;
    }

    @PostMapping("/Apply/AgencyCA")
    public JsonResult applyToCA(@RequestBody Map<String,String> map) {
        JsonResult result = null;
        JsonResultFactory jsonResultFactory = JsonResultFactory.getInstance();
        String applyId = map.get("applyId");
        String uscc = map.get("uscc");
        if (applyId == null || uscc == null) {
            result = jsonResultFactory.buildFailResult(ResultCode.PARAM_IS_BLANK);
            return result;
        }
        // check
        Integer role = userService.findRoleById(applyId);
        if (role == 3 || role == 4) {
            result = jsonResultFactory.buildFailResult(ResultCode.PARAM_FORMAT_ERROR);
            return result;
        }
        applyAgencyService.addAgencyCA(applyId, uscc);
        result = jsonResultFactory.buildSuccessResult();
        return result;
    }

}
