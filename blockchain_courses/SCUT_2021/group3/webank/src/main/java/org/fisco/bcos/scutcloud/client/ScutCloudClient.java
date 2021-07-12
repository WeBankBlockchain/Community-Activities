package org.fisco.bcos.scutcloud.client;
//hello
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

import com.fasterxml.jackson.core.JsonFactory;
import com.google.gson.JsonObject;
import org.fisco.bcos.scutcloud.contract.AccessControl;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple7;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;


public class ScutCloudClient {
    static Logger logger = LoggerFactory.getLogger(ScutCloudClient.class);

    private BcosSDK bcosSDK;
    private Client client;
    private CryptoKeyPair cryptoKeyPair;

    /* read initlai property */
    public void initialize() throws Exception {
        @SuppressWarnings("resource")//suppress warning infomation
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        bcosSDK = context.getBean(BcosSDK.class);
        client = bcosSDK.getClient(1);
        cryptoKeyPair = client.getCryptoSuite().createKeyPair();
        client.getCryptoSuite().setCryptoKeyPair(cryptoKeyPair);
        logger.debug("create client for group1,account address is" + cryptoKeyPair.getAddress());
    }

    /* deploy contract and get contract address */
    public void deployScutCloudAndRecordAddr() {
        try {
            AccessControl accessControl = AccessControl.deploy(client, cryptoKeyPair);
            System.out.println(
                    "deploy ScutCloud sucess,contract address is" + accessControl.getContractAddress());
            recordAssetAddr(accessControl.getContractAddress());
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(" deploy ScutCloud contract failed, error message is  " + e.getMessage());
        }
    }

    /* get contract address */
    public void recordAssetAddr(String address) throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        prop.setProperty("address", address);
        final Resource contractResource = new ClassPathResource("contract.properties");
        FileOutputStream fileOutputStream = new FileOutputStream(contractResource.getFile());
        prop.store(fileOutputStream, "contract address");
    }

    /* load contract address */
    public String loadScutCloudAddr() throws Exception {
        // load Asset contact address from contract.properties
        Properties prop = new Properties();
        final Resource contractResource = new ClassPathResource("contract.properties");
        prop.load(contractResource.getInputStream());

        String contractAddress = prop.getProperty("address");
        if (contractAddress == null || contractAddress.trim().equals("")) {
            throw new Exception(" load ScutCloud contract address failed, please deploy it first. ");
        }
        logger.info(" load ScutCloud address from contract.properties, address is {}", contractAddress);
        return contractAddress;
    }

    /* method : register user */
    public void registerScutCloudUser(String username, String pwd, String address) {
        try {
            String contractAddress = loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractAddress, client, cryptoKeyPair);//get contract
            boolean res1 = accessControl.isExitDataName(username, new BigInteger("0"));
            boolean res2 = accessControl.isExitUserAddress(address, new BigInteger("0"));

            TransactionReceipt receipt = accessControl.createUser(username, pwd, address);
            List<AccessControl.NewUserEventResponse> responses = accessControl.getNewUserEvents(receipt);
            if (!responses.isEmpty()) {
                if (!res1 && !res2) {
                    System.out.println("register success");
                } else if (res1) {
                    System.out.println("username is already exited");
                } else if (res2) {
                    System.out.println("useraddress is already exited");
                }
            } else {
                System.out.println("common fail,register failed");
            }
        } catch (Exception e) {
            logger.error("registerScutCloudUser exception,error message is {}", e.getMessage());
            System.out.println("register ScutCloud user failed,error is " + e.getMessage());
        }
    }

    public void alterScutCloudUser(BigInteger _id, String username, String pwd, String description, String address) {
        try {
            String contractAddress = loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractAddress, client, cryptoKeyPair);
            boolean res1 = accessControl.isExitDataName(username, _id);
            boolean res2 = accessControl.isExitUserAddress(address, _id);
            TransactionReceipt receipt = accessControl.alterUser(_id, username, pwd, description, address);
            List<AccessControl.AlterUserEventResponse> responses = accessControl.getAlterUserEvents(receipt);
            if (!responses.isEmpty()) {
                if (!res1 && !res2) {
                    System.out.println("alter success");
                } else if (res1) {
                    System.out.println("username is already exited");
                } else if (res2) {
                    System.out.println("useraddress is already exit");
                }
            } else {
                System.out.println("common failed,alter failed");
            }
        } catch (Exception e) {
            logger.error("alterScutCloudUser exception,error message is {}", e.getMessage());
            System.out.println("alter ScutCloud user failed,error is " + e.getMessage());
        }

    }

    public BigInteger getUserLength() {
        BigInteger res = new BigInteger("0");
        try {
            String contractAddress = loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractAddress, client, cryptoKeyPair);//get contract
            res = accessControl.getUserLength();
        } catch (Exception e) {
            logger.error("getScutCloudUserLength exception,error message is {}", e.getMessage());
            System.out.println("get ScutCloud user length failed,error is " + e.getMessage());
        }
        return res;
    }

    public void createScutCloudData(BigInteger _id, BigInteger _credit, List<BigInteger> not, String dataName, String dataDescription, String address, String type) {
        try {
            String contractAddress = loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractAddress, client, cryptoKeyPair);//get contract
            boolean res = accessControl.isExitDataName(dataName, _id);
            //_id should auto product
            TransactionReceipt receipt = accessControl.createData(_id, _credit, not, dataName, dataDescription, address, type);
            List<AccessControl.NewDataEventResponse> responses = accessControl.getNewDataEvents(receipt);

            if (!responses.isEmpty()) {
                if (!res) {
                    System.out.println("create data success");
                    System.out.println(responses);
                } else if (res) {
                    System.out.println("data is already exited");
                }
            } else {
                System.out.println("common fail");
            }
        } catch (Exception e) {
            logger.error("createScutCloudData exception,error message is {}", e.getMessage());
            System.out.println("create ScutCloud data failed,error is " + e.getMessage());
        }
    }

    public void alterScutCloudData(BigInteger _id, BigInteger _credit, List<BigInteger> not, String dataName, String dataDescription, String address, String type) {
        try {
            String contractaddress = loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, client, cryptoKeyPair);
            boolean res = accessControl.isExitDataName(dataName, _id);

            TransactionReceipt receipt = accessControl.alterData(_id, _credit, dataName, dataDescription, address, type, not);
            List<AccessControl.AlterDataEventResponse> responses = accessControl.getAlterDataEvents(receipt);

            if (!responses.isEmpty()) {
                if (res) {
                    System.out.println("dataName is exited");
                } else {
                    System.out.println("alterScutcloudData success");
                }
            } else {
                System.out.println("common fail");
            }
        } catch (Exception e) {
            logger.error("alterScutCloudData exception,error message is {}", e.getMessage());
            System.out.println("alter ScutCloud data failed,error is " + e.getMessage());
        }
    }

    public BigInteger getScutCloudDataLength() {//tool method ,to help getdatas iterator
        BigInteger res = new BigInteger("0");
        try {
            String contractaddress = loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, client, cryptoKeyPair);
            //res = Integer.getInteger(accessControl.getDataLength().toString());
            res = accessControl.getDataLength();

        } catch (Exception e) {
            logger.error("getScutCloudDataLength exception,error message is {}", e.getMessage());
            System.out.println("get ScutCloud dataslength failed,error is " + e.getMessage());
        }
        return res;
    }

    public List<Tuple7<BigInteger, BigInteger, BigInteger, String, String, String, String>> getdatas() {
        List<Tuple7<BigInteger, BigInteger, BigInteger, String, String, String, String>> res = new ArrayList<>();
        try {
            String contractaddress = loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, client, cryptoKeyPair);
            for (int i = 0; i < Integer.parseInt(getScutCloudDataLength().toString()); i++) {
                res.add(accessControl.datas(new BigInteger(String.valueOf(i))));
            }
        } catch (Exception e) {
            logger.error("getScutCloudData exception,error message is {}", e.getMessage());
            System.out.println("get ScutCloud datas failed,error is " + e.getMessage());
        }
        return res;
    }

    //control section
    //check user's credit
    public boolean checkCredit(BigInteger userId, BigInteger dataId) {
        boolean res = false;
        try {
            String contractaddress = loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, client, cryptoKeyPair);
            res = accessControl.checkCredit(userId, dataId);
        } catch (Exception e) {
            logger.error("checkCredit exception,error message is {}", e.getMessage());
            System.out.println("checkCredit failed,error is " + e.getMessage());
        }
        return res;
    }

    //check the user's is in the list of can request
    //false is can request
    public boolean checkList(BigInteger userId, BigInteger dataId) {
        boolean res = false;
        try {
            String contractaddress = loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, client, cryptoKeyPair);
            res = accessControl.checkList(dataId, userId);
        } catch (Exception e) {
            logger.error("checkList exception,error message is {}", e.getMessage());
            System.out.println("checkList failed,error is " + e.getMessage());
        }
        return res;
    }

    public void shareRequest(BigInteger userId, BigInteger dataId) {
        try {
            String contractaddress = loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, client, cryptoKeyPair);

            boolean res1 = checkCredit(userId, dataId);
            if (res1) {
                boolean res2 = checkList(userId, dataId);
                if (!res2) {
                    TransactionReceipt receipt = accessControl.shareRequest(userId, dataId);
                    List<AccessControl.RequestApprovedEventResponse> responses = accessControl.getRequestApprovedEvents(receipt);
                    if (!responses.isEmpty()) {
                        System.out.println("shareRequest success");
                    } else {
                        System.out.println("common fail");
                    }
                } else {
                    System.out.println("user's can't be allowed to request this data");
                }
            } else {
                System.out.println("user's credit is not enough");
            }


        } catch (Exception e) {
            logger.error("shareRequest exception,error message is {}", e.getMessage());
            System.out.println("shareRequest failed,error is " + e.getMessage());
        }
    }

    public void freeRequest(BigInteger userId, BigInteger dataId, String message) {
        try {
            String contractaddress = loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, client, cryptoKeyPair);
            boolean res = checkList(userId, dataId);
            if (!res) {
                TransactionReceipt receipt = accessControl.freeRequest(userId, dataId, message);
                List<AccessControl.FreeRequestEventResponse> responses = accessControl.getFreeRequestEvents(receipt);
                if (!responses.isEmpty()) {
                    System.out.println("freeRequest success");
                } else {
                    System.out.println("common fail");
                }
            } else {
                System.out.println("user can't be allowed to request this data");
            }
        } catch (Exception e) {
            logger.error("freeRequest exception,error message is {}", e.getMessage());
            System.out.println("freeRequest failed,error is " + e.getMessage());
        }
    }

    //approve request free
    public void freeApprove(BigInteger freeId) {
        try {
            String contractaddress = loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, client, cryptoKeyPair);
            TransactionReceipt receipt = accessControl.freeApproved(freeId);
            List<AccessControl.FreeApprovedEventResponse> responses = accessControl.getFreeApprovedEvents(receipt);
            if (!responses.isEmpty()) {
                System.out.println("freeApprove success");
            } else {
                System.out.println("common fail");
            }
        } catch (Exception e) {
            logger.error("freeApprove exception,error message is {}", e.getMessage());
            System.out.println("freeApprove failed,error is " + e.getMessage());
        }
    }

    public void freeDenied(BigInteger freeId) {
        try {
            String contractaddress = loadScutCloudAddr();
            AccessControl accessControl = AccessControl.load(contractaddress, client, cryptoKeyPair);
            TransactionReceipt receipt = accessControl.freeDenied(freeId);
            List<AccessControl.FreeDeniedEventResponse> responses = accessControl.getFreeDeniedEvents(receipt);
            if (!responses.isEmpty()) {
                System.out.println("freeDenied success");
            } else {
                System.out.println("common fail");
            }
        } catch (Exception e) {
            logger.error("freeDenied exception,error message is {}", e.getMessage());
            System.out.println("freeDenied failed,error is " + e.getMessage());
        }
    }

    public static void Usage() {
        System.out.println(" Usage#:");

        System.exit(0);
    }
//    public static void main(String []args) throws Exception {
//
//        if(args.length < 1){
//            Usage();
//        }
//        ScutCloudClient client = new ScutCloudClient();
//        client.initialize();
//
//        switch (args[0]) {
//            case "deploy" : {
//                client.deployScutCloudAndRecordAddr();
//                break;
//            }
//            case "register" : {
//                if(args.length < 3){
//                    Usage();
//                }
//                client.registerScutCloudUser("lj", "123456", "0x5B38Da6a701c568545dCfcB03FcB875f56beddC4");
//                break;
//            }
//            case "alterUser":{
//                if(args.length < 4){
//                    Usage();
//                }
//                client.alterScutCloudUser(new BigInteger("1"),"ld","123456","ok","0xAb8483F64d9C6d1EcF9b849Ae677dD3315835cb2");
//                break;
//            }
//            case "createData":{
//                if(args.length < 7){
//                    Usage();
//                }
//                List<BigInteger> not = new ArrayList<BigInteger>() ;
//                client.createScutCloudData(new BigInteger("1"), new BigInteger("1"), not,
//                        "test","test is ok","my heart","txt");
//                break;
//            }
//            case "alterData":{
//                if(args.length < 7){
//                    Usage();
//                }
//                List<BigInteger> not = new ArrayList<BigInteger>() ;
//                client.alterScutCloudData(new BigInteger("1"), new BigInteger("1"), not,
//                        "test_test1","test is ok","my heart","txt");
//                break;
//            }
//            case"getDatas":{
//                List<Tuple7<BigInteger, BigInteger, BigInteger, String, String, String, String>> temp = client.getdatas();
//               // JsonObject jsonObject = new JsonObject();
//                List<JsonObject> jsonObjects = new ArrayList<>();
//                //System.out.println(client.getdatas().get(0));//check
//                System.out.println(Integer.parseInt((client.getScutCloudDataLength().toString())));
//                for(int i  = 0 ;i < Integer.parseInt((client.getScutCloudDataLength().toString()));i++) {
//                    jsonObjects.get(i).addProperty("dataID", temp.get(i).getValue1());
//                    jsonObjects.get(i).addProperty("dataCredit",temp.get(i).getValue2());
//                    jsonObjects.get(i).addProperty("downloadTimes",temp.get(i).getValue3());
//                    jsonObjects.get(i).addProperty("notAllow",temp.get(i).getValue4());
//                    jsonObjects.get(i).addProperty("dataName",temp.get(i).getValue5());
//                    jsonObjects.get(i).addProperty("dataDescription",temp.get(i).getValue6());
//                    jsonObjects.get(i).addProperty("dataAddress",temp.get(i).getValue7());
//                }
//                System.out.println(jsonObjects);
//                break;
//            }
//            case"getUserLength":{
//                System.out.println(client.getUserLength());
//                break;
//            }
//            default: {
//                Usage();
//            }
//        }
//        System.exit(0);
//    }
}
