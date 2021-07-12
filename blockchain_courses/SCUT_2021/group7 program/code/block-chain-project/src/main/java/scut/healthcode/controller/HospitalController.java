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


@RestController
@CrossOrigin
@RequestMapping("/hospital")
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;

    private static final Logger logger = LoggerFactory.getLogger(HospitalController.class);

    /**
     * Hospital upload Nucleic Acid Information
     *
     * @param nucleicAcidInfo The Nucleic Acid Information
     * @return whether the information is inserted
     */
//    String ID, String userName, String time, int result
    @PostMapping("/upload")
    public HashMap<String, Object> upload(@RequestBody NucleicAcidInfo nucleicAcidInfo){
        try {
            logger.info("accept nucleic Acid Info");
            logger.info(nucleicAcidInfo.toString());

            return hospitalService.upload(nucleicAcidInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
