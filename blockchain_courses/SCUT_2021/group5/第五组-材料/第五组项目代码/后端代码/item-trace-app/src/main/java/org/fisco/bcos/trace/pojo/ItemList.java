package org.fisco.bcos.trace.pojo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
public class ItemList {
    @Autowired
    @JsonProperty("item_list")
    private List<List<String> > item_list;
    public ItemList(){
    }
    public ItemList(List<List<String> > itemlist){
        item_list=itemlist;
    }
}
