package org.fisco.bcos.scutcloud.controller;

import com.google.gson.JsonObject;
import org.fisco.bcos.scutcloud.pojo.User;
import org.fisco.bcos.scutcloud.result.Result;
import org.fisco.bcos.scutcloud.result.ResultFactory;
import org.fisco.bcos.scutcloud.service.DeployService;
import org.fisco.bcos.scutcloud.service.UserService;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple6;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@ResponseBody
@Controller
@CrossOrigin
@RequestMapping("/api/User")
public class UserController {

    //UserService userService ;

    @Autowired
    DeployService deployService;

    @PostMapping("/register")

    public Result register(@RequestBody User user){
        UserService userService = new UserService();
        userService.deployService = deployService;

         return userService.registerScutCloudUser(user.getUserName(),user.getPwd(),user.getAddress());

    }

    @PostMapping("/alter")
    public Result alterUser(@RequestBody User user){
        UserService userService = new UserService();
        userService.deployService =  deployService;

        return userService.alterScutCloudUser(user.get_id(),user.getUserName(),user.getPwd(),user.getDescription(), user.getAddress());

    }

    @GetMapping("/getLength")
    public Result getLength(){
        UserService userService = new UserService();
        userService.deployService =  deployService;

        return userService.getUserLength();
    }
    @PostMapping("/Transfer")
    public Result transfer(@RequestBody User user) throws Exception {
        UserService userService = new UserService();
        userService.deployService =  deployService;

        return userService.transfer(user.get_id(), user.getUserName(),user.getCredit());
    }
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        UserService userService = new UserService();
        userService.deployService =  deployService;
        boolean isValid = userService.login(user.getUserName(),user.getPwd());
        if(isValid)
        {
            Tuple6<BigInteger, BigInteger, String, String, String, String> temp = userService.getUser(user.getUserName());
            User res = new User(temp.getValue1(),Integer.parseInt(temp.getValue2().toString()),temp.getValue3(),temp.getValue4(),temp.getValue5(),temp.getValue6());
            return ResultFactory.buildSuccessResult("login successfully",res);
        }
        else
        {
            return ResultFactory.buildFailResult("login failed");
        }
    }

    @PostMapping("/getUser")
    public Result getUser(@RequestBody User user){
        UserService userService = new UserService();
        userService.deployService = deployService;


        Tuple6<BigInteger, BigInteger, String, String, String, String> temp = userService.getUser(user.getUserName());
        User res = new User(temp.getValue1(),Integer.parseInt(temp.getValue2().toString()),temp.getValue3(),temp.getValue4(),temp.getValue5(),temp.getValue6());
        return ResultFactory.buildSuccessResult(res);
    }
}
