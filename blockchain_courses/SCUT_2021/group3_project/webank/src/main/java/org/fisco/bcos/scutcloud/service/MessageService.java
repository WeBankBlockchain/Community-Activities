package org.fisco.bcos.scutcloud.service;

import org.fisco.bcos.scutcloud.contract.AccessControl;
import org.fisco.bcos.scutcloud.pojo.Message;
import org.fisco.bcos.scutcloud.result.Result;
import org.fisco.bcos.scutcloud.result.ResultFactory;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple5;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MessageService {

    @Autowired
    public DeployService deployService;

    //approve request free
    public Result freeApprove(BigInteger freeId) {
        try {
            String contractaddress = deployService.loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, deployService.client, deployService.cryptoKeyPair);
            TransactionReceipt receipt = accessControl.freeApproved(freeId);
            List<AccessControl.FreeApprovedEventResponse> responses = accessControl.getFreeApprovedEvents(receipt);
            if (!responses.isEmpty()) {
                System.out.println("freeApprove success");
                return ResultFactory.buildSuccessResult("freeApprove success");
            } else {
                System.out.println("common fail");
                return ResultFactory.buildFailResult("common fail");
            }
        } catch (Exception e) {
            deployService.logger.error("freeApprove exception,error message is {}", e.getMessage());
            System.out.println("freeApprove failed,error is " + e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

    public Result freeDenied(BigInteger freeId) {
        try {
            String contractaddress = deployService.loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, deployService.client, deployService.cryptoKeyPair);
            TransactionReceipt receipt = accessControl.freeDenied(freeId);
            List<AccessControl.FreeDeniedEventResponse> responses = accessControl.getFreeDeniedEvents(receipt);
            if (!responses.isEmpty()) {
                System.out.println("freeDenied success");
                return ResultFactory.buildSuccessResult("freeApprove success");
            } else {
                System.out.println("common fail");
                return ResultFactory.buildFailResult("common fail");
            }
        } catch (Exception e) {
            deployService.logger.error("freeDenied exception,error message is {}", e.getMessage());
            System.out.println("freeDenied failed,error is " + e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

    public BigInteger getMessageLength() {
        BigInteger res = new BigInteger("0");
        try {
            String contractaddress = deployService.loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, deployService.client, deployService.cryptoKeyPair);
            res = accessControl.getMsgLength();
        } catch (Exception e) {
            deployService.logger.error("getMessageLength exception,error message is {}", e.getMessage());
            System.out.println("getMessage length failed,error is " + e.getMessage());
        }
        return res;
    }
    public List<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, String>> getMessage(BigInteger userId){
        List<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, String>> res = new ArrayList<>();
        try{
            String contractaddress = deployService.loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, deployService.client, deployService.cryptoKeyPair);
            for (int i = 0; i < Integer.parseInt(getMessageLength().toString()); i++) {
                if(accessControl.msgs(new BigInteger(String.valueOf(i))).getValue1() == userId
                || accessControl.msgs(new BigInteger(String.valueOf(i))).getValue2() == userId)
                    res.add(accessControl.msgs(new BigInteger(String.valueOf(i))));
            }
        } catch (Exception e){
            deployService.logger.error("getMessage exception,error message is {}", e.getMessage());
            System.out.println("getMessage failed,error is " + e.getMessage());
        }
        return res;
    }
    public Result getMessage_new_req(BigInteger userId) {
        List<Message> res = new ArrayList<>();
        try{
            String contractaddress = deployService.loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, deployService.client, deployService.cryptoKeyPair);
            int n = (Integer.parseInt(getMessageLength().toString()));
            for(int i = 0 ; i < n ; i++){
                int current =Integer.parseInt( accessControl.msgs(new BigInteger(String.valueOf(i))).getValue4().toString());
                if(accessControl.msgs(new BigInteger(String.valueOf(i))).getValue1().equals(userId)
                        && current == 0){
                    Message temp = new Message(
                            accessControl.msgs(new BigInteger(String.valueOf(i))).getValue1(),
                            accessControl.msgs(new BigInteger(String.valueOf(i))).getValue2(),
                            BigInteger.valueOf(i),
                            accessControl.msgs(new BigInteger(String.valueOf(i))).getValue3(),
                            accessControl.msgs(new BigInteger(String.valueOf(i))).getValue4(),
                            accessControl.msgs(new BigInteger(String.valueOf(i))).getValue5()
                    );
                    res.add(temp);
                }
            }
            return ResultFactory.buildSuccessResult(res);
        } catch (Exception e){
            deployService.logger.error("getMessage exception,error message is {}", e.getMessage());
            System.out.println("getMessage failed,error is " + e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }
    public Result getMessage_new_res(BigInteger userId) {
        List<Message> res = new ArrayList<>();
        try{
            String contractaddress = deployService.loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, deployService.client, deployService.cryptoKeyPair);
            int n = (Integer.parseInt(getMessageLength().toString()));

            for(int i = 0 ; i < n ; i++){
                int current =Integer.parseInt( accessControl.msgs(new BigInteger(String.valueOf(i))).getValue4().toString());
                if(accessControl.msgs(new BigInteger(String.valueOf(i))).getValue2().equals(userId)
                        && current == 0){
                    Message temp = new Message(
                            accessControl.msgs(new BigInteger(String.valueOf(i))).getValue1(),
                            accessControl.msgs(new BigInteger(String.valueOf(i))).getValue2(),
                            BigInteger.valueOf(i),
                            accessControl.msgs(new BigInteger(String.valueOf(i))).getValue3(),
                            accessControl.msgs(new BigInteger(String.valueOf(i))).getValue4(),
                            accessControl.msgs(new BigInteger(String.valueOf(i))).getValue5()
                    );
                    res.add(temp);
                }
            }
            return ResultFactory.buildSuccessResult(res);
        } catch (Exception e){
            deployService.logger.error("getMessage exception,error message is {}", e.getMessage());
            System.out.println("getMessage failed,error is " + e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }
    public Result getMessage_new_finished(BigInteger userId) {
        List<Message> res = new ArrayList<>();
        try{
            String contractaddress = deployService.loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, deployService.client, deployService.cryptoKeyPair);
            int n = (Integer.parseInt(getMessageLength().toString()));
            for(int i = 0 ; i < n ; i++){
               int current =Integer.parseInt( accessControl.msgs(new BigInteger(String.valueOf(i))).getValue4().toString());
                if((accessControl.msgs(new BigInteger(String.valueOf(i))).getValue1().equals(userId)
                        || accessControl.msgs(new BigInteger(String.valueOf(i))).getValue2().equals(userId))
                        && current != 0){
                    Message temp = new Message(
                            accessControl.msgs(new BigInteger(String.valueOf(i))).getValue1(),
                            accessControl.msgs(new BigInteger(String.valueOf(i))).getValue2(),
                            BigInteger.valueOf(i),
                            accessControl.msgs(new BigInteger(String.valueOf(i))).getValue3(),
                            accessControl.msgs(new BigInteger(String.valueOf(i))).getValue4(),
                            accessControl.msgs(new BigInteger(String.valueOf(i))).getValue5()
                    );
                    res.add(temp);
                }
            }
            return ResultFactory.buildSuccessResult(res);
        } catch (Exception e){
            deployService.logger.error("getMessage exception,error message is {}", e.getMessage());
            System.out.println("getMessage failed,error is " + e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

}
