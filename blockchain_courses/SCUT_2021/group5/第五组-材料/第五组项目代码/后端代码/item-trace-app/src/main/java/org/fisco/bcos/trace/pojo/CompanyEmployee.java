package org.fisco.bcos.trace.pojo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
public class CompanyEmployee {
    @Autowired
    @JsonProperty("employee_id")
    private List<String> employee_id;
    @JsonProperty("nick_name")
    private List<String> nick_name;
    public CompanyEmployee(){
    }
    public CompanyEmployee(List<String> employeeid,List<String> nickname){
        employee_id=employeeid;
        nick_name=nickname;
    }
}
