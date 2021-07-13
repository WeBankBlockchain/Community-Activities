package com.example.demo.service.impl;

import com.example.demo.ApplicationContextUtil;
import com.example.demo.client.ContractClient;
import com.example.demo.contract.TestContract;
import com.example.demo.pojo.Result;
import com.example.demo.service.ChainService;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ChainService")
public class ChainServiceImpl implements ChainService {
    @Autowired
    ContractClient contractClient = ApplicationContextUtil.getBean(ContractClient.class);
    @Override
    public Result genId(String user_id, String token) {
        try{
            String contractAddress = contractClient.loadContractAddr();
            TestContract testContract = TestContract.load(contractAddress,contractClient.client,contractClient.cryptoKeyPair);

            TransactionReceipt receipt = testContract.genId();
            List<TestContract.GenIdEventEventResponse> responseList = testContract.getGenIdEventEvents(receipt);
            if(!responseList.isEmpty()){
                if(responseList.get(0).ret){

                    String id = responseList.get(0).id;
                    TransactionReceipt receipt1 = testContract.bind2(id,user_id,token);
                    List<TestContract.Bind2EventEventResponse> responseList1 = testContract.getBind2EventEvents(receipt1);

                    if(!responseList1.isEmpty()){
                        if(responseList1.get(0).ret){
                            System.out.println("genId and bind2 ok");
                            return Result.OK().data(id).build();
                        }else{
                            System.out.println("bind2 fail");
                            return Result.BAD().data(responseList1.get(0).res).build();
                        }
                    }else{
                        System.out.println("bind2 empty response");
                        return Result.BAD().data("empty response list").build();
                    }
                }else{
                    System.out.println("genId fail");
                    return Result.BAD().data(responseList.get(0).res).build();
                }
            }else{
                System.out.println("genId empty response list");
                return Result.BAD().data("empty response list").build();
            }
        }catch(Exception e){
            System.out.printf("genId/bind2 meet exception: %s", e.getMessage());
            return Result.BAD().data("exception happened").build();
        }
    }

    @Override
    public Result initHead(String user_id, String id, String time, String location, String token) {
        try{
            String contractAddress = contractClient.loadContractAddr();
            TestContract testContract = TestContract.load(contractAddress,contractClient.client,contractClient.cryptoKeyPair);

            Tuple2<Boolean,String> tuple2 = testContract.isBind(id,user_id);
            if(tuple2.getValue1()){
                Tuple2<Boolean,String> tuple21 = testContract.isInit(id);
                if(!tuple21.getValue1()){
                    TransactionReceipt receipt = testContract.initHead(id,time,location,user_id,token);
                    List<TestContract.InitHeadEventEventResponse> responseList = testContract.getInitHeadEventEvents(receipt);
                    if(!responseList.isEmpty()){
                        if(responseList.get(0).ret){
                            System.out.println("initHead ok");
                            return Result.OK().data(responseList.get(0).res).build();
                        }else{
                            System.out.println("initHead fail");
                            return Result.BAD().data(responseList.get(0).res).build();
                        }
                    }else{
                        System.out.println("initHead empty response list");
                        return Result.BAD().data("empty response list").build();
                    }
                }else{
                    System.out.println("head is inited");
                    return Result.BAD().data(tuple21.getValue2()).build();
                }
            }else{
                System.out.println("not your head");
                return Result.BAD().data(tuple2.getValue2()).build();
            }
        } catch (Exception e){
            System.out.printf("isBind/isInit/initHead meet exception: %s", e.getMessage());
            return Result.BAD().data("exception happened").build();
        }
    }

    @Override
    public Result giveRight(String from, String to, String id, String token) {
        try{
            String contractAddress = contractClient.loadContractAddr();
            TestContract testContract = TestContract.load(contractAddress,contractClient.client,contractClient.cryptoKeyPair);

            Tuple2<Boolean,String> tuple2 = testContract.isBind(id,from);
            if(tuple2.getValue1()){
                //System.out.println("is bind");
                TransactionReceipt receipt = testContract.giveRight(id,to,token);
                List<TestContract.GiveRightEventEventResponse> responseList = testContract.getGiveRightEventEvents(receipt);
                if(!responseList.isEmpty()){
                    if(responseList.get(0).ret){
                        System.out.println("giveRight ok");
                        return Result.OK().data(responseList.get(0).res).build();
                    }else{
                        System.out.println("giveRight fail");
                        return Result.BAD().data(responseList.get(0).res).build();
                    }
                }else{
                    System.out.println("giveRight empty response list");
                    return Result.BAD().data("empty response list").build();
                }
            }else{
                System.out.println("not your id");
                return Result.BAD().data(tuple2.getValue2()).build();
            }
        }catch (Exception e){
            System.out.printf("isBind/giveRight meet exception: %s", e.getMessage());
            return Result.BAD().data("exception happened").build();
        }
    }

    @Override
    public Result addNode(String user_id, String id, String in_time, String out_time, String token) {
        try{
            String contractAddress = contractClient.loadContractAddr();
            TestContract testContract = TestContract.load(contractAddress,contractClient.client,contractClient.cryptoKeyPair);

            Tuple2<Boolean,String> tuple2 = testContract.hasRight(id,user_id);
            if(tuple2.getValue1()){
                TransactionReceipt receipt = testContract.addNode(id,user_id,in_time,out_time,token);
                List<TestContract.AddNodeEventEventResponse> responseList = testContract.getAddNodeEventEvents(receipt);
                if(!responseList.isEmpty()){
                    if(responseList.get(0).ret){
                        System.out.println("addNode ok");
                        return Result.OK().data(responseList.get(0).res).build();
                    }else {
                        System.out.println("addNode fail");
                        return Result.BAD().data(responseList.get(0).res).build();
                    }
                }else{
                    System.out.println("addNode empty response list");
                    return Result.BAD().data("empty response list").build();
                }
            }else {
                System.out.println("not has right");
                return Result.BAD().data("not has right").build();
            }
        }catch (Exception e){
            System.out.printf("hasRight/addNode meet exception: %s", e.getMessage());
            return Result.BAD().data("exception happened").build();
        }
    }
}
