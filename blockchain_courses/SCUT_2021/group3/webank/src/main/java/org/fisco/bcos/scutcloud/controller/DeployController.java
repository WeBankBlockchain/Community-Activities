package org.fisco.bcos.scutcloud.controller;

import com.google.gson.JsonObject;
import org.fisco.bcos.scutcloud.service.DeployService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@Controller
@CrossOrigin
@RequestMapping("/Deploy")
public  class DeployController {
    public DeployService deployService ;
    @GetMapping("/init")
    public JsonObject init() throws Exception {
        deployService = new DeployService();
        deployService.initialize();
        deployService.deployScutCloudAndRecordAddr();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("status","success");
        return jsonObject;
    }
    @Bean
    public DeployService deployService() throws Exception {
        deployService = new DeployService();
        deployService.initialize();
        deployService.deployScutCloudAndRecordAddr();
        return deployService;
    }

}
