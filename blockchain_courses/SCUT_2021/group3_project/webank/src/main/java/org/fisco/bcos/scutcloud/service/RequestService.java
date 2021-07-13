package org.fisco.bcos.scutcloud.service;

import org.fisco.bcos.scutcloud.contract.AccessControl;
import org.fisco.bcos.scutcloud.controller.DataController;
import org.fisco.bcos.scutcloud.pojo.Data;
import org.fisco.bcos.scutcloud.result.Result;
import org.fisco.bcos.scutcloud.result.ResultFactory;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.math.BigInteger;
import java.util.List;

public class RequestService {


    @Autowired
    public DeployService deployService;

    //control section
    //check user's credit
    public boolean checkCredit(BigInteger userId, BigInteger dataId) {
        boolean res = false;
        try {
            String contractaddress = deployService.loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, deployService.client, deployService.cryptoKeyPair);
            res = accessControl.checkCredit(userId, dataId);
        } catch (Exception e) {
            deployService.logger.error("checkCredit exception,error message is {}", e.getMessage());
            System.out.println("checkCredit failed,error is " + e.getMessage());
        }
        return res;
    }

    //check the user's is in the list of can request
    //false is can request
    public boolean checkList(BigInteger userId, BigInteger dataId) {
        boolean res = false;
        try {
            String contractaddress = deployService.loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, deployService.client, deployService.cryptoKeyPair);
            res = accessControl.checkList(dataId, userId);
        } catch (Exception e) {
            deployService.logger.error("checkList exception,error message is {}", e.getMessage());
            System.out.println("checkList failed,error is " + e.getMessage());
        }
        return res;
    }

    public Result shareRequest(BigInteger userId, BigInteger dataId) {
        try {
            DataService dataService = new DataService();
            dataService.deployService = deployService;
            String contractaddress = deployService.loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, deployService.client, deployService.cryptoKeyPair);

            boolean res1 = checkCredit(userId, dataId);
            boolean res3 = false;
            System.out.println(accessControl.getAccessible(userId).size());
            for(int i = 0 ; i < accessControl.getAccessible(userId).size() ;i++){
                if(accessControl.getAccessible(userId).get(i).equals(dataId))
                    res3 = true;
            }
            if(res3 == true) {
                Data data = dataService.getDataById(dataId);
                return ResultFactory.buildSuccessResult("shareRequest success", data.getAddress());
                }
            else {
                if (res1) {
                    boolean res2 = checkList(userId, dataId);
                    if (!res2) {
                        TransactionReceipt receipt = accessControl.shareRequest(userId, dataId);
                        List<AccessControl.RequestApprovedEventResponse> responses = accessControl.getRequestApprovedEvents(receipt);
                        if (!responses.isEmpty()) {
                            System.out.println("shareRequest success");
                            Data data = dataService.getDataById(dataId);
                            if (data != null)
                                return ResultFactory.buildSuccessResult("shareRequest success", data.getAddress());
                            else
                                return ResultFactory.buildFailResult("can't find the data!");
                        } else {
                            System.out.println("common fail");
                            return ResultFactory.buildFailResult("common fail");
                        }
                    } else {
                        System.out.println("user's can't be allowed to request this data");
                        return ResultFactory.buildFailResult("user's can't be allowed to request this data");

                    }
                } else {
                    System.out.println("user's credit is not enough");
                    return ResultFactory.buildFailResult("user's credit is not enough");

                }
            }
        } catch (Exception e) {
            deployService.logger.error("shareRequest exception,error message is {}", e.getMessage());
            System.out.println("shareRequest failed,error is " + e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

    public Result freeRequest(BigInteger userId, BigInteger dataId, String message) {
        boolean res_ = false;
        try {
            String contractaddress = deployService.loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, deployService.client, deployService.cryptoKeyPair);
            boolean res = checkList(userId, dataId);
            if (!res) {
                TransactionReceipt receipt = accessControl.freeRequest(userId, dataId, message);
                List<AccessControl.FreeRequestEventResponse> responses = accessControl.getFreeRequestEvents(receipt);
                if (!responses.isEmpty()) {
                    System.out.println("freeRequest success");
                    res_ = true;
                    return ResultFactory.buildSuccessResult("freeRequest success");

                } else {
                    System.out.println("common fail");
                    return ResultFactory.buildFailResult("common fail");
                }
            } else {
                System.out.println("user can't be allowed to request this data");
                return ResultFactory.buildFailResult("user's can't be allowed to request this data");

            }
        } catch (Exception e) {
            deployService.logger.error("freeRequest exception,error message is {}", e.getMessage());
            System.out.println("freeRequest failed,error is " + e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());

        }
    }

}
