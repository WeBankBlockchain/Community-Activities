package org.fisco.bcos.trace.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class ItemID {
    @Autowired
    @JsonProperty("item_id")
    private String item_id;

    public ItemID() {
    }
    public ItemID(String itemid) {
        item_id=itemid;
    }
}
