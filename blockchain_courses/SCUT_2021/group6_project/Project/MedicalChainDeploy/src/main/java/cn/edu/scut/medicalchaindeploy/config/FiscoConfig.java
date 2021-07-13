package cn.edu.scut.medicalchaindeploy.config;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.Resource;

@ImportResource(locations = "classpath:applicationContext.xml")
@Configuration
public class FiscoConfig {

    @Resource
    BcosSDK bcosSDK;

    @Bean
    public Client client(){
        return bcosSDK.getClient(1);
    }
}