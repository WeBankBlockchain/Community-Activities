package org.fisco.bcos.scutcloud.controller;

import com.google.gson.JsonObject;
import org.fisco.bcos.scutcloud.pojo.Request;
import org.fisco.bcos.scutcloud.result.Result;
import org.fisco.bcos.scutcloud.service.DataService;
import org.fisco.bcos.scutcloud.service.DeployService;
import org.fisco.bcos.scutcloud.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@ResponseBody
@Controller
@CrossOrigin
@RequestMapping("/api/Request")
public class RequestController {

    @Autowired
    DeployService deployService;




    @PostMapping("/sharedRequest")
    public Result sharedRequest(@RequestBody Request request){

        RequestService requestService = new RequestService();
        requestService.deployService = deployService;

        return requestService.shareRequest(request.getUserId(),request.getDataId());
    }

    @PostMapping("/freeRequest")
    public Result freeRequest(@RequestBody Request request){
        RequestService requestService = new RequestService();
        requestService.deployService = deployService;

        return requestService.freeRequest(request.getUserId(),request.getDataId(),request.getMessage());
    }


}
