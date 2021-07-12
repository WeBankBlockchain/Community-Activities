package cn.edu.scut.medicalchaindeploy;

import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MedicalChainDeployApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalChainDeployApplication.class, args);
    }

}
