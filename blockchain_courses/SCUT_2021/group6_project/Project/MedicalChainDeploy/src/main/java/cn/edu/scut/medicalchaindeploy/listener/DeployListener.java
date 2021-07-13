package cn.edu.scut.medicalchaindeploy.listener;


import cn.edu.scut.medicalchaindeploy.contract.Address;
import cn.edu.scut.medicalchaindeploy.contract.MedicalChain;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class DeployListener implements ApplicationListener<ApplicationReadyEvent> {

    @Resource
    Client client;

    @Value("${static.contract-owner-private-key}")
    String ownerPrivateKey;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        try{
            CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
            CryptoKeyPair keyPair = cryptoSuite.createKeyPair(ownerPrivateKey);
            MedicalChain medicalChain = MedicalChain.deploy(
                    client,
                    keyPair
            );
            System.out.println("OwnerAddress: " + keyPair.getAddress());
            System.out.println("OwnerPublicKey: " + keyPair.getHexPublicKey());
            System.out.println("OwnerPrivateKey: " + keyPair.getHexPrivateKey());
            System.out.println("ContractAddress: " + medicalChain.getContractAddress());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
