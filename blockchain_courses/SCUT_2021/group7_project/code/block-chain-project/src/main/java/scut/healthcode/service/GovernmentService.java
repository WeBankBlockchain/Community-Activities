package scut.healthcode.service;

import org.springframework.stereotype.Service;
import scut.healthcode.entity.RegionInfo;

import java.util.HashMap;

@Service
public interface GovernmentService {
    /**
     * Upload Region Information to block chain
     *
     * @param regionInfo The Nucleic Acid Information
     * @author Patrick
     * @return whether the information is inserted
     */

    HashMap<String, Object> upload(RegionInfo regionInfo);
}
