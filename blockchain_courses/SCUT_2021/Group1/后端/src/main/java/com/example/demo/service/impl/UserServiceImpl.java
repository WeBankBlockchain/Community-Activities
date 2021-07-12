package com.example.demo.service.impl;

import com.example.demo.ApplicationContextUtil;
import com.example.demo.client.ContractClient;
import com.example.demo.contract.TestContract;
import com.example.demo.pojo.LoginRes;
import com.example.demo.pojo.Result;
import com.example.demo.service.UserService;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    ContractClient contractClient = ApplicationContextUtil.getBean(ContractClient.class);

    @Override
    public Result signup(String user_id, String password, int user_type, String port_name) {
        try{
            String contractAddress = contractClient.loadContractAddr();
            TestContract testContract = TestContract.load(contractAddress,contractClient.client,contractClient.cryptoKeyPair);

            if(user_type!=1) { //if not port
                TransactionReceipt receipt = testContract.register(user_id, password, BigInteger.valueOf(user_type));
                List<TestContract.RegisterEventEventResponse> response = testContract.getRegisterEventEvents(receipt);

                if (!response.isEmpty()) {
                    if (response.get(0).ret) {
                        System.out.println("signup ok");
                        return Result.OK().data(response.get(0).res).build();
                    } else {
                        System.out.println("signup fail");
                        return Result.BAD().data(response.get(0).res).build();
                    }
                } else {
                    System.out.println("signup empty response list");
                    return Result.BAD().data("empty response list").build();
                }
            } else {
                TransactionReceipt receipt = testContract.registerPort(user_id,password,port_name);
                List<TestContract.RegisterPortEventEventResponse> responseList = testContract.getRegisterPortEventEvents(receipt);

                if (!responseList.isEmpty()) {
                    if (responseList.get(0).ret) {
                        System.out.println("signup ok");
                        System.out.println(port_name);
                        return Result.OK().data(responseList.get(0).res).build();
                    } else {
                        System.out.println("signup fail");
                        return Result.BAD().data(responseList.get(0).res).build();
                    }
                } else {
                    System.out.println("signup empty response list");
                    return Result.BAD().data("empty response list").build();
                }
            }
        }catch (Exception e) {
            System.out.printf("signup meet exception: %s", e.getMessage());
            return Result.BAD().data("exception happened").build();
        }
    }

    @Override
    public Result signin(String user_id, String password) {
        try {
            String contractAddress = contractClient.loadContractAddr();
            TestContract testContract = TestContract.load(contractAddress,contractClient.client,contractClient.cryptoKeyPair);

            TransactionReceipt receipt = testContract.login(user_id,password);
            List<TestContract.LoginEventEventResponse> responseList = testContract.getLoginEventEvents(receipt);

            if(!responseList.isEmpty()){
                if(responseList.get(0).ret){
                    System.out.println("signin ok");
                    System.out.println(responseList.get(0).token);
                    LoginRes loginRes = new LoginRes(responseList.get(0).token, responseList.get(0).user_type);
                    return Result.OK().data(loginRes).build();
                }else{
                    System.out.println("signin fail");
                    return Result.BAD().data(responseList.get(0).res).build();
                }
            }else{
                System.out.println("signin empty response list");
                return Result.BAD().data("empty response list").build();
            }
        }catch (Exception e){
            System.out.printf("signin meet exception: %s", e.getMessage());
            return Result.BAD().data("exception happened").build();
        }
    }
}
