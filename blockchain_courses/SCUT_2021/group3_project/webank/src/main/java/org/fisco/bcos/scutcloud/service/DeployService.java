package org.fisco.bcos.scutcloud.service;

import org.fisco.bcos.scutcloud.client.ScutCloudClient;
import org.fisco.bcos.scutcloud.contract.AccessControl;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.graalvm.compiler.lir.CompositeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DeployService {

    static public Logger logger = LoggerFactory.getLogger(ScutCloudClient.class);

    public BcosSDK bcosSDK;
    public Client client;
    public CryptoKeyPair cryptoKeyPair;



//    public DeployService(Logger logger,BcosSDK bcosSDK,Client client ,CryptoKeyPair cryptoKeyPair){
//        this.logger = logger;
//        this.bcosSDK = bcosSDK;
//        this.client = client;
//        this.cryptoKeyPair = cryptoKeyPair;
//    }
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

//    public void setLogger(CryptoKeyPair cryptoKeyPair) {
//        this.logger.debug("create client for group1,account address is" + cryptoKeyPair.getAddress());
//    }
//    public void setCryptoKeyPair(Client client){
//        this.cryptoKeyPair = client.getCryptoSuite().getCryptoKeyPair();
//        client.getCryptoSuite().setCryptoKeyPair(cryptoKeyPair);
//    }
//    public void setClient(BcosSDK bcosSDK){
//        this.client = bcosSDK.getClient(1);
//    }
//    @Bean
//    public BcosSDK bcosSDK(){
//        ApplicationContext context =
//                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        bcosSDK = context.getBean(BcosSDK.class);
//        return bcosSDK;
//    }
//    @Bean
//    public Client client(){
//        Client client = bcosSDK.getClient(1);
//        return client;
//    }
//    @Bean
//    public CryptoKeyPair cryptoKeyPair(){
//        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().getCryptoKeyPair();
//        client.getCryptoSuite().setCryptoKeyPair(cryptoKeyPair);
//        return cryptoKeyPair;
//    }
//
//    @Bean
//    Logger logger(){
//
//        logger.debug("create client for group1,account address is" + cryptoKeyPair.getAddress());
//        return logger;
//    }

}
