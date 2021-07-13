package org.fisco.bcos.trace.pojo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
public class ItemProcess {
    @Autowired
    @JsonProperty("transfer_time")
    private List<String> transfer_time;
    @JsonProperty("sender_id")
    private List<String> sender_id;
    @JsonProperty("receiver_id")
    private List<String> receiver_id;
    @JsonProperty("send_from")
    private List<String> send_from;
    @JsonProperty("send_to")
    private List<String> send_to;
    public ItemProcess(){
    }
    public ItemProcess(List<String> transfertime,List<String> senderid,List<String> receiverid,
                       List<String>  sendfrom,List<String>  sendto){
        transfer_time=transfertime;
        sender_id=senderid;
        receiver_id=receiverid;
        send_from=sendfrom;
        send_to=sendto;
    }
}
