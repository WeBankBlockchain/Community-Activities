package scut.healthcode.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import scut.healthcode.entity.NucleicAcidInfo;
import scut.healthcode.service.HospitalService;
import scut.healthcode.service.TestService;
import scut.healthcode.service.implement.TestServiceImpl;

import java.util.HashMap;
import java.util.Locale;

@RestController
//@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private TestService testService;

    private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    @GetMapping("/get")
    public String sayHello(@RequestParam("testinput") String testinput){
        return testService.getStr(testinput);
    }


    @PostMapping("/uploadJson")
    public String uploadJson(NucleicAcidInfo nucleicAcidInfo){
        logger.info("accept");
        return "kl";
    }

    @PostMapping("/query")
    public HashMap<String, Object> query(@RequestParam String table_name, @RequestParam String primary_key) throws Exception {
        return testService.query(table_name, primary_key);
    }
}
