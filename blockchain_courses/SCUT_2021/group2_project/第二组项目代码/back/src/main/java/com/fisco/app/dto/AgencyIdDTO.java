package com.fisco.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 将agency_id封装成对象进行传输
 * @author amer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgencyIdDTO {
    private String agencyId;
}
