package org.fisco.bcos.trace.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

//import org.apache.ibatis.type.Alias;
@Data
public class UserLevel {
    /*private String msg_id;
    private int status;
    private int version;
    private int eyes;*/
    @Autowired
    @JsonProperty("user_level")
    private String level;

    public UserLevel() {
    }
    public UserLevel(String lv) {
        this.level=lv;
    }
}
