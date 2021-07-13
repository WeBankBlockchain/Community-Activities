package org.fisco.bcos.trace.pojo;

import java.util.List;

public class TransferRequest {
    public String receiver_id;
    public String send_from;
    public String send_to;
    public List<String> item_list;
    TransferRequest(){}
    TransferRequest(String receiverid,String sendfrom,String sendto,List<String> itemlist){
        receiver_id=receiverid;
        send_from=sendfrom;
        send_to=sendto;
        item_list=itemlist;
    }

}
