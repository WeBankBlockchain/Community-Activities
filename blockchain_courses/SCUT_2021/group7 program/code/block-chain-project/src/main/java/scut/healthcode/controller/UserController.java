package scut.healthcode.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import scut.healthcode.entity.UserInfo;
import scut.healthcode.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/upload")
    public HashMap<String, Object> upload(@RequestBody UserInfo userInfo) {
        try {
            logger.info("accept UserInfo");
            logger.info(userInfo.toString());

            return userService.upload(userInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/generateHealthcode")
    public HashMap<String, Object> generate(@RequestBody Map params) {
        logger.info("accept generateHealthcode");
        logger.info(params.toString());
        return userService.generateHealthcode(params.get("id").toString());

    }

    @PostMapping("/checkIsHealthy")
    public HashMap<String, Object> isHealth(@RequestBody Map params) {
        logger.info("accept checkIsHealthy");
        logger.info(params.toString());
        HashMap h = userService.isHealth(params.get("userHealthHash").toString());
        return h;
    }
}
