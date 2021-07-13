package org.example.demo.config;

import lombok.Data;
import bcos.ContractAddress;
import org.example.demo.contracts.Account;
import org.example.demo.contracts.NTFMarket;
import org.example.demo.contracts.Transaction;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.client.Client;
import org.springframework.context.annotation.Bean;


@Slf4j
@Configuration
@ConfigurationProperties(prefix = "contract-address")
public class ContractConfig {
    private String account;
    private String ntfmarket;
    private String transaction;
    @Bean
    public Account loadAccount(Client client){
        return Account.load(account, client, client.getCryptoSuite().getCryptoKeyPair());

    }
    @Bean
    public NTFMarket loadNTFMarket(Client client){
        return NTFMarket.load(ntfmarket, client, client.getCryptoSuite().getCryptoKeyPair());

    }
    @Bean
    public Transaction loadTransaction(Client client){
        return Transaction.load(transaction, client, client.getCryptoSuite().getCryptoKeyPair());

    }
    @Bean
    public ContractAddress setAddress(){

        ContractAddress contractAddress = new ContractAddress();
        contractAddress.setAccount(account);
        contractAddress.setNtfmarket(ntfmarket);
        contractAddress.setTransaction(transaction);
        return contractAddress;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNtfmarket() {
        return ntfmarket;
    }

    public void setNtfmarket(String ntfmarket) {
        this.ntfmarket = ntfmarket;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }
}
