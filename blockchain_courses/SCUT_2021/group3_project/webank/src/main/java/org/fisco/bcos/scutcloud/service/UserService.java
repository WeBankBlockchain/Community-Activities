package org.fisco.bcos.scutcloud.service;

import org.fisco.bcos.scutcloud.contract.AccessControl;
import org.fisco.bcos.scutcloud.result.Result;
import org.fisco.bcos.scutcloud.result.ResultFactory;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple6;
import org.fisco.bcos.sdk.model.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;


//@Service
//@Configuration
public class UserService {

   public DeployService deployService;

//    @Bean
//    public DeployService deployService(){
//        @SuppressWarnings("resource")//suppress warning infomation
//        ApplicationContext context =
//                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        deployService.bcosSDK = context.getBean(BcosSDK.class);
//        deployService.client = deployService.bcosSDK.getClient(1);
//        deployService.cryptoKeyPair = deployService.client.getCryptoSuite().createKeyPair();
//        deployService.client.getCryptoSuite().setCryptoKeyPair(deployService.cryptoKeyPair);
//        deployService.logger.debug("create client for group1,account address is" + deployService.cryptoKeyPair.getAddress());
//        return deployService();
//    }

//    public UserService(DeployService deployService){
//        this.deployService = deployService;
//    }

//    public void setDeployService(DeployService deployService) {
//        @SuppressWarnings("resource")//suppress warning infomation
//        ApplicationContext context =
//                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        deployService.bcosSDK = context.getBean(BcosSDK.class);
//        deployService.client = deployService.bcosSDK.getClient(1);
//        deployService.cryptoKeyPair = deployService.client.getCryptoSuite().createKeyPair();
//        deployService.client.getCryptoSuite().setCryptoKeyPair(deployService.cryptoKeyPair);
//        deployService.logger.debug("create client for group1,account address is" + deployService.cryptoKeyPair.getAddress());
//        this.deployService = deployService;
//    }

    public Result registerScutCloudUser(String username, String pwd, String address) {
        try {
            String contractAddress = deployService.loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractAddress, deployService.client, deployService.cryptoKeyPair);//get contract
            boolean res1 = accessControl.isExitDataName(username, new BigInteger("0"));
            boolean res2 = accessControl.isExitUserAddress(address, new BigInteger("0"));

            TransactionReceipt receipt = accessControl.createUser(username, pwd, address);
            List<AccessControl.NewUserEventResponse> responses = accessControl.getNewUserEvents(receipt);
            if (!responses.isEmpty()) {
                if (!res1 && !res2) {
                    System.out.println("register success");
                    return ResultFactory.buildSuccessResult("register success");
                } else if (res1) {
                    System.out.println("username is already exited");
                    return ResultFactory.buildFailResult("username is already exited");
                } else{
                    System.out.println("useraddress is already exited");
                    return ResultFactory.buildFailResult("useraddress is already exited");
                }
            } else {
                return ResultFactory.buildFailResult("responses is Empty");
            }
        } catch (Exception e) {
            deployService.logger.error("registerScutCloudUser exception,error message is {}", e.getMessage());
            System.out.println("register ScutCloud user failed,error is " + e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

    public Result alterScutCloudUser(BigInteger _id, String username, String pwd, String description, String address) {
        try {
            String contractAddress = deployService.loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractAddress, deployService.client, deployService.cryptoKeyPair);
            boolean res1 = accessControl.isExitDataName(username, _id);
            boolean res2 = accessControl.isExitUserAddress(address, _id);
            TransactionReceipt receipt = accessControl.alterUser(_id, username, pwd, description, address);
            List<AccessControl.AlterUserEventResponse> responses = accessControl.getAlterUserEvents(receipt);
            if (!responses.isEmpty()) {
                if (!res1 && !res2) {
                    System.out.println("alter success");
                    return ResultFactory.buildSuccessResult("alter success");
                } else if (res1) {
                    System.out.println("username is already exited");
                    return ResultFactory.buildFailResult("username is already exited");
                } else{
                    System.out.println("useraddress is already exit");
                    return ResultFactory.buildFailResult("useraddress is already exited");
                }
            } else {
                System.out.println("common failed,alter failed");
                return ResultFactory.buildFailResult("common failed,alter failed");
            }
        } catch (Exception e) {
            deployService.logger.error("alterScutCloudUser exception,error message is {}", e.getMessage());
            System.out.println("alter ScutCloud user failed,error is " + e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

    public Result getUserLength() {
        BigInteger res = new BigInteger("0");
        try {
            String contractAddress = deployService.loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractAddress, deployService.client, deployService.cryptoKeyPair);//get contract
            res = accessControl.getUserLength();
            return ResultFactory.buildSuccessResult("chaxunchenggon", res);
        } catch (Exception e) {
            deployService.logger.error("getScutCloudUserLength exception,error message is {}", e.getMessage());
            System.out.println("get ScutCloud user length failed,error is " + e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());
        }

    }
    public BigInteger getLength() throws Exception {
        String contractAddress = deployService.loadScutCloudAddr();
        AccessControl accessControl = AccessControl.load(contractAddress, deployService.client, deployService.cryptoKeyPair);//get contract
        BigInteger res = accessControl.getUserLength();
        return res;
    }

    public Tuple6<BigInteger, BigInteger, String, String, String, String> getUser(String userName){
        Tuple6<BigInteger, BigInteger, String, String, String, String> user = null;
        try{
            String contractAddress = deployService.loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractAddress, deployService.client, deployService.cryptoKeyPair);
            int a =  Integer.parseInt(accessControl.getUserLength().toString());
            for(int i = 1 ; i < a ;i++){
                String temp = String.valueOf(i);
                String t = accessControl.users(new BigInteger(temp)).getValue3();
                String t1 = accessControl.users(new BigInteger(temp)).getValue4();
                String t2 = accessControl.users(new BigInteger(temp)).getValue6();
                if(accessControl.users(new BigInteger(temp)).getValue3().equals(userName)) {

                    user =  accessControl.users(new BigInteger(temp));
                    break;
                }
            }

        } catch (Exception e) {
            deployService.logger.error("getUser exception,error message is {}", e.getMessage());
            System.out.println("get user length failed,error is " + e.getMessage());
        }
        return user;
    }
    public Result transfer(BigInteger userId,String userName,int credit) throws Exception {
        String contractAddress = deployService.loadScutCloudAddr();
        AccessControl accessControl = AccessControl.load(contractAddress, deployService.client, deployService.cryptoKeyPair);
        BigInteger userCredit = accessControl.users(new BigInteger(String.valueOf(userId))).getValue2();

        if(Integer.parseInt(userCredit.toString()) >= credit) {
            //就可以进行积分转移
            TransactionReceipt receipt = accessControl.transIntegral(userId,new BigInteger(String.valueOf(credit)),getUser(userName).getValue1());
            List<AccessControl.TransIntegralEventResponse> responses = accessControl.getTransIntegralEvents(receipt);
            if(!responses.isEmpty()){
                System.out.println("success");
                return ResultFactory.buildSuccessResult("success");
            }
            else{
                System.out.println("common fail");
                return ResultFactory.buildFailResult("common fail");
            }
        }
        else
        {
            System.out.println("积分不够");
            return ResultFactory.buildFailResult("积分不够");
        }


    }
    public boolean login(String userName,String pwd){
        boolean isValid = true;
        try {
            String contractAddress = deployService.loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractAddress, deployService.client, deployService.cryptoKeyPair);
            String targetPwd = getUser(userName).getValue4();
            if(!pwd.equals(targetPwd)){
                isValid = false;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return isValid;
    }


}
