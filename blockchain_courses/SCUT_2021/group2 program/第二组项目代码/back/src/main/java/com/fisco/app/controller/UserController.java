package com.fisco.app.controller;


import com.fisco.app.dto.AddAgencyDTO;
import com.fisco.app.pojo.ApplyAgency;
import com.fisco.app.result.JsonResult;
import com.fisco.app.result.JsonResultFactory;
import com.fisco.app.result.ResultCode;
import com.fisco.app.service.ApplyAgencyService;
import com.fisco.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * 用户管理/功能
 */
@RestController
//@RequestMapping("/Info")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    ApplyAgencyService applyAgencyService;


    @PostMapping("/Auth/register")
    public JsonResult register(@RequestBody Map<String,String> map) {
        JsonResult result = null;
        JsonResultFactory jsonResultFactory = JsonResultFactory.getInstance();
        String name = map.get("name");;
        String password = map.get("pwd");
        if (name == null || password == null) {
            result = jsonResultFactory.buildFailResult(ResultCode.PARAM_IS_BLANK);
            return result;
        }
        Integer orderId;
        while (true) {
            orderId = UUID.randomUUID().toString().replaceAll("-", "").hashCode();
            orderId = orderId < 0 ? -orderId : orderId; //String.hashCode()
            if (!userService.IsExist(orderId.toString())) break;
        }
        Boolean flag = userService.addUser(orderId.toString(), name,password);
        result = jsonResultFactory.buildSuccessResult(orderId.toString());
        return result;
    }

    @PostMapping("/Auth/modifyPwd")
    public JsonResult modifyPwd(@RequestBody Map<String,String> map) {
        JsonResult result = null;
        JsonResultFactory jsonResultFactory = JsonResultFactory.getInstance();
        String userId = map.get("id");
        String pwd = map.get("pwd");
        if (userId == null || pwd == null) {
            result = jsonResultFactory.buildFailResult(ResultCode.PARAM_IS_BLANK);
            return result;
        }
        userService.updatePwd(userId,pwd);
        result = jsonResultFactory.buildSuccessResult();

        return result;
    }

    /**
     * 返回用户列表-user
     * @author amer
     */
    @GetMapping("/Info/UserList/User")
    public JsonResult getUsers(){
        JsonResult result = null;
        JsonResultFactory jsonResultFactory = JsonResultFactory.getInstance();
        result = jsonResultFactory.buildSuccessResult(userService.getUsers(1));
        return result;
    }

    /**
     * 返回用户列表-company
     * @author amer
     */
    @GetMapping("/Info/UserList/Company")
    public JsonResult getCompanies(){
        JsonResult result = null;
        JsonResultFactory jsonResultFactory = JsonResultFactory.getInstance();
        result = jsonResultFactory.buildSuccessResult(userService.getUsers(2));
        return result;
    }

    /**
     * 返回用户列表-agency
     * @author amer
     */
    @GetMapping("/Info/UserList/Agency")
    public JsonResult getAgencies(){
        JsonResult result = null;
        JsonResultFactory jsonResultFactory = JsonResultFactory.getInstance();
        result = jsonResultFactory.buildSuccessResult(userService.getUsers(3));
        return result;
    }

    /**
     * 授权用户成为企业/机构
     * @param addAgencyDTO
     * @return
     */
    @PostMapping("/Auth/Organization")
    public JsonResult AuthAgency(@RequestBody AddAgencyDTO addAgencyDTO){
        JsonResult result = null;
        JsonResultFactory jsonResultFactory = JsonResultFactory.getInstance();
        applyAgencyService.updateResult(addAgencyDTO);
        if(addAgencyDTO.getResult() == 1){
            ApplyAgency agency = applyAgencyService.findApplyAgency(addAgencyDTO.getApplyId());
            userService.updateRole(agency);
        }
        result = jsonResultFactory.buildSuccessResult();
        return result;
    }

}
