package org.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.config.BcosConfig;
import org.example.demo.config.BcosSDKConfig;
import org.example.demo.constants.ContractConstants;
import org.example.demo.contracts.Account;
import org.example.demo.contracts.NTFMarket;
import org.example.demo.contracts.Transaction;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.config.ConfigOption;
import org.fisco.bcos.sdk.config.exceptions.ConfigException;
import org.fisco.bcos.sdk.config.model.ConfigProperty;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.fisco.bcos.sdk.client.Client;

import java.util.HashMap;

@Slf4j
@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ContractService {
    public static Account accountContract;
    public static banquanMarket banquanMarketContract;
    public static Transaction transactionContract;

    @Autowired
    BcosSDKConfig bcosSDKConfig;

    static BcosSDK bcosSDK;

    public void setContracts(String privateKey) throws Exception {

        bcosSDK = bcosSDKConfig.getBcosSDK();
        log.info("get bcosSDK finish");
        Client client = bcosSDK.getClient(1);
        log.info("get client finish");
        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
        log.info("cryptosuite finish");
        CryptoKeyPair cryptoKeyPair = cryptoSuite.getKeyPairFactory().createKeyPair(privateKey);
        log.info("keypair finish");
        accountContract = Account.load(ContractConstants.AccountAddress, client, cryptoKeyPair);
        ntfMarketContract = NTFMarket.load(ContractConstants.NTFMarketAddress, client, cryptoKeyPair);
        transactionContract = Transaction.load(ContractConstants.TransactionAddress, client, cryptoKeyPair);
    }

    public static Account getAccountContract() {
        return accountContract;
    }

    public static banquanMarket getbanquanMarketContract() {
        return banquanMarketContract;
    }

    public static Transaction getTransactionContract() {
        return transactionContract;
    }
}
