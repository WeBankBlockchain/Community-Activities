package scut.healthcode.service;


import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public interface TestService {

    /**
     * test
     * @param input The test input.
     * @return If success, return "wahaha".
     * @author
     */
    public String getStr(String input);

    public HashMap<String, Object> query(String table_name, String primary_key) throws Exception;
//    public String getAccount(String input) throws Exception;
}
