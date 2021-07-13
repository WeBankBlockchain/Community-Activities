package org.fisco.bcos.scutcloud;

import org.fisco.bcos.scutcloud.controller.DeployController;
import org.fisco.bcos.scutcloud.service.DeployService;
import org.fisco.bcos.scutcloud.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {
         @Autowired
         DeployService deployService;
         @Test
         public void register() throws Exception {
//        DeployService deployService = new DeployService();
//        DeployService deployService = deployService;
//        deployService.initialize();
//        deployService.deployScutCloudAndRecordAddr();

        UserService userService = new UserService();
        userService.deployService = deployService;
        userService.registerScutCloudUser("lj","123","0x5B38Da6a701c568545dCfcB03FcB875f56beddC4");
    }
}
