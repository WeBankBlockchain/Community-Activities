package scut.healthcode.service.implement;
import org.springframework.stereotype.Service;
//import scut.healthcode.blockchain.client.AssetClient;
import scut.healthcode.blockchain.client.HealthcodeClient;
import scut.healthcode.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.HashMap;

@Service("testService")
public class TestServiceImpl implements TestService {

    private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    @Override
    public String getStr(String input) {
        logger.info(input);
        try {
//            HealthcodeClient healthcodeClient = HealthcodeClient.getHealthcodeClient();
//            return healthcodeClient.isHealthSelect("1111");
            return "hello Huffman";
        } catch (Exception e){
            e.printStackTrace();
        }
        return "wahaha";
    }

    @Override
    public HashMap<String, Object> query(String table_name, String primary_key) throws Exception {
        return HealthcodeClient.getHealthcodeClient().query(table_name, primary_key);
    }

}
