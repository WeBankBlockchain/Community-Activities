package scut.healthcode.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import scut.healthcode.entity.NucleicAcidInfo;
import scut.healthcode.entity.RegionInfo;
import scut.healthcode.service.GovernmentService;

import java.util.HashMap;

@RestController
@CrossOrigin
@RequestMapping("/government")
public class GovernmentController {
    @Autowired
    private GovernmentService governmentService;
    private static final Logger logger = LoggerFactory.getLogger(GovernmentController.class);

    /**
     * Government upload Region Information
     *
     * @param regionInfo Region Information
     * @return whether the information is inserted
     */
    @PostMapping("/upload")
    public HashMap<String, Object> upload(@RequestBody RegionInfo regionInfo){
        try {
            logger.info("accept region information");
            logger.info(regionInfo.toString());

            return governmentService.upload(regionInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
