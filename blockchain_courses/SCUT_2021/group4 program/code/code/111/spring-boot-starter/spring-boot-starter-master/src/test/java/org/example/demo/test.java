package org.example.demo;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;

import java.math.BigInteger;
import java.util.ArrayList;

public class test {
    public final String configFile = test.class.getClassLoader().getResource("config.toml").getPath();
    public Client generateAccount()
    {
        // 初始化BcosSDK对象
        BcosSDK sdk =  BcosSDK.build(configFile);
        // 获取Client对象，此处传入的群组ID为1
        Client client = sdk.getClient(Integer.valueOf(1));
        // 构造AssembleTransactionProcessor对象，需要传入client对象，CryptoKeyPair对象和abi、binary文件存放的路径。abi和binary文件需要在上一步复制到定义的文件夹中。
        CryptoKeyPair keyPair = client.getCryptoSuite().createKeyPair();
        return client;
    }

    public void myBalance(Client client) throws Exception {

        AssembleTransactionProcessor transactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(client, client.getCryptoSuite().getCryptoKeyPair(), "src/main/resources/abi/", "");
        //  ArrayList<Object> params = new ArrayList<>();


          /*  for(int i=0;i<input.size();i++)
            {

                params.add(input.get(i));

            }
            */

        String address="0x4382671d60c022e109d57b0f64db9d9c35d87f9d";
        TransactionResponse transactionResponse = transactionProcessor.sendTransactionAndGetResponseByContractLoader("org.example.demo.contracts.Account", address, "myBalance",new ArrayList<>());
        // 调用合约，合约地址为helloWorldAddress， 调用函数名为『set』，函数参数类型为params

        System.out.println( transactionResponse.getReturnObject());
        BigInteger id = (BigInteger) transactionResponse.getReturnObject().get(0);
    }

}