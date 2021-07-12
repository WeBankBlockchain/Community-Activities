package org.fisco.bcos.scutcloud.service;

import org.fisco.bcos.scutcloud.contract.AccessControl;
import org.fisco.bcos.scutcloud.pojo.Data;
import org.fisco.bcos.scutcloud.pojo.Data_new;
import org.fisco.bcos.scutcloud.result.Result;
import org.fisco.bcos.scutcloud.result.ResultFactory;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple7;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple8;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DataService {

    @Autowired
    public DeployService deployService;

    public Result createScutCloudData(BigInteger _id, BigInteger credit, List<BigInteger> not, String dataName, String dataDescription, String address, String type) {
        try {
            String contractAddress = deployService.loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractAddress, deployService.client, deployService.cryptoKeyPair);//get contract
            boolean res = accessControl.isExitDataName(dataName, _id);
            //_id should auto product
            List<BigInteger> temp = new ArrayList<>();
            //TransactionReceipt receipt = accessControl.createData(_id, credit, not, dataName, dataDescription, address, type);
            TransactionReceipt receipt = accessControl.createData(_id, credit, temp, dataName, dataDescription, address, type);
            List<AccessControl.NewDataEventResponse> responses = accessControl.getNewDataEvents(receipt);

            if (!responses.isEmpty()) {
                if (!res) {
                    System.out.println("create data success");
                    System.out.println(responses);
                    return ResultFactory.buildSuccessResult("create data success");
                } else{
                    System.out.println("data is already exited");
                    return ResultFactory.buildFailResult("data is already exited");
                }
            } else {
                System.out.println("common fail");
                return ResultFactory.buildFailResult("common fail");
            }
        } catch (Exception e) {
            deployService.logger.error("createScutCloudData exception,error message is {}", e.getMessage());
            System.out.println("create ScutCloud data failed,error is " + e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

    public Result alterScutCloudData(String userName,BigInteger _id, BigInteger _credit, BigInteger downloadTimes, String dataName, String dataDescription, String address, String type) {
        try {
            String contractaddress = deployService.loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, deployService.client, deployService.cryptoKeyPair);
            boolean res = accessControl.isExitDataName(dataName, _id);
            List<BigInteger> not = new ArrayList<>();
            TransactionReceipt receipt = accessControl.alterData(_id, _credit, dataName, dataDescription, address, type, not);
            List<AccessControl.AlterDataEventResponse> responses = accessControl.getAlterDataEvents(receipt);

            if (!responses.isEmpty()) {
                if (res) {
                    System.out.println("dataName is exited");
                    return ResultFactory.buildFailResult("dataName is exited");
                } else {
                    System.out.println("alterScutcloudData success");
                    return ResultFactory.buildSuccessResult("alterScutcloudData success");
                }
            } else {
                System.out.println("common fail");
                return ResultFactory.buildFailResult("common fail");
            }
        } catch (Exception e) {
            deployService.logger.error("alterScutCloudData exception,error message is {}", e.getMessage());
            System.out.println("alter ScutCloud data failed,error is " + e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }
    public BigInteger getDatalength()  {
        BigInteger res = new BigInteger("0");
        try{
        String contractaddress = deployService.loadScutCloudAddr();
        AccessControl accessControl = AccessControl.load(contractaddress, deployService.client, deployService.cryptoKeyPair);
        //res = Integer.getInteger(accessControl.getDataLength().toString());
         res = accessControl.getDataLength();

        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }
    public Result getScutCloudDataLength() {//tool method ,to help getdatas iterator
        BigInteger res = new BigInteger("0");
        try {
            String contractaddress = deployService.loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, deployService.client, deployService.cryptoKeyPair);
            //res = Integer.getInteger(accessControl.getDataLength().toString());
            res = accessControl.getDataLength();
            return ResultFactory.buildSuccessResult(res);


        } catch (Exception e) {
            deployService.logger.error("getScutCloudDataLength exception,error message is {}", e.getMessage());
            System.out.println("get ScutCloud dataslength failed,error is " + e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());
        }

    }

    public List<Data_new> getdatas() {
        List<Tuple7<BigInteger, BigInteger, BigInteger, String, String, String, String>> res = new ArrayList<>();
        List<Data_new> final_res = new ArrayList<>();
        try {
            String contractaddress = deployService.loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, deployService.client, deployService.cryptoKeyPair);
            for (int i = 0; i < Integer.parseInt(getDatalength().toString()); i++) {
                res.add(accessControl.datas(new BigInteger(String.valueOf(i))));
            }
            for(int i = 0 ; i < res.size() ; i++) {//maybe the id should -1 //这里等等还要换
                Data_new datanew = new Data_new(accessControl.users(accessControl.fdataToOwner(res.get(i).getValue1())).getValue3()
                        ,res.get(i).getValue1()
                        ,res.get(i).getValue2()
                        ,res.get(i).getValue3()
                        ,res.get(i).getValue4()
                        ,res.get(i).getValue5()
                        ,res.get(i).getValue6()
                        ,res.get(i).getValue7());
                final_res.add(datanew);
            }
        } catch (Exception e) {
            deployService.logger.error("getScutCloudData exception,error message is {}", e.getMessage());
            System.out.println("get ScutCloud datas failed,error is " + e.getMessage());
            return null;
        }

        return final_res;
    }
    public List<Data_new> getdata(String dataName,String dataType) throws Exception {
        List<Tuple7<BigInteger, BigInteger, BigInteger, String, String, String, String>> res = new ArrayList<>();
        List<Data_new> final_res = new ArrayList<>();
        String contractaddress = deployService.loadScutCloudAddr();
        AccessControl accessControl = AccessControl.load(contractaddress, deployService.client, deployService.cryptoKeyPair);
        for (int i = 0; i < Integer.parseInt(getDatalength().toString()); i++) {
            if(dataName !=null) {
        System.out.println(accessControl.datas(new BigInteger(String.valueOf(i))).getValue4());
                if(accessControl.datas(new BigInteger(String.valueOf(i))).getValue4().equals(dataName))
                    if(dataType == null)
                        res.add(accessControl.datas(new BigInteger(String.valueOf(i))));
                    else{
                        if(accessControl.datas(new BigInteger(String.valueOf(i))).getValue7().equals(dataType))
                            res.add(accessControl.datas(new BigInteger(String.valueOf(i))));
                    }
            }
            else{
                if(dataType == null)
                    res.add(accessControl.datas(new BigInteger(String.valueOf(i))));
                else{
                    if(accessControl.datas(new BigInteger(String.valueOf(i))).getValue7().equals(dataType))
                        res.add(accessControl.datas(new BigInteger(String.valueOf(i))));
                }
            }
        }
        for(int i = 0 ; i < res.size() ; i++) {//maybe the id should -1 //这里等等还要换
            Data_new temp = new Data_new(accessControl.users(accessControl.fdataToOwner(res.get(i).getValue1())).getValue3()
                    ,res.get(i).getValue1()
                    ,res.get(i).getValue2()
                    ,res.get(i).getValue3()
                    ,res.get(i).getValue4()
                    ,res.get(i).getValue5()
                    ,res.get(i).getValue6()
                    ,res.get(i).getValue7());
            final_res.add(temp);
        }
        return final_res;

    }
    public List<Data_new> getSelfData(BigInteger userId) throws Exception {
        String contractaddress = deployService.loadScutCloudAddr();
        AccessControl accessControl = AccessControl.load(contractaddress, deployService.client, deployService.cryptoKeyPair);
        List<Tuple7<BigInteger, BigInteger, BigInteger, String, String, String, String>> res = new ArrayList<>();
        List<Data_new> final_res = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(getDatalength().toString()); i++) {
            //if(accessControl.datas(new BigInteger(String.valueOf(i))).getValue1().equals(userId))//update data_to_owner
            if(accessControl.fdataToOwner(accessControl.datas(new BigInteger(String.valueOf(i))).getValue1()).equals(userId))

                res.add(accessControl.datas(new BigInteger(String.valueOf(i))));

        }
        for(int i = 0 ; i < res.size() ; i++) {//maybe the id should -1 //这里等等还要换
            Data_new temp = new Data_new(
                     accessControl.users(userId).getValue3()
                    ,res.get(i).getValue1()
                    ,res.get(i).getValue2()
                    ,res.get(i).getValue3()
                    ,res.get(i).getValue4()
                    ,res.get(i).getValue5()
                    ,res.get(i).getValue6()
                    ,res.get(i).getValue7());
            final_res.add(temp);
        }
//        System.out.println(final_res.get(1).getCredit());
//        System.out.println(final_res.get(1).getId());
        return final_res;
    }
    public Data getDataById(BigInteger dataId) throws Exception {
        String contractaddress = deployService.loadScutCloudAddr();
        AccessControl accessControl = AccessControl.load(contractaddress, deployService.client, deployService.cryptoKeyPair);
        Tuple7<BigInteger, BigInteger, BigInteger, String, String, String, String> res;
        Data temp = null;
        if(accessControl.datas(dataId) != null)
        {
             res = accessControl.datas(dataId);
             temp = new Data(
                     res.getValue1()
                    ,res.getValue2()
                    ,res.getValue3()
                    ,res.getValue4()
                    ,res.getValue5()
                    ,res.getValue6()
                    ,res.getValue7());
        }
        return temp;
    }
}
