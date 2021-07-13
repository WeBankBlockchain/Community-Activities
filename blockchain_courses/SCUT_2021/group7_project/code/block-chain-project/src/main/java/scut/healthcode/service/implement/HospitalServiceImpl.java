package scut.healthcode.service.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import scut.healthcode.blockchain.client.HealthcodeClient;
import scut.healthcode.entity.NucleicAcidInfo;
import scut.healthcode.service.HospitalService;
import scut.healthcode.util.RetMessageFactory;

import java.util.HashMap;

@Service("hospitalService")
public class HospitalServiceImpl implements HospitalService {

    private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    @Override
    public HashMap<String, Object> upload(NucleicAcidInfo nucleicAcidInfo) {
        HashMap<String, Object> ret = RetMessageFactory.newReturnMessage(-100);
        try {
            HealthcodeClient healthcodeClient = HealthcodeClient.getHealthcodeClient();
            int ret_code = healthcodeClient.hospitalUpload(nucleicAcidInfo);
            String ret_info = "";
            switch (ret_code) {
                case -3: ret_info = "Upload Fail. Invalid value of attribute <result>"; break;
                case -2: ret_info = "Unknown Error"; break;
                case -1: ret_info = "Upload Fail. Invalid value of attribute <date>"; break;
                case 0: ret_info = "Upload Success. Insert a new record."; break;
                case 1: ret_info = "Upload Success. Update an exist record"; break;
                default: ret_info = "Unexpected ret_code with " + ret_code; break;
            }

            ret.put("ret_code", ret_code);
            ret.put("ret_info", ret_info);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
}
