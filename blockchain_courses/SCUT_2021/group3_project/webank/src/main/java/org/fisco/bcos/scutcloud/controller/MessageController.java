package org.fisco.bcos.scutcloud.controller;

import com.google.gson.JsonObject;
import org.fisco.bcos.scutcloud.pojo.Message;
import org.fisco.bcos.scutcloud.pojo.User;
import org.fisco.bcos.scutcloud.result.Result;
import org.fisco.bcos.scutcloud.result.ResultFactory;
import org.fisco.bcos.scutcloud.service.DeployService;
import org.fisco.bcos.scutcloud.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@ResponseBody
@Controller
@CrossOrigin
@RequestMapping("/api/Message")
public class MessageController {

    @Autowired
    DeployService deployService;

    @PostMapping("/getAllMessage_res")//接收方
    public Result getAllMessage1(@RequestBody User user){
        MessageService messageService = new MessageService();
        messageService.deployService = deployService;

        return messageService.getMessage_new_res(user.get_id());
    }

    @PostMapping("/getAllMessage_req")//发送方
    public Result getAllMessage2(@RequestBody User user){
        MessageService messageService = new MessageService();
        messageService.deployService = deployService;

        return messageService.getMessage_new_req(user.get_id());
    }
    @PostMapping("/getAllMessage_finished")//已处理
    public Result getAllMessage3(@RequestBody User user){
        MessageService messageService = new MessageService();
        messageService.deployService = deployService;

        return messageService.getMessage_new_finished(user.get_id());
    }

    @GetMapping("/getLength")
    public Result getLength(){
        MessageService messageService = new MessageService();
        messageService.deployService = deployService;

        BigInteger res = messageService.getMessageLength();

        return ResultFactory.buildSuccessResult(res);
    }

    @PostMapping("/approve")
    public Result approveRequest(@RequestBody Message message){
        MessageService messageService = new MessageService();
        messageService.deployService = deployService;

        return messageService.freeApprove(message.getFreeId());

    }

    @PostMapping("/deny")
    public Result denyRequest(@RequestBody Message message){
        MessageService messageService = new MessageService();
        messageService.deployService = deployService;

        return messageService.freeDenied(message.getFreeId());

    }
}
