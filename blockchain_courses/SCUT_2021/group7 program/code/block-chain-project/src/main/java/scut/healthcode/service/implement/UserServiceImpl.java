package scut.healthcode.service.implement;

import org.springframework.stereotype.Service;
import scut.healthcode.blockchain.client.HealthcodeClient;
import scut.healthcode.entity.UserInfo;
import scut.healthcode.service.UserService;
import scut.healthcode.util.RetMessageFactory;

import java.util.HashMap;

@Service("userService")
public class UserServiceImpl implements UserService {


    @Override
    public HashMap<String, Object> upload(UserInfo userInfo) {
        HashMap<String, Object> ret = RetMessageFactory.newReturnMessage(-100);
        try {
            HealthcodeClient healthcodeClient = HealthcodeClient.getHealthcodeClient();
            int ret_code = healthcodeClient.userUpload(userInfo);
            String ret_info = "";
            switch (ret_code) {
                case -1: ret_info = "Upload Fail."; break;
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

    @Override
    public HashMap<String, Object> isHealth(String hashCode) {
        HashMap<String, Object> ret = RetMessageFactory.newReturnMessage(-100);
        try{
            HealthcodeClient healthcodeClient = HealthcodeClient.getHealthcodeClient();
            int[] ret_code = healthcodeClient.isHealth(hashCode);
            String ret_info = "";
            switch (ret_code[0]) {
                case 0:
                {
                    switch (ret_code[1]){
                        case 0:
                            ret_info = "Healthy";break;
                        case 1:
                            ret_info = "Unhealthy";break;
                        case -1:
                            ret_info = "Unknown Result";break;
                    }
                    break;
                }
                case -1:
                    ret_info = "HashCode doesn't Exist.";break;
                case -2:
                    ret_info = "HashCode Expired"; break;
            }

            ret.put("ret_code", ret_code[0]);
            ret.put("ret_info", ret_info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public HashMap<String, Object> generateHealthcode(String ID) {
        HashMap<String, Object> ret = RetMessageFactory.newReturnMessage(-100);
        try {
            HealthcodeClient healthcodeClient = HealthcodeClient.getHealthcodeClient();
            ret = healthcodeClient.generateHealthcode(ID);
            int is_vaild = (int) ret.get("ret_code");
            String ret_info = "";
            switch (is_vaild) {
                case 1: ret_info = "Generate success."; break;
                case 2: ret_info = "User does not exist."; break;
                case 3: ret_info = "Unknown problem."; break;
                default: ret_info = "Unexpected ret_code with " + ret.get("ret_code"); break;
            }
            ret.put("ret_info", ret_info);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
}
