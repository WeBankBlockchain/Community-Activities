package scut.healthcode.blockchain.client;

import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import scut.healthcode.blockchain.contract.Government;
import scut.healthcode.blockchain.contract.Hospital;
import scut.healthcode.blockchain.contract.User;
import scut.healthcode.blockchain.contract.Util;
import scut.healthcode.entity.NucleicAcidInfo;
import scut.healthcode.entity.RegionInfo;
import scut.healthcode.entity.UserInfo;
import scut.healthcode.util.RetMessageFactory;

public class HealthcodeClient {
    private static final Logger logger = LoggerFactory.getLogger(HealthcodeClient.class);

    private BcosSDK bcosSDK;
    private org.fisco.bcos.sdk.client.Client client;
    private CryptoKeyPair cryptoKeyPair;


    public static final String HOSPITAL_ADDRESS = "HospitalAddress";
    public static final String USER_ADDRESS = "UserAddress";
    public static final String UTIL_ADDRESS = "UtilAddress";
    public static final String GOVERNMENT_ADDRESS = "GovernmentAddress";
//    public static final String HEALTHCODE_ADDRESS = "HealthcodeAddress";

    //单例模式
    private static HealthcodeClient healthcodeClient;
    private HealthcodeClient(){}
    public static HealthcodeClient getHealthcodeClient(){
        if (healthcodeClient == null) {
            healthcodeClient = new HealthcodeClient();
            try {
                healthcodeClient.initialize();
                healthcodeClient.deployAndRecordAddr(null);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return healthcodeClient;
    }

    /***
     * 初始化，仅生成healthcodeClient时调用一次
     * @throws Exception
     */
    private void initialize() throws Exception {
        @SuppressWarnings("resource")
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        bcosSDK = context.getBean(BcosSDK.class);
        client = bcosSDK.getClient(1);
        cryptoKeyPair = client.getCryptoSuite().createKeyPair();
        client.getCryptoSuite().setCryptoKeyPair(cryptoKeyPair);
        logger.debug("create client for group1, account address is " + cryptoKeyPair.getAddress());
    }

    /**
     * 部署合约，并持久化记录该合约地址
     * @param contractName 合约名
     */
    public void deployAndRecordAddr(String contractName) {
        try {
            if (contractName == null){
                logger.info("contract name is null");
                loadAssetAddr(HOSPITAL_ADDRESS);
                loadAssetAddr(UTIL_ADDRESS);
                loadAssetAddr(GOVERNMENT_ADDRESS);
                loadAssetAddr(USER_ADDRESS);
//                recordAddr(HEALTHCODE_ADDRESS, Healthcode.deploy(client, cryptoKeyPair).getContractAddress());
            } else {

                switch (contractName) {
                    case HOSPITAL_ADDRESS:
                        recordAddr(HOSPITAL_ADDRESS, Hospital.deploy(client, cryptoKeyPair).getContractAddress());
                        break;
                    case USER_ADDRESS:
                        recordAddr(USER_ADDRESS, User.deploy(client, cryptoKeyPair).getContractAddress());
                        break;
                    case UTIL_ADDRESS:
                        recordAddr(UTIL_ADDRESS, Util.deploy(client, cryptoKeyPair).getContractAddress());
                        break;
                    case GOVERNMENT_ADDRESS:
                        recordAddr(GOVERNMENT_ADDRESS, Government.deploy(client, cryptoKeyPair).getContractAddress());
                        break;
                    default: {
                        logger.info("no this contract");
                    }
                }
            }
        } catch (Exception e) {
             e.printStackTrace();
            System.out.println(" deploy " + contractName + "contract failed, error message is  " + e.getMessage());
        }
    }

    /**
     * 持久化存储合约信息在属性文件contract.properties中
     * @param contractName 合约名
     * @param address 合约地址
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void recordAddr(String contractName, String address) throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        final Resource contractResource = new ClassPathResource("contract.properties");
        prop.load(contractResource.getInputStream());
        prop.setProperty(contractName, address);

        FileOutputStream fileOutputStream = new FileOutputStream(contractResource.getFile());
        prop.store(fileOutputStream, "contract address");
    }

    /**
     * 从属性文件contract.properties中读取合约地址，不存在则新建
     * @param contractName 合约名
     * @return 合约地址
     * @throws Exception
     */
    public String loadAssetAddr(String contractName) throws Exception {
        // load Asset contact address from contract.properties
        Properties prop = new Properties();
        Resource contractResource = new ClassPathResource("contract.properties");
        prop.load(contractResource.getInputStream());

        String contractAddress = prop.getProperty(contractName);

        logger.info(" load " + contractName + "contract at " + contractAddress);
        if (contractAddress == null || contractAddress.trim().equals("")) {
            deployAndRecordAddr(contractName);
            contractResource = new ClassPathResource("contract.properties");
            prop.load(contractResource.getInputStream());

            contractAddress = prop.getProperty(contractName);
            logger.info(" load " + contractName + "contract address failed, deploy it at " + contractAddress);
        }

        return contractAddress;
    }

    ///////////////////////////////////用户\商场模块//////////////////////////////////////////
    /**
     * 获取ID对应用户的健康状况，并返回健康哈希值
     * @param ID 身份证号
     * @return ret_code & 健康哈希值
     */
    public HashMap<String, Object> generateHealthcode(String ID) {
        HashMap<String, Object> ret = new HashMap<>();
        try {
            deployAndRecordAddr(USER_ADDRESS);
            String contractAddress = loadAssetAddr(USER_ADDRESS);
            User user = User.load(contractAddress, client, cryptoKeyPair);

            TransactionReceipt receipt = user.generate(ID);
            Tuple2<BigInteger, String> result = user.getGenerateOutput(receipt);

            int ret_code = result.getValue1().intValue();
            String hashcode = result.getValue2();
            ret.put("ret_code", ret_code);
            ret.put("Hashcode", hashcode);
            return ret;
        } catch (Exception e) {
            // TODO Auto-generated catch block
             e.printStackTrace();
        }
        return ret;
    }

    /**
     * 根据健康哈希值，返回用户是否健康
     * @param hashcode 健康哈希值
     * @return is_valid & is_health
     */
    public int[] isHealth(String hashcode) {
        try {
            String contractAddress = loadAssetAddr(USER_ADDRESS);
            User user = User.load(contractAddress, client, cryptoKeyPair);

            Tuple2<BigInteger, BigInteger> result = user.isHealthy(hashcode);
            int is_valid = result.getValue1().intValue();
            int is_health = result.getValue2().intValue();
            return new int[]{is_valid, is_health};
//            switch (is_valid) {
//                case 0:
//                    logger.info("Valid HealthCode.");
//                    if (is_health == 0) {
//                        return 0;
//                    } else if (is_health == -1) {
//
//                        logger.info("The health state of User is Unknown.");
//                    }
//                    break;
//                case 1:
//                    logger.info("HealthCode doesn't exist.");
//                    break;
//                case 2:
//                    logger.info("HealthCode out of date");
//                    break;
//                default:
//                    logger.info("Unexpected valid code with " + is_valid);
//            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.info("health select failed.");
        }
        return new int[]{-100, -100};
    }

    /**
     * 上传用户个人信息
     * @param userInfo 用户个人信息
     */
    public int userUpload(UserInfo userInfo) {
        try {
            String contractAddress = loadAssetAddr(USER_ADDRESS);
            User user = User.load(contractAddress, client, cryptoKeyPair);

            TransactionReceipt receipt = user.upload(userInfo.getId(), userInfo.getName(),
                    userInfo.getResidence());
            return user.getUploadOutput(receipt).getValue1().intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -100;
    }


    ///////////////////////////////////////医院模块//////////////////////////////////////////
    /**
     * 发起上传核酸信息的交易
     * @param nucleicAcidInfo 核酸检测信息
     * @return 状态码
     */
    public int hospitalUpload(NucleicAcidInfo nucleicAcidInfo) {
        try {
            // 重新部署一次合约，测试用，待删除
//            deployAssetAndRecordAddr(HOSPITAL_ADDRESS);
            // FISCO BCOS 的now获取的并非unix时间戳，都不知道是什么东西, 智能合约无法正确处理
            // 在这里进行加工, FISCO BCOS也太垃圾了吧
            String contractAddress = loadAssetAddr(HOSPITAL_ADDRESS);
            Hospital hospital = Hospital.load(contractAddress, client, cryptoKeyPair);

            SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
//            String time="1970-01-06 11:45:55";
            Date date = format.parse(nucleicAcidInfo.getTime().trim());
//            System.out.print("********************Format To times:"+date.getTime()/1000);
            if (new Date().getTime() < date.getTime()) {
                System.out.println("当前时间戳: " + new Date().getTime());
                System.out.println("检测时间: " + date.getTime());
                return -1;
            }

            if (nucleicAcidInfo.getResult() < 0 || nucleicAcidInfo.getResult() > 1) {
                return -3;
            }

            TransactionReceipt receipt = hospital.upload(
                    nucleicAcidInfo.getId(),
                    nucleicAcidInfo.getUserName(),
                    new BigInteger(String.valueOf(nucleicAcidInfo.getResult())),
                    new BigInteger(String.valueOf(date.getTime()/1000)));

            Tuple1<BigInteger> result = hospital.getUploadOutput(receipt);
            return result.getValue1().intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -100;
        
//        return "hospitalUpload failed.";
    }

    ///////////////////////////////////////Government//////////////////////////////////////////
    /**
     * Send transaction to upload region info.
     * @param regionInfo Region Info
     * @return ret code
     */

    public int governmentUpload(RegionInfo regionInfo){
        try{
            deployAndRecordAddr(GOVERNMENT_ADDRESS);
            String contractAddress = loadAssetAddr(GOVERNMENT_ADDRESS);
            Government government = Government.load(contractAddress, client, cryptoKeyPair);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(regionInfo.getDate().trim());
            if(new Date().getTime() < date.getTime()){
                System.out.println("Current time:" + new Date().getTime());
                System.out.println("Upload time:" + date.getTime());
                return -1;
            }

            if(regionInfo.getIsDangerous() < 0 || regionInfo.getIsDangerous() > 1){
                return -3;
            }

            TransactionReceipt receipt = government.upload_region_info(
                    regionInfo.getRegionName(),
                    new BigInteger(String.valueOf(regionInfo.getIsDangerous())),
                    new BigInteger(String.valueOf(date.getTime()/1000)));
            Tuple1<BigInteger> result = government.getUpload_region_infoOutput(receipt);
            return result.getValue1().intValue();

        }catch (Exception e){
            e.printStackTrace();
        }
        return -100;


    }

    ///////////////////////////////////////查询//////////////////////////////////////////
    public HashMap<String, Object> query(String table_name, String primary_key) throws Exception {
        String contractAddress = loadAssetAddr(UTIL_ADDRESS);
        Util util = Util.load(contractAddress, client, cryptoKeyPair);
        TransactionReceipt receipt = util.query(table_name, primary_key);
        Tuple2<BigInteger, String> result = util.getQueryOutput(receipt);
        int ret_code = result.getValue1().intValue();
        HashMap<String, Object> ret = RetMessageFactory.newReturnMessage(ret_code);
        ret.put("entry", result.getValue2());
        return ret;
    }
}