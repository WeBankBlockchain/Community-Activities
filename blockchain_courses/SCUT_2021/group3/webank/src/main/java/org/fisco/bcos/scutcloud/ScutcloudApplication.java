package org.fisco.bcos.scutcloud;

import org.fisco.bcos.scutcloud.client.ScutCloudClient;
import org.fisco.bcos.scutcloud.pojo.FileProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@EnableConfigurationProperties({
//    FileProperty.class
//})
public class ScutcloudApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ScutcloudApplication.class, args);
//        ScutCloudClient client = new ScutCloudClient();
//        client.initialize();
//        client.deployScutCloudAndRecordAddr();
    }
}
