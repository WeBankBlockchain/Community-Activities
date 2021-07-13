package org.fisco.bcos.trace.pojo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
public class ReceiverRequest {
    @Autowired
    @JsonProperty("sender_id")
    private List<String> sender_id;
    @JsonProperty("send_from")
    private List<String> send_from;
    @JsonProperty("send_to")
    private List<String> send_to;
    @JsonProperty("item_list")
    private List<List<String> > item_list;
    public ReceiverRequest(){
    }
    public ReceiverRequest(List<String> senderid,List<String> sendfrom,List<String> sendto,List<List<String> > itemlist){
        sender_id=senderid;
        send_from=sendfrom;
        send_to=sendto;
        item_list=itemlist;
    }
}
