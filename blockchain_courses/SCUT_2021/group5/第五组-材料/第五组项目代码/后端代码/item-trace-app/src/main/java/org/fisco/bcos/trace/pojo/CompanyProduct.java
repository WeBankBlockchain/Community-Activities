package org.fisco.bcos.trace.pojo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
public class CompanyProduct {
    @Autowired
    @JsonProperty("product_id")
    private List<String> product_id;
    @JsonProperty("product_name")
    private List<String> product_name;
    public CompanyProduct(){
    }
    public CompanyProduct(List<String> productid,List<String> productname){
        product_id=productid;
        product_name=productname;
    }
}
